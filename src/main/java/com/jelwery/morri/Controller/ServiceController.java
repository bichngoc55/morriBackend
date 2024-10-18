package com.jelwery.morri.Controller;

import com.jelwery.morri.Model.Service;
import com.jelwery.morri.Service.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/service")
@RestController
public class ServiceController {
    @Autowired
    private DichVuService dichVuService;

    //create
    @PostMapping("/create")
    public Service createService(@Valid @RequestBody Service service) throws  Exception {
        System.out.println(service);
        return dichVuService.createService(service);
    }
}
