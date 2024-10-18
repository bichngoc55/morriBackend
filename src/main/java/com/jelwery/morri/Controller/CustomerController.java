package com.jelwery.morri.Controller;

import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/customer")
@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    //create
    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) throws Exception {
        return customerService.createCustomer(customer);
    }
}
