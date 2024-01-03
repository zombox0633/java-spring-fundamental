package com.springframework.springfundamental.service;

import com.springframework.springfundamental.entity.Customer;
import com.springframework.springfundamental.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    //GET BY ID
    @Test
    void testForGetCustomerById() {
        // given ctrl+alt+r
        var customerId = UUID.fromString("f30c2a95-96bd-4285-9dc7-354d3be8425c");

        var customer = new Customer();
        customer.setId(customerId);
        customer.setEmail("NanAraujo@gmail.com");
        customer.setPassword("KfCunu4i&");
        customer.setName("NanAraujo");
        customer.setBirthday(new java.sql.Date(new Date().getTime()));

        //when
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));
        var actual = customerService.getCustomerById(UUID.randomUUID().toString());

        //that
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(customerId);

        verify(customerRepository, times(1)).findById(any(UUID.class));
    }

    //CREATE
}
