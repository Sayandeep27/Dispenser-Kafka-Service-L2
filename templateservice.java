package com.icici.smsgateway.server;
// implemented as told by manesh sir
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import com.icici.smsgateway.common.GlobalFunc;
import com.icici.smsgateway.common.KafkaConfig;
import com.icici.smsgateway.common.UniqueIdGenerator;
import com.icici.smsgateway.dao.KafkaResponseData;
import com.icici.smsgateway.dao.DBPool;
import com.icici.smsgateway.dao.KafkaDataSend;
import com.icici.smsgateway.mail.JSendMailCode;
import org.json.JSONObject;

public class SMSServerIdleHandler extends ChannelDuplexHandler {

    private static final Logger logger = LogManager.getLogger(SMSServerIdleHandler.class.getName());

    private final DBPool dbpool;
    private final List lstIM;
    private final List lstVD;
    private final List lpsd; // For getting Priority
    private static String SeperatorString = "^`!";
    private String messageType = "";
    private String errorMsg = "";
    private String nPipeId = "";
    private String nPipePnId = "";
    private String sourceIP = "";
    private String sourcePort = "";
    String localPort = "";
    String localIP = "";
    int messagePriority = 0; // store message priority
    Map<String, String> pnCheck;
    Properties prop;
    Collection clients;

    SMSServerIdleHandler(Properties prop, Collection<String> clientips, DBPool mdbpool, List<String> lstIM,
            List<String> lstVD, List<String> lpsd, Map<String, String> pnCheck) {

        this.prop = prop;
        this.clients = clientips;
        this.dbpool = mdbpool;
        this.lstIM = lstIM;
        this.lstVD = lstVD;
        this.lpsd = lpsd;
        this.pnCheck = pnCheck;
    }

    // -------------------------------------------------------------------------
    // String utility methods
    // -------------------------------------------------------------------------

    private String replaceUnWantedChar(String inStr) {
        inStr = inStr.replace("\r", "");
        inStr = inStr.replace("\n", "");
        inStr = inStr.trim();
        return inStr;
    }

    private String replaceUnWantedCharXML(String inStr) {
        inStr = inStr.replace("<MESSAGE>", "<MESSAGE><![CDATA[");
        inStr = inStr.replace("</MESSAGE>", "]]></MESSAGE>");
        inStr = inStr.trim();
        return inStr;
    }

    private String replaceInMobile(String inStr) {
        inStr = inStr.replace(" ", "");
        inStr = inStr.replace("-", "");
        inStr = inStr.replace("\\r", "");
        inStr = inStr.replace("\\n", "");
        inStr = inStr.trim();
        return inStr;
    }

    // -------------------------------------------------------------------------
    // Channel lifecycle
    // -------------------------------------------------------------------------

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        SMSIdleServer._nClientCount.getAndIncrement();
        System.out.println("Client Connected ::" + SMSIdleServer._nClientCount + " at " + SMSIdleServer._connString);
        logger.info("Client Connected ::" + SMSIdleServer._nClientCount + " at " + SMSIdleServer._connString);

        String localAddress = ctx.channel().localAddress().toString();
        localPort = localAddress.substring(localAddress.indexOf(":") + 1);
        localIP = localAddress.substring(1, localAddress.indexOf(":"));

        String sourceAdd = ctx.channel().remoteAddress().toString();
        sourceIP = sourceAdd.substring((sourceAdd.indexOf("/") + 1), (sourceAdd.indexOf(":")));
        sourcePort = sourceAdd.substring((sourceAdd.indexOf(":") + 1));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SMSIdleServer._nClientCount.getAndDecrement();
        System.out.println("Channel disconnected");
        logger.info("Channel disconnected");
    }

    private void checkMaxConnection() {
        if (SMSIdleServer._nClientCount.get() > Integer.parseInt(prop.getProperty("MAXCONN"))) {
            // sendAlert();
        }
    }

    private boolean checkMaxClient() {
        return false;
    }

    // -------------------------------------------------------------------------
    // Alert on max connections
    // -------------------------------------------------------------------------

    private void sendAlert() {

        Connection con = null;
        CallableStatement st = null, stmt = null;
        ResultSet rs = null;
        InetAddress localhost = null;
        List<String> lstRec = null;
        JSendMailCode mail = null;

        try {
            localhost = (InetAddress.getLocalHost());
        } catch (UnknownHostException e1) {
            logger.error("Error :=> ", e1);
        }

        try {
            con = dbpool.getConnection();
            st = con.prepareCall("{call SMS_ALERT_NUMOF_CONNECTIONS(?,?,?,?,?)}");
            st.setString(1, "Number of connections on Server " + localIP + " " + "and Listener : " + localPort
                    + " Exceeded " + prop.getProperty("MAXCONN") + ".Please check.");
            st.setString(2, sourceIP);
            st.setString(3, sourcePort);
            st.setString(4, localIP);
            st.setString(5, localPort);
            rs = st.executeQuery();
            logger.info("----------------------------------------------------------------------------->>>>>>>>>>>>>>>>>>Crossed Max conn");

            mail = new JSendMailCode();
            lstRec = new ArrayList<String>();
            int i = 0;
            while (rs.next()) {
                lstRec.add(i, rs.getString("EmailID"));
                i++;
            }
            if (!lstRec.isEmpty() && lstRec != null) {
                mail.SendMailSMTP(lstRec,
                        "Number of connections on Server "
                                + localIP + " " + "Port:" + localPort + " Exceeded " + prop.getProperty("MAXCONN")
                                + ".Please check.",
                        "No.of connections on IP:" + localIP + " Port:" + localPort + " exceeded "
                                + prop.getProperty("MAXCONN")
                                + ". Check open connections and close unused connections."
                                + "Coordinate with SMS team(022-24906001).",
                        prop);
            }

        } catch (Exception ex) {
            logger.error("Error in channelConnected method SMSServerIdleHandler ", ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    dbpool.releaseConnection(con);
            } catch (Exception e2) {
                logger.error("Error while closing connection in method SMSServerIdleHandler ", e2);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Inbound message handling
    // -------------------------------------------------------------------------

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        String sourceAdd = ctx.channel().remoteAddress().toString();

        ByteBuf cbReq = (ByteBuf) msg;
        StringBuffer sbReq = new StringBuffer();
        sbReq.append(cbReq.toString(Charset.defaultCharset()).trim());

        if (SMSIdleServer._strDebug.equalsIgnoreCase("Y"))
            logger.info("Request from " + sourceIP + ":" + sbReq.toString());

        try {
            String strXMLOrg = replaceUnWantedChar(sbReq.toString());
            logger.info("XML STRING :" + strXMLOrg);
            List<String> lstVal = new ArrayList<String>();
            lstVal = validXML(strXMLOrg, ctx.channel());

            if (lstVal.get(0).equalsIgnoreCase("SUCCESS")) {
                strXMLOrg = lstVal.get(1);

                do {
                    String strXML = "";
                    if (strXMLOrg.length() > strXMLOrg.indexOf("#END#") + 5) {
                        strXML = strXMLOrg.substring(0, strXMLOrg.indexOf("#END#") + 5);
                        strXMLOrg = strXMLOrg.substring(strXMLOrg.indexOf("#END#") + 5);
                    } else if (strXMLOrg.length() == strXMLOrg.indexOf("#END#") + 5) {
                        strXML = strXMLOrg.substring(0, strXMLOrg.indexOf("#END#") + 5);
                        strXMLOrg = "";
                    }

                    String[] tokens = strXML.split("\\" + SeperatorString);
                    tokens[0] = replaceUnWantedChar(tokens[0].toString());

                    if (tokens[0].equals("CON")) {
                        try {
                            this.messageType = tokens[1].toString().replace("\\r\\n", "");
                            if (tokens.length > 2) {
                                String threadName = tokens[2].toString().replace("\\r\\n", "");
                            }
                            String strRes = "";
                            if (checkMaxClient()) {
                                strRes = "CON!-1" + SeperatorString;
                            } else {
                                strRes = "CON!0" + SeperatorString;
                            }
                            writeInChannel(strRes, ctx.channel());
                        } catch (Exception econ) {
                            writeInChannel("CON!111" + SeperatorString, ctx.channel());
                            logger.error("Error in connection", econ);
                            errorDump("", "", "", sbReq.toString(), GlobalFunc.getDBDateTime(),
                                    "Error in CON::" + econ.getMessage(), getLnrPort(ctx.channel()),
                                    getLnrIP(ctx.channel()), sourceIP, sourcePort);
                        }

                    } else if (tokens[0].equals("MSG")) {
                        try {
                            SMSIdleServer._nMSGCount++;
                            logger.info("Message Received :: " + SMSIdleServer._nMSGCount + " On "
                                    + GlobalFunc.getDBDateTime() + " at " + SMSIdleServer._connString);
                            String strReq = "";
                            strReq = tokens[1].replace("#END#", "");

                            if (this.messageType.equalsIgnoreCase("XML")) {
                                strReq = "<XML>" + strReq + "</XML>";
                                if (validClient(ctx.channel().remoteAddress().toString())) {
                                    String strRes = getResponse(strReq, ctx.channel().localAddress().toString(),
                                            ctx.channel());
                                    writeInChannel(strRes, ctx.channel());
                                } else {
                                    if (SMSIdleServer._strDebug.equalsIgnoreCase("Y"))
                                        logger.info("Request received from unknown client!!!" + sourceAdd);
                                    writeInChannel(
                                            "Request received from unknown client!!! " + ctx.channel().remoteAddress(),
                                            ctx.channel());
                                }
                            }
                        } catch (Exception emsg) {
                            writeInChannel("ACK!111" + SeperatorString, ctx.channel());
                            logger.error("Error in connection", emsg);
                            errorDump("", "", "", sbReq.toString(), GlobalFunc.getDBDateTime(),
                                    "Error in MSG::" + emsg.getMessage(), getLnrPort(ctx.channel()),
                                    getLnrIP(ctx.channel()), sourceIP, sourcePort);
                        }

                    } else if (tokens[0].equals("EXT")) {
                        try {
                            String strRes = "EXT!Client Requested Exit" + SeperatorString;
                            writeInChannel(strRes, ctx.channel());
                        } catch (Exception ex) {
                            writeInChannel("EXT!111" + SeperatorString, ctx.channel());
                            logger.error("Error in connection", ex);
                            errorDump("", "", "", sbReq.toString(), GlobalFunc.getDBDateTime(),
                                    "Error in MSG::" + ex.getMessage(), getLnrPort(ctx.channel()),
                                    getLnrIP(ctx.channel()), sourceIP, sourcePort);
                        } finally {
                            try {
                                ctx.channel().close();
                            } catch (Exception e2) {
                                logger.error("Error in EXT client close", e2);
                            }
                        }

                    } else if (tokens[0].equals("KEEPALIVE")) {
                        logger.info("MESSAGE RCVD FROM CLIENT KEEPALIVE.");
                    }

                } while (strXMLOrg.indexOf("#END#") != -1);
            }

        } catch (Exception em) {
            errorDump("", "", "", sbReq.toString(), GlobalFunc.getDBDateTime(),
                    "Error in messageReceived::" + em.getMessage(), getLnrPort(ctx.channel()),
                    getLnrIP(ctx.channel()), sourceIP, sourcePort);
            logger.error("Error in messageReceived ", em);
        }
    }

    private void dbalert(String localIP2, String localPort2) {
        // TODO Auto-generated method stub
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("Child Channel Idle, closing connection");
        ctx.channel().close();
    }

    // -------------------------------------------------------------------------
    // Client IP validation
    // -------------------------------------------------------------------------

    private boolean validClient(String strClientIP) {
        String strIPAuth = this.prop.getProperty("ipauth");
        logger.info("strIPAuth" + strIPAuth);
        if (strIPAuth != null && strIPAuth.equalsIgnoreCase("N")) {
            return true;
        } else {
            strClientIP = strClientIP.substring(1, strClientIP.indexOf(":"));
            if (this.clients.contains((String) strClientIP)) {
                return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // XML protocol validation (New code - extra character removal, 20 June 2022)
    // -------------------------------------------------------------------------

    private List validXML(String strXML, Channel cnl) {

        List<String> lstVal = new ArrayList<String>();
        lstVal.add(0, "FAILED");
        lstVal.add(1, strXML);

        /*
         * int idxcon = strXML.indexOf("CON^`!") ;
         * int idxmsg = strXML.indexOf("MSG^`!") ;
         * int idxext = strXML.indexOf("EXT^`!") ;
         * int idxka  = strXML.indexOf("KEEPALIVE^`!") ;
         */

        /******************* code for unwanted character removal 26 April 2022 ***************************************/

        if (strXML.contains("CON^`!") || strXML.contains("MSG^`!") || strXML.contains("EXT^`!")
                || strXML.contains("KEEPALIVE^`!")) {

            if (strXML.contains("CON^`!")) {
                int b = strXML.indexOf("CON");
                strXML = strXML.substring(b);
                logger.info("SUBSTRING OF CON ::" + strXML);

            } else if (strXML.contains("MSG^`!")) {
                int c = strXML.indexOf("MSG");
                strXML = strXML.substring(c);
                logger.info("SUBSTRING OF MSG ::" + strXML);

            } else if (strXML.contains("EXT^`!")) {
                int d = strXML.indexOf("EXT");
                strXML = strXML.substring(d);
                logger.info("SUBSTRING OF EXT ::" + strXML);

            } else if (strXML.contains("KEEPALIVE^`!")) {
                int e = strXML.indexOf("KEEPALIVE");
                strXML = strXML.substring(e);
                logger.info("SUBSTRING OF KEEPALIVE ::" + strXML);

            } else {
                logger.info("IN ELSE PART OF CHEKING SEPARATE STRING ::" + strXML);
            }

            logger.info("BEFORE IF ::" + strXML);

            if (strXML.substring(0, 6).equalsIgnoreCase("CON^`!")
                    || strXML.substring(0, 6).equalsIgnoreCase("MSG^`!")
                    || strXML.substring(0, 6).equalsIgnoreCase("EXT^`!")
                    || strXML.substring(0, 12).equalsIgnoreCase("KEEPALIVE^`!")) {

                if (strXML.substring(strXML.length() - 5).equalsIgnoreCase("#END#")) {
                    lstVal.add(0, "SUCCESS");
                    lstVal.add(1, strXML);
                    return lstVal;
                }

                if (strXML.lastIndexOf("#END#") != -1) {
                    lstVal.add(0, "SUCCESS");
                    strXML = strXML.substring(0, strXML.lastIndexOf("#END#") + 5);
                    lstVal.add(1, strXML);
                    return lstVal;
                }

            } else {
                logger.info("XML PACKET IN ELSE " + strXML);
                logger.error("Error in start part of message");
                logger.error(strXML);
                writeInChannel("ACK!111", cnl);
                String localAddress = cnl.localAddress().toString();
                errorDump("", "", "", strXML, GlobalFunc.getDBDateTime(), "Error in start part of message",
                        localAddress.substring(localAddress.indexOf(":") + 1),
                        localAddress.substring(1, localAddress.indexOf(":")), this.sourceIP, this.sourcePort);
            }

            return lstVal;

        } else {
            logger.info("STRING STARTING WITH MSG,EXT,KEEPALIVE,CON NOT FOUND ");
        }

        return lstVal;
    }

    // -------------------------------------------------------------------------
    // Channel write helper
    // -------------------------------------------------------------------------

    private boolean writeInChannel(String strVal, Channel channel) {
        try {
            if (SMSIdleServer._strDebug.equalsIgnoreCase("Y"))
                logger.info("Response:" + strVal.toString());

            if (channel != null && channel.isWritable()) {
                ByteBuf time = Unpooled.buffer(strVal.length());
                time.writeBytes(strVal.getBytes());
                channel.write(time);
                channel.flush();
            }
        } catch (Exception e) {
            logger.error("Channel writting exception::" + e);
        }
        return true;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        logger.log(Level.WARN, "Unexpected exception from downstream.", e.getCause());
        writeInChannel("ACK!111", ctx.channel());
        ctx.channel().close();
    }

    // -------------------------------------------------------------------------
    // XML node helper
    // -------------------------------------------------------------------------

    private String checkGetValue(Node ndVal) {
        return (ndVal == null ? "" : ndVal.getTextContent().trim());
    }

    // -------------------------------------------------------------------------
    // IP / Port helpers
    // -------------------------------------------------------------------------

    private String getLnrIP(Channel channel) {
        try {
            String localAddress = channel.localAddress().toString();
            return localAddress.substring(1, localAddress.indexOf(":"));
        } catch (Exception e) {
            logger.error("Error in getIP", e);
        }
        return null;
    }

    private String getLnrPort(Channel channel) {
        try {
            String localAddress = channel.localAddress().toString();
            return localAddress.substring(localAddress.indexOf(":") + 1);
        } catch (Exception e) {
            logger.error("Error in getIP", e);
        }
        return null;
    }

    // -------------------------------------------------------------------------
    // Pipe ID helpers
    // -------------------------------------------------------------------------

    private String getPipeID() {
        int pipeCount;
        if (messagePriority == 1) {
            SMSIdleServer._nPipeCount.getAndIncrement();
            if (SMSIdleServer._nPipeCount.get() < SMSIdleServer._nPriorityMinPipe
                    || SMSIdleServer._nPipeCount.get() > SMSIdleServer._nPriorityMaxPipe) {
                SMSIdleServer._nPipeCount.set(SMSIdleServer._nPriorityMinPipe);
            }
            pipeCount = SMSIdleServer._nPipeCount.get();
        } else {
            SMSIdleServer._nNonPrioPipeCount.getAndIncrement();
            if (SMSIdleServer._nNonPrioPipeCount.get() < SMSIdleServer._nNonPriorityMinPipe
                    || SMSIdleServer._nNonPrioPipeCount.get() > SMSIdleServer._nNonPriorityMaxPipe) {
                SMSIdleServer._nNonPrioPipeCount.set(SMSIdleServer._nNonPriorityMinPipe);
            }
            pipeCount = SMSIdleServer._nNonPrioPipeCount.get();
        }
        return String.valueOf(pipeCount);
    }

    private String getPnPipeID(String appId, String deptId) {
        logger.info("inside the getPnPipeID");
        logger.info("category value is::::" + pnCheck.get(deptId + "-" + appId));
        int pipeCount = 0;
        if (pnCheck.get(deptId + "-" + appId) != null && pnCheck.get(deptId + "-" + appId).equalsIgnoreCase("Y")) {
            logger.info("======push notification match for APP_ID:" + appId + " DEPT_ID:" + deptId
                    + " Flag values is:" + pnCheck.get(deptId + "" + appId));
            SMSIdleServer._nPipePnCount.getAndIncrement();
            if (SMSIdleServer._nPipePnCount.get() < SMSIdleServer._nPriorityMinPipePn
                    || SMSIdleServer._nPipePnCount.get() > SMSIdleServer._nPriorityMaxPipePn) {
                SMSIdleServer._nPipePnCount.set(SMSIdleServer._nPriorityMinPipePn);
            }
            pipeCount = SMSIdleServer._nPipePnCount.get();
        }
        return String.valueOf(pipeCount);
    }

    // -------------------------------------------------------------------------
    // NEW: Template resolution
    // Calls USP_GET_SMS_TEMPLATE_DETAILS to replace placeholders in the
    // template text with values supplied in the incoming MESSAGE field.
    //
    // @param templateId     - ID of the template in TBL_SMS_TEMPLATE_DETAILS
    // @param templateValues - actual values separated by ~|~  (e.g. "235425~|~4")
    // @param con            - existing DB connection (passed in to avoid opening a new one)
    // @return               - resolved message text, or null if template not found
    //                         or placeholder count mismatches
    // -------------------------------------------------------------------------

    private String resolveTemplate(String templateId, String templateValues, Connection con) throws Exception {

        CallableStatement tmplSt = null;
        ResultSet rs = null;

        try {
            logger.info("resolveTemplate called with TEMPLATE_ID=" + templateId
                    + " TEMPLATE_VALUES=" + templateValues);

            tmplSt = con.prepareCall("{call USP_GET_SMS_TEMPLATE_DETAILS(?, ?)}");
            tmplSt.setInt(1, Integer.parseInt(templateId));
            tmplSt.setString(2, templateValues);
            rs = tmplSt.executeQuery();

            if (rs.next()) {
                String resolvedMessage = rs.getString("Message");
                logger.info("resolveTemplate result=" + resolvedMessage);

                return resolvedMessage;
            }

        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (tmplSt != null)
                    tmplSt.close();
            } catch (Exception e) {
                logger.error("Error closing template statement", e);
            }
        }

        return null;
    }

    // -------------------------------------------------------------------------
    // Core SMS processing
    // -------------------------------------------------------------------------

    private String getResponse(String strReq, String localAddress, Channel cnl) {

        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = null;
        DocumentBuilder docBuilder = null;
        Connection con = null;
        CallableStatement st = null;
        StringReader strReader = null;
        StringBuffer sbRes = new StringBuffer();
        sbRes.append("ACK!");

        try {
            strReq = replaceUnWantedCharXML(strReq);

            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            strReader = new StringReader(strReq);
            doc = docBuilder.parse(new InputSource(strReader));

            String DEPT                   = checkGetValue(doc.getElementsByTagName("DEPT").item(0));
            String APPID                  = checkGetValue(doc.getElementsByTagName("APPID").item(0));
            String DEPTMSGID              = checkGetValue(doc.getElementsByTagName("DEPTMSGID").item(0));
            String MOBILE                 = checkGetValue(doc.getElementsByTagName("MOBILE").item(0));
            String MESSAGE                = checkGetValue(doc.getElementsByTagName("MESSAGE").item(0));
            String FROMDATETIME           = checkGetValue(doc.getElementsByTagName("FROMDATETIME").item(0));
            String TODATETIME             = checkGetValue(doc.getElementsByTagName("TODATETIME").item(0));
            String NODELIVERYTIMEFROM     = checkGetValue(doc.getElementsByTagName("NODELIVERYTIMEFROM").item(0));
            String NODELIVERYTIMETO       = checkGetValue(doc.getElementsByTagName("NODELIVERYTIMETO").item(0));
            String HTTPMODE               = checkGetValue(doc.getElementsByTagName("HTTPMODE").item(0));
            String TRN_GENERATE_TIMESTAMP = checkGetValue(doc.getElementsByTagName("TRN_GENERATE_TIMESTAMP").item(0));
            String info1                  = checkGetValue(doc.getElementsByTagName("REMARKS").item(0));
            String info2                  = checkGetValue(doc.getElementsByTagName("REMARKS1").item(0));
            String info3                  = checkGetValue(doc.getElementsByTagName("REMARKS2").item(0));
            String info4                  = checkGetValue(doc.getElementsByTagName("REMARKS3").item(0));
            String dupchk                 = checkGetValue(doc.getElementsByTagName("DUPLICATE_CHECK").item(0));
            String alt_channel            = checkGetValue(doc.getElementsByTagName("ALT_CHANNEL").item(0));
            String popSenderAdd           = checkGetValue(doc.getElementsByTagName("POP_SENDER_ADDR").item(0));

            // -----------------------------------------------------------------
            // NEW: Read template identifier from the incoming XML.
            // If TEMPLATE_ID is provided, MESSAGE contains the template values
            // separated by ~|~ and is resolved through USP_GET_SMS_TEMPLATE_DETAILS.
            // -----------------------------------------------------------------
            String TEMPLATE_ID     = checkGetValue(doc.getElementsByTagName("TEMPLATE_ID").item(0));

            if (info4.trim().equals(""))
                info4 = "JAVA";
            else
                info4 += " JAVA";

            try {
                String strError = "ACK!MSGSTATUS=" + "FALSE" + ";FATAL=" + "TRUE" + ";DEPT=" + DEPT + ";APPID=" + APPID
                        + ";DEPTMSGID=" + DEPTMSGID + ";INFO1=" + info1 + ";INFO2=" + info2 + ";INFO3=" + info3
                        + ";INFO4=" + info4 + ";TRN_GENERATE_TIMESTAMP=" + TRN_GENERATE_TIMESTAMP + ";MOBILE=" + MOBILE
                        + ";ISGMSGID=;";

                // --- Standard validations --------------------------------
                if (DEPT.equals("")) {
                    logger.info("ERROR=Department Id is blank for source_IP " + sourceIP);
                    return strError + "ERROR=Department Id is blank" + SeperatorString;

                } else if (APPID.equals("")) {
                    logger.info("ERROR=Application Id is blank for source_IP " + sourceIP);
                    return strError + "ERROR=Application Id is blank" + SeperatorString;

                } else if (!lstVD.contains((DEPT + "-" + APPID))) {
                    logger.info("ERROR=Department id is disabled for source_IP " + sourceIP);
                    return strError + "ERROR=Department id is disabled" + SeperatorString;

                } else if (MOBILE.equals("")) {
                    logger.info("ERROR=Mobile number is blank for source_IP " + sourceIP);
                    return strError + "ERROR=Mobile number is blank" + SeperatorString;
                }

                // -----------------------------------------------------------------
                // NEW: Template resolution block.
                // If TEMPLATE_ID is present, treat MESSAGE as the template values.
                // -----------------------------------------------------------------
                if (!TEMPLATE_ID.equals("")) {

                    logger.info("TEMPLATE_ID provided=" + TEMPLATE_ID + ", resolving values from MESSAGE...");

                    con = this.dbpool.getConnection();

                    String resolvedMessage = resolveTemplate(TEMPLATE_ID, MESSAGE, con);

                    if (resolvedMessage == null || resolvedMessage.trim().equals("")) {
                        logger.info("ERROR=Template resolution failed for TEMPLATE_ID=" + TEMPLATE_ID
                                + " source_IP=" + sourceIP);
                        return strError + "ERROR=Template resolution failed" + SeperatorString;
                    }

                    if (resolvedMessage.equalsIgnoreCase("Template not found")
                            || resolvedMessage.equalsIgnoreCase("Template mismatched")) {
                        logger.info("ERROR=" + resolvedMessage + " for TEMPLATE_ID=" + TEMPLATE_ID
                                + " source_IP=" + sourceIP);
                        return strError + "ERROR=" + resolvedMessage + SeperatorString;
                    }

                    // Override the MESSAGE with the fully resolved template text
                    MESSAGE = resolvedMessage;
                    logger.info("MESSAGE resolved from template: " + MESSAGE);

                } else {
                    // No template — open DB connection for later use (existing behaviour)
                    con = this.dbpool.getConnection();
                }
                // -----------------------------------------------------------------
                // End of template resolution block
                // -----------------------------------------------------------------

                // --- MESSAGE validations (run after template resolution) ------
                if (MESSAGE.equals("")) {
                    logger.info("ERROR=Message Text is blank for source_IP " + sourceIP);
                    return strError + "ERROR=Message Text is blank" + SeperatorString;

                } else if (MESSAGE.length() > 500) {
                    logger.info("ERROR=Message length cannot be greater than 500 characters for source_IP " + sourceIP);
                    return strError + "ERROR=Message length cannot be greater than 500 characters" + SeperatorString;
                }

                // --- Mobile number validations --------------------------------
                String[] strArrMob = null;
                String strMob = MOBILE;

                if (strMob.indexOf(",") != -1) {
                    strArrMob = strMob.split("\\,");
                } else {
                    strArrMob = new String[1];
                    strArrMob[0] = strMob;
                }

                for (int i = 0; i < strArrMob.length; i++) {
                    strArrMob[i] = replaceInMobile(strArrMob[i]);

                    if (strArrMob[i].equals("")) {
                        logger.info("ERROR=Mobile number is blank for source_IP " + sourceIP);
                        return strError + "ERROR=Mobile number is blank" + SeperatorString;

                    } else if (strArrMob[i].length() < 10 || strArrMob[i].length() > 15) {
                        logger.info("ERROR=Mobile number is not valid for source_IP " + sourceIP);
                        return strError + "ERROR=Mobile number is not valid" + SeperatorString;

                    } else if (lstIM.contains(strArrMob[i])) {
                        logger.info("ERROR=Mobile number is invalid for source_IP " + sourceIP);
                        return strError + "ERROR=Mobile number is invalid" + SeperatorString;

                    } else {
                        Scanner sc = new Scanner(strArrMob[i]);
                        if (!sc.hasNextBigInteger()) {
                            logger.info("ERROR=Mobile number is not numeric for source_IP " + sourceIP);
                            return strError + "ERROR=Mobile number is not numeric" + SeperatorString;
                        }
                    }
                }

                // --- Priority routing -----------------------------------------
                String reglstStr[];
                for (String reglst : lpsd) {
                    reglstStr = reglst.split("\\|");
                    if (reglstStr[0].contains(DEPT + "-" + APPID)) {
                        messagePriority = Integer.valueOf(reglstStr[1]);
                        break;
                    }
                }

                // As discussed with Manesh Sir on 20 April 2026 for uniquely generate Message ID on java
                // Develop BY Shubham Salunkhe By comment down the Procedure call
                st = con.prepareCall(
                        "{call USP_INSERTONLY_PUSH_SMS_JAVA_DUPCHK(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                for (int i = 0; i < strArrMob.length; i++) {

                    nPipeId   = getPipeID();
                    nPipePnId = getPnPipeID(APPID, DEPT);

                    try {
                        long pmsgid = 0;

                        // As discussed with Manesh Sir on 20 April 2026
                        // Develop BY Shubham Salunkhe
                        pmsgid = (long) generate9DigitKey(localPort);

                        // AS discussed with manesh on 23-May-2026 doing development for Time Based Message Processing
                        logger.info("FROMDATETIME=" + FROMDATETIME + "=TODATETIME=" + TODATETIME);

                        if (!FROMDATETIME.equals("") && !TODATETIME.equals("")) {
                            logger.info("Inside the time based Functionality");
                            st.setString(1,  DEPT);
                            st.setString(2,  APPID);
                            st.setString(3,  null);
                            st.setString(4,  popSenderAdd);
                            st.setString(5,  DEPTMSGID);
                            st.setString(6,  null);
                            st.setString(7,  strArrMob[i]);
                            st.setString(8,  MESSAGE);
                            st.setString(9,  FROMDATETIME);
                            st.setString(10, TODATETIME);
                            st.setString(11, null);
                            st.setString(12, alt_channel);
                            st.setString(13, null);
                            st.setString(14, null);
                            st.setString(15, null);
                            st.setString(16, NODELIVERYTIMEFROM);
                            st.setString(17, NODELIVERYTIMETO);
                            st.setString(18, null);
                            st.setString(19, HTTPMODE);
                            st.setString(20, info1);
                            st.setString(21, info2);
                            st.setString(22, info3);
                            st.setString(23, info4);
                            st.setString(24, TRN_GENERATE_TIMESTAMP);
                            st.setString(25, null);
                            st.setString(26, null);
                            st.setString(27, null);
                            st.setString(28, null);
                            st.setString(29, null);
                            st.setString(30, null);
                            st.setString(31, null);
                            st.setString(32, null);
                            st.setString(33, localPort);
                            st.setString(34, localIP);
                            st.setString(35, strReq);
                            st.setString(36, "11");
                            st.setString(37, dupchk);
                            st.setString(38, sourceIP);
                            st.setString(39, null);
                            st.setString(40, nPipePnId);
                            st.registerOutParameter(41, Types.NUMERIC);
                            st.registerOutParameter(42, Types.VARCHAR);
                            logger.info("Procedure call=" + st.executeUpdate());
                            logger.info("st.getLong(41)=" + st.getLong(41));
                            logger.info("st.getLong(42)=" + st.getString(42));
                        }

                        if (pmsgid > 0) {
                            String SendPNmessageflag = this.prop.getProperty("SendPNmessageFlag");
                            logger.info("SendPNmessageflag :: " + SendPNmessageflag);

                            String categoryK = pnCheck.get(DEPT + "-" + APPID);
                            logger.info("category==" + categoryK);

                            if (!(!FROMDATETIME.equals("") && !TODATETIME.equals(""))) {
                                logger.info("inside the if condition 111");

                                if (categoryK.toUpperCase().equals("K")) {
                                    try {
                                        KafkaDataSend kafkaResponseData = new KafkaDataSend();
                                        kafkaResponseData.setMsg_id(String.valueOf(pmsgid));
                                        kafkaResponseData.setDept(DEPT);
                                        kafkaResponseData.setAppid(APPID);
                                        kafkaResponseData.setMobile(MOBILE);
                                        kafkaResponseData.setDeptmsgid(DEPTMSGID);
                                        kafkaResponseData.setMessage(MESSAGE);
                                        kafkaResponseData.setFromdatetime(FROMDATETIME);
                                        kafkaResponseData.setTodatetime(TODATETIME);
                                        kafkaResponseData.setNodeliverytimefrom(NODELIVERYTIMEFROM);
                                        kafkaResponseData.setNodeliverytimeto(NODELIVERYTIMETO);
                                        kafkaResponseData.setHttpmode(HTTPMODE);
                                        kafkaResponseData.setRemarks(info1);
                                        kafkaResponseData.setTrn__generate_timestamp(TRN_GENERATE_TIMESTAMP);
                                        kafkaResponseData.setDuplicate_check(dupchk);
                                        kafkaResponseData.setRemarks1(info2);
                                        kafkaResponseData.setRemarks2(info3);
                                        kafkaResponseData.setTopic_name("smsgw.request");

                                        logger.info("Before the kafkaPreoducer.kafkaProcuder");
                                        KafkaConfig kafkaPreoducer = new KafkaConfig();
                                        kafkaPreoducer.kafkaProcuder(kafkaResponseData);
                                        logger.info("After the kafkaPreoducer.kafkaProcuder");

                                    } catch (Exception ex) {
                                        logger.error("Error Occurred During Kafka Implementation=", ex);
                                    }
                                }
                            }

                            if ("true".equals(SendPNmessageflag)) {
                                try {
                                    String category = pnCheck.get(DEPT + "-" + APPID);
                                    logger.info("category ::" + category + " mobile no::" + MOBILE);

                                    if ("Y".equalsIgnoreCase(category)) {
                                        String PNMessageURL = this.prop.getProperty("PNMessageURL");
                                        logger.info("PNMessageURL::" + PNMessageURL);

                                        URL url = new URL(PNMessageURL);
                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                        conn.setRequestMethod("POST");
                                        conn.setRequestProperty("Content-Type", "application/json");
                                        conn.setConnectTimeout(6000);
                                        conn.setReadTimeout(10000);
                                        conn.setDoOutput(true);

                                        JSONObject requestBody = new JSONObject();
                                        requestBody.put("MSG_ID",                 pmsgid);
                                        requestBody.put("DEPTID",                 DEPT);
                                        requestBody.put("APPID",                  APPID);
                                        requestBody.put("MOBILE_NO",              MOBILE);
                                        requestBody.put("DEPTMSGID",              DEPTMSGID);
                                        requestBody.put("MESSAGE",                MESSAGE);
                                        requestBody.put("FROMDATETIME",           FROMDATETIME);
                                        requestBody.put("TODATETIME",             TODATETIME);
                                        requestBody.put("NODELIVERYTIMEFROM",     NODELIVERYTIMEFROM);
                                        requestBody.put("NODELIVERYTIMETO",       NODELIVERYTIMETO);
                                        requestBody.put("HTTPMODE",               HTTPMODE);
                                        requestBody.put("REMARKS",                info1);
                                        requestBody.put("DUPLICATE_CHECK",        dupchk);
                                        requestBody.put("ALT_CHANNEL",            alt_channel);
                                        requestBody.put("TRN_GENERATE_TIMESTAMP", TRN_GENERATE_TIMESTAMP);
                                        requestBody.put("REMARKS1",               info2);
                                        requestBody.put("REMARKS2",               info3);

                                        String jsonString = requestBody.toString().replaceAll("\\\\", "");
                                        logger.info("jsonString request ::" + jsonString);

                                        DataOutputStream wr = null;
                                        wr = new DataOutputStream(conn.getOutputStream());
                                        wr.writeBytes(jsonString);
                                        wr.flush();

                                        int responseCode = conn.getResponseCode();
                                        logger.info("responseCode ::" + responseCode);

                                        if (responseCode == HttpURLConnection.HTTP_OK) {
                                            BufferedReader in = new BufferedReader(
                                                    new InputStreamReader(conn.getInputStream()));
                                            String inputLine;
                                            StringBuilder response = new StringBuilder();
                                            while ((inputLine = in.readLine()) != null) {
                                                response.append(inputLine);
                                            }
                                            in.close();
                                            logger.info("response msg deatils :: " + response.toString());
                                        } else {
                                            logger.info("request failed with http status code: " + responseCode);
                                        }

                                        wr.close();
                                        conn.disconnect();
                                    }

                                } catch (Exception e) {
                                    logger.error("exception details :: " + e.getMessage());
                                }
                            }

                            sbRes.append("MSGSTATUS=" + "TRUE" + ";FATAL=" + "FALSE" + ";DEPT=" + DEPT + ";APPID="
                                    + APPID + ";DEPTMSGID=" + DEPTMSGID + ";INFO1=" + info1 + ";INFO2=" + info2
                                    + ";INFO3=" + info3 + ";INFO4=" + info4 + ";TRN_GENERATE_TIMESTAMP="
                                    + TRN_GENERATE_TIMESTAMP + ";MOBILE=" + strArrMob[i] + ";ISGMSGID=" + pmsgid
                                    + ";ERROR=" + this.errorMsg);
                            sbRes.append(SeperatorString);

                        } else {
                            String errMsg = st.getString(42);
                            if (errMsg != null && errMsg.trim().indexOf("Duplicate Message for") != -1) {
                                sbRes.append("MSGSTATUS=" + "TRUE" + ";FATAL=" + "TRUE" + ";DEPT=" + DEPT + ";APPID="
                                        + APPID + ";DEPTMSGID=" + DEPTMSGID + ";INFO1=" + info1 + ";INFO2=" + info2
                                        + ";INFO3=" + info3 + ";INFO4=" + info4 + ";TRN_GENERATE_TIMESTAMP="
                                        + TRN_GENERATE_TIMESTAMP + ";MOBILE=" + strArrMob[i] + ";ISGMSGID=;ERROR="
                                        + st.getString(42));
                                sbRes.append(SeperatorString);
                            } else {
                                sbRes.append("MSGSTATUS=" + "FALSE" + ";FATAL=" + "TRUE" + ";DEPT=" + DEPT + ";APPID="
                                        + APPID + ";DEPTMSGID=" + DEPTMSGID + ";INFO1=" + info1 + ";INFO2=" + info2
                                        + ";INFO3=" + info3 + ";INFO4=" + info4 + ";TRN_GENERATE_TIMESTAMP="
                                        + TRN_GENERATE_TIMESTAMP + ";MOBILE=" + strArrMob[i] + ";ISGMSGID=;ERROR="
                                        + st.getString(42));
                                sbRes.append(SeperatorString);
                            }
                        }

                    } catch (SQLException ex) {
                        throw ex;

                    } catch (Exception e) {
                        logger.error("Error :=> ", e);
                        this.errorMsg = e.getMessage();
                        sbRes.append("MSGSTATUS=" + "FALSE" + ";FATAL=" + "TRUE" + ";DEPT=" + DEPT + ";APPID=" + APPID
                                + ";DEPTMSGID=" + DEPTMSGID + ";INFO1=" + info1 + ";INFO2=" + info2 + ";INFO3=" + info3
                                + ";INFO4=" + info4 + ";TRN_GENERATE_TIMESTAMP=" + TRN_GENERATE_TIMESTAMP + ";MOBILE="
                                + strArrMob[i] + ";ISGMSGID=;ERROR=" + this.errorMsg);
                        sbRes.append(SeperatorString);
                        errorDump(DEPT, APPID, DEPTMSGID, strReq, GlobalFunc.getDBDateTime(),
                                "Error in messageReceived::" + e.getMessage(),
                                localAddress.substring(localAddress.indexOf(":") + 1),
                                localAddress.substring(1, localAddress.indexOf(":")), sourceIP, sourcePort);
                    }

                    if (i < strArrMob.length - 1) {
                        writeInChannel(sbRes.toString(), cnl);
                        sbRes = new StringBuffer();
                        sbRes.append("ACK!");
                    }
                }

            } catch (Exception e) {
                logger.log(Level.ERROR, e);
                this.errorMsg = e.getMessage();
                sbRes.append("MSGSTATUS=" + "FALSE" + ";FATAL=" + "TRUE" + ";DEPT=" + DEPT + ";APPID=" + APPID
                        + ";DEPTMSGID=" + DEPTMSGID + ";INFO1=" + info1 + ";INFO2=" + info2 + ";INFO3=" + info3
                        + ";INFO4=" + info4 + ";TRN_GENERATE_TIMESTAMP=" + TRN_GENERATE_TIMESTAMP + ";MOBILE=" + MOBILE
                        + ";ISGMSGID=;ERROR=" + this.errorMsg);
                sbRes.append(SeperatorString);
                errorDump(DEPT, APPID, DEPTMSGID, strReq, GlobalFunc.getDBDateTime(),
                        "Error in messageReceived::" + e.getMessage(),
                        localAddress.substring(localAddress.indexOf(":") + 1),
                        localAddress.substring(1, localAddress.indexOf(":")), sourceIP, sourcePort);

            } finally {
                try {
                    if (st != null)
                        st.close();
                    if (con != null)
                        this.dbpool.releaseConnection(con);
                } catch (Exception e) {
                    logger.error("DB close on getResponse", e);
                }
            }

        } catch (Exception e) {
            sbRes.append("111-Error in parsing the message");
            logger.error(strReq + "\n" + sbRes.toString(), e);
            errorDump("", "", "", strReq, GlobalFunc.getDBDateTime(), "Error in messageReceived::" + e.getMessage(),
                    localAddress.substring(localAddress.indexOf(":") + 1),
                    localAddress.substring(1, localAddress.indexOf(":")), sourceIP, sourcePort);

        } finally {
            if (strReader != null) {
                strReader.close();
            }
        }

        return sbRes.toString();
    }

    // -------------------------------------------------------------------------
    // Special character utilities
    // -------------------------------------------------------------------------

    private boolean FN_CheckSpecialChars(String message) {
        if (message.length() > 0) {
            int asciiNum = 0;
            for (int i = 0; i < message.length(); i++) {
                asciiNum = message.codePointAt(i);
                // !^/<>|
                if (asciiNum == 33
                        || asciiNum == 94
                        /* || asciiNum == 47 */
                        || asciiNum == 60
                        || asciiNum == 62
                        || asciiNum == 124
                        /* || asciiNum == 38 */) {
                    return true;
                }
            }
        }
        return false;
    }

    private String RemoveSpecialChars(String message) {
        String valRet = "";
        if (message.length() > 0) {
            int asciiNum = 0;
            for (int i = 0; i < message.length(); i++) {
                asciiNum = message.codePointAt(i);
                if (asciiNum == 33
                        || asciiNum == 94
                        || asciiNum == 47
                        || asciiNum == 60
                        || asciiNum == 62
                        || asciiNum == 124
                        /* || asciiNum == 38 */) {
                    continue;
                } else {
                    valRet += message.charAt(i);
                }
            }
        } else {
            valRet = message;
        }
        return valRet;
    }

    // -------------------------------------------------------------------------
    // Error dump to DB
    // -------------------------------------------------------------------------

    private boolean errorDump(String messageDeptID, String messageAppID, String messageDeptRefMsgID,
            String rawMessageText, String ErrDtTime, String LastError,
            String ServerPort, String ServerIP, String sourceIP, String sourcePort) {

        Connection con = null;
        CallableStatement st = null;

        try {
            con = this.dbpool.getConnection();
            st = con.prepareCall("{call USP_LIST_SRVR_ERROR_i(?,?,?,?,?,?,?,?,?)}");
            st.setString(1, messageDeptID);
            st.setString(2, messageAppID);
            st.setString(3, messageDeptRefMsgID);
            st.setString(4, rawMessageText);
            st.setString(5, ErrDtTime);
            st.setString(6, LastError);
            st.setString(7, ServerPort);
            st.setString(8, ServerIP);
            st.setString(9, sourceIP);
            // st.setString(10, sourcePort);
            st.executeUpdate();

        } catch (Exception e) {
            logger.error("Error :=>", e);
        } finally {
            try {
                if (st != null)
                    st.close();
                if (con != null)
                    this.dbpool.releaseConnection(con);
            } catch (Exception e) {
            }
        }

        return true;
    }

    // -------------------------------------------------------------------------
    // Unique message ID generation
    // -------------------------------------------------------------------------

    public long generate9DigitKey(String localPort) {
        long uniqueKey = 0;
        try {
            long combined = (System.currentTimeMillis() ^ System.nanoTime()) ^ Math.abs(localPort.hashCode());
            uniqueKey = Integer.valueOf(localPort) + Math.abs((long) (combined % 1000000000L));
            logger.info("uniqueKey111=" + uniqueKey);
        } catch (Exception ex) {
            logger.error("Error Occuurred at generate9DigitKey ", ex);
        }
        return uniqueKey;
    }

}
