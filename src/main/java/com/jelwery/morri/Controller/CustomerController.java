package com.jelwery.morri.Controller;

import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PatchMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable("customerId") String customerId, @RequestBody Customer customer) throws Exception {
        return customerService.updateCustomer(customerId,customer);
    }
    @GetMapping("/")
    public List<Customer> getAllCustomers() throws Exception {
        return customerService.getAllCustomers();
    }
    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") String customerId) throws Exception {
        return customerService.deleteCustomer(customerId);
    }
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") String customerId) throws Exception {
        return customerService.getOneCustomer(customerId);
    }
}
