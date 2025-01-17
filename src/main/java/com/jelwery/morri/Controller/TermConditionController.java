package com.jelwery.morri.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelwery.morri.Model.TermCondition;
import com.jelwery.morri.Service.TermConditionService;

@CrossOrigin(origins = "http://localhost:3000")  
@RequestMapping("/termAndCondition")
@RestController
public class TermConditionController {
    @Autowired
    private TermConditionService Service;

    @PostMapping("/create")
    public TermCondition create(@Valid @RequestBody TermCondition service) throws  Exception {

        return Service.create(service);
    }
     @GetMapping("/")
    public List<TermCondition> getAllServices() {
        return Service.getAll();
    }

    @PutMapping("/{id}")
    public TermCondition updateService(@PathVariable String id, @RequestBody TermCondition service) {
        return Service.updateService(id, service);
    } 
}
