package com.jelwery.morri.Controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Exception.ResourceNotFoundException;
import com.jelwery.morri.Model.BillBan;
import com.jelwery.morri.Model.BillBan.BillStatus;
import com.jelwery.morri.Repository.BillBanRepository;
import com.jelwery.morri.Service.BillBanService;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/billBan")
public class BillBanController {
    @Autowired
    private BillBanService billBanService;
    @Autowired
    private BillBanRepository billBanRepository;

    @PostMapping("/create")
    public BillBan createBillBan(@RequestBody BillBan billBan) {
        return billBanService.createBillBan(billBan);
    }
     @GetMapping("/")
    public List<BillBan> getAllBillBan() {
        return billBanService.getAllBillBan();
    } 
    @GetMapping("/{billBanId}")
    public BillBan getBillBan(@PathVariable("billBanId") String billBanId) 
    {return billBanService.getBillBanById(billBanId);}
    
    @PatchMapping("/update/{billBanId}")
    public BillBan updateBillBan(@PathVariable("billBanId") String billBanId, @RequestBody BillBan updatedBillBan) {
        return billBanService.updateBillBan(billBanId, updatedBillBan);
    }
     @GetMapping("/today")
    public List<BillBan> getToday() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return billBanRepository.findByCreateAtBetween(startOfDay, endOfDay);
    }

    @GetMapping("/thisweek")
    public List<BillBan> getCurrentWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .withHour(23).withMinute(59).withSecond(59);
        return billBanRepository.findByCreateAtBetween(startOfWeek, endOfWeek);
    }

    @GetMapping("/thismonth")
    public List<BillBan> getCurrentMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1)
            .withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
            .withHour(23).withMinute(59).withSecond(59);
        return billBanRepository.findByCreateAtBetween(startOfMonth, endOfMonth);
    }

    @GetMapping("/thisyear")
    public List<BillBan> getCurrentYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfYear = now.withDayOfYear(1)
            .withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfYear = now.with(TemporalAdjusters.lastDayOfYear())
            .withHour(23).withMinute(59).withSecond(59);
        return billBanRepository.findByCreateAtBetween(startOfYear, endOfYear);
    }
 

@DeleteMapping("/{billBanId}")
public ResponseEntity<Void> deleteBillBan(@PathVariable String billBanId) {
    try {
        billBanService.updateBillBanStatus(billBanId, BillStatus.CANCELLED);
        return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
}
