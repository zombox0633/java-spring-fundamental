package com.springframework.springfundamental.controller;

import com.springframework.springfundamental.dto.customer.PostCustomerRequest;
import com.springframework.springfundamental.entity.Customer;
import com.springframework.springfundamental.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "APIs for managing customer data including retrieval and creation.")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Customer Data by id",
            description = "Returns customer data for the specified id.")
    public Customer getCustomerById(@PathVariable("id") String id){
        return customerService.getCustomerById(id);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create New Customer", description = "Creates a new customer record in the system.")
    public Customer createCustomer(@Valid @RequestBody PostCustomerRequest request){
        return customerService.saveCustomer(request);
    }
}
