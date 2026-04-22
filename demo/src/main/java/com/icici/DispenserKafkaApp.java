package com.icici;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
 
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.icici.*" })
public class DispenserKafkaApp
{
    public static void main(String[] args)
    {
        System.out.println(" Dispenser Application Started");
        SpringApplication.run(DispenserKafkaApp.class, args);
    }
}
 