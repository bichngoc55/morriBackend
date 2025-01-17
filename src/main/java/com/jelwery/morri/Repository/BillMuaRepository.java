package com.jelwery.morri.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jelwery.morri.Model.BillMua;

public interface BillMuaRepository  extends MongoRepository<BillMua, String> {
    @Query("{'staffId._id': ?0, 'createdAt': {$gte: ?1, $lt: ?2}, 'status': 1}")
    List<BillMua> findCompletedBillsByEmployeeIdAndDateRange(String employeeId, LocalDateTime startDate, LocalDateTime endDate);

    default int countProductsByEmployeeIdAndYearAndMonth(String employeeId, int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1);
        
        List<BillMua> bills = findCompletedBillsByEmployeeIdAndDateRange(employeeId, startDate, endDate);
        
        return bills.stream()
            .filter(bill -> bill.getDsSanPhamDaMua() != null)
            .flatMap(bill -> bill.getDsSanPhamDaMua().stream())
            .mapToInt(product -> 1) 
            .sum();
    }
}
