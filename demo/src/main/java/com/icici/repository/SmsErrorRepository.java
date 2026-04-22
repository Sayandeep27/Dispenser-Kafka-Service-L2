package com.icici.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.icici.entity.SmsGatewayErrorBean;
 
public interface SmsErrorRepository extends JpaRepository<SmsGatewayErrorBean, Integer> {
 
}