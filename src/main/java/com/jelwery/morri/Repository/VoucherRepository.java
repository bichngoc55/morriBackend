package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jelwery.morri.Model.Voucher;

public interface VoucherRepository extends MongoRepository<Voucher,String> {
    
    Optional<Voucher> findByVoucherCode(String voucherCode);
     
    List<Voucher> findByStatus(String status);
     
    @Query("{ 'startDate' : { $lte: ?0 }, 'endDate' : { $gte: ?0 }, 'status': 'ACTIVE' }")
    List<Voucher> findActiveVouchers(LocalDateTime now); 
    List<Voucher> findByDiscountBetween(Double minDiscount, Double maxDiscount);
     
    @Query("{ 'voucherName' : { $regex: ?0, $options: 'i' }}")
    List<Voucher> findByVoucherNameContainingIgnoreCase(String voucherName);
     
    @Query("{ 'endDate' : { $lt: ?0 }}")
    List<Voucher> findExpiredVouchers(LocalDateTime now); 
    @Query("{ 'endDate' : { $gte: ?0, $lte: ?1 }, 'status': 'ACTIVE' }")
    List<Voucher> findSoonToExpireVouchers(LocalDateTime now, LocalDateTime sevenDaysLater);
     
    Long countByStatus(String status);
     
    List<Voucher> findByStatusAndDiscountBetween(String status, Double minDiscount, Double maxDiscount);
     
    void deleteByVoucherCode(String voucherCode);
     
    boolean existsByVoucherCode(String voucherCode);
    List<Voucher> findByEndDateAfterAndStartDateBefore(LocalDateTime endDate, LocalDateTime startDate);

}
