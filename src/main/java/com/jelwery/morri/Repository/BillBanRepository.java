package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jelwery.morri.Model.BillBan;
import com.jelwery.morri.Model.OrderDetail;


public interface BillBanRepository extends MongoRepository<BillBan, String> {
    List<BillBan> findByStatus(String status);
    List<BillBan> findByStaffName(String name);
    List<BillBan> findByStaffPhoneNumber(String phoneNumber);
    List<BillBan> findByCustomerName(String name);
    List<BillBan> findByCustomerPhoneNumber(String phoneNumber);
    List<BillBan> findByCreateAtBetween(LocalDateTime start, LocalDateTime end);
    List<BillBan> findByCreateAt(LocalDateTime createAt);
    @Query("{'staff._id': ?0, 'createAt': {$gte: ?1, $lt: ?2}, 'status': 'COMPLETED'}")
    List<BillBan> findCompletedBillsByEmployeeIdAndDateRange(String employeeId, LocalDateTime startDate, LocalDateTime endDate);

    default int countProductsByEmployeeIdAndYearAndMonth(String employeeId, int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1);
        
        List<BillBan> bills = findCompletedBillsByEmployeeIdAndDateRange(employeeId, startDate, endDate);
        
        return bills.stream()
            .filter(bill -> bill.getOrderDetails() != null)
            .flatMap(bill -> bill.getOrderDetails().stream())
            .mapToInt(OrderDetail::getQuantity)
            .sum();
    }


}
