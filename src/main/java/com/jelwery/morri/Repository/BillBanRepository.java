package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.BillBan;


public interface BillBanRepository extends MongoRepository<BillBan, String> {
    List<BillBan> findByStatus(String status);
    List<BillBan> findByStaffName(String name);
    List<BillBan> findByStaffPhoneNumber(String phoneNumber);
    List<BillBan> findByCustomerName(String name);
    List<BillBan> findByCustomerPhoneNumber(String phoneNumber);
    List<BillBan> findByCreateAtBetween(LocalDateTime start, LocalDateTime end);
    List<BillBan> findByCreateAt(LocalDateTime createAt);


}
