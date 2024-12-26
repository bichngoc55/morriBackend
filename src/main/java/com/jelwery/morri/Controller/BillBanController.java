package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.BillBan;
import com.jelwery.morri.Service.BillBanService;

@RestController
@RequestMapping("/billBan")
public class BillBanController {
    @Autowired
    private BillBanService billBanService;

    @PostMapping("/create")
    public BillBan createBillBan(@RequestBody BillBan billBan) {
        return billBanService.createBillBan(billBan);
    }
     @GetMapping("/")
    public List<BillBan> getAllBillBan() {
        return billBanService.getAllBillBan();
    } 
    @PatchMapping("/update/{billBanId}")
    public BillBan updateBillBan(@PathVariable("billBanId") String billBanId, @RequestBody BillBan updatedBillBan) {
        return billBanService.updateBillBan(billBanId, updatedBillBan);
    }
}
