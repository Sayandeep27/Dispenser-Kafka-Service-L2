package com.icici.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.icici.entity.SmsSuccessDetails;
 
public interface SmsSuccessRepository extends JpaRepository<SmsSuccessDetails, Integer> {
 
}