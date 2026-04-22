package com.icici.repository;
 
import java.util.List;
import java.util.stream.Collectors;
 
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.icici.dto.ChannelDataBean;
import com.icici.dto.DispenseOutputBean;
 
public class DispenserRepoImpl implements DispenserRepo {
    
    private static Logger log = LogManager.getLogger();
    
    @Autowired
    private EntityManager entityManager;
    
    public List<DispenseOutputBean> fechSmsGatewayDetails(ChannelDataBean channelDataBean)
    {
        log.info("Inside the fechSmsGatewayDetails");
        StoredProcedureQuery query = this.entityManager
                .createStoredProcedureQuery("USP_DISP_SELECT_COE_KAFKA");
        
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        
        query.setParameter(1, "FT42");
        query.setParameter(2, "FT42");
        query.setParameter(3, "Y");
        
        log.info("Before ResultSet");
        List<Object[]> rawData = query.getResultList();
        List<DispenseOutputBean> dtoList = rawData.stream()
            .map(objArray -> new DispenseOutputBean((String) objArray[0],(String) objArray[1] ))
            .collect(Collectors.toList());
        dtoList.stream().forEach(s -> System.out.println((s)));
        log.info("inside the sendSMSDetails 0003333");
        
        return dtoList;
            
    }
 
}
 
 