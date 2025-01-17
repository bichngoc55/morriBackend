package com.jelwery.morri.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.jelwery.morri.DTO.CustomerDTO;
import com.jelwery.morri.Model.Customer;
import com.jelwery.morri.Service.CustomerService;

 @CrossOrigin(origins = "http://localhost:3000")  

@RestController
@RequestMapping("/customer")

public class CustomerController {
    @Autowired
    CustomerService customerService;

    //create
    @PostMapping("/create")
    public Customer createCustomer(@RequestBody CustomerDTO customer) throws Exception {
        System.out.println("Customer created: "+ customer);
        return customerService.createCustomer(customer);
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

    @GetMapping("/getCustomerByPhone/{customerPhone}")
    public Customer getCustomerByPhone(@PathVariable("customerPhone") String customerPhone) throws Exception {
        return customerService.getCustomerByPhone(customerPhone);
    }
    @PatchMapping("/{customerId}")
    public Customer updateCustomer(
        @PathVariable("customerId") String customerId,
        @RequestBody CustomerDTO customerDTO) throws Exception {
        return customerService.updateCustomer(customerId, customerDTO);
    }
}
