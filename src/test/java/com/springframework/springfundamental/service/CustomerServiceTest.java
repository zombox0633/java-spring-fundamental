package com.springframework.springfundamental.service;

import com.springframework.springfundamental.dto.customer.PostCustomerRequest;
import com.springframework.springfundamental.entity.Customer;
import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.exception.InvalidException;
import com.springframework.springfundamental.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    //mockCustomer
    private Customer mockCustomer(UUID customerId) {

        var customer = new Customer();
        customer.setId(customerId);
        customer.setEmail("NanAraujo@gmail.com");
        customer.setPassword("KfCunu4i&");
        customer.setName("NanAraujo");
        customer.setBirthday(new Date(System.currentTimeMillis()));

        return customer;
    }

    //GET ALL
    @Test
    void testForGetCustomers() {
        //given
        var customer1 =  new Customer();
        var customer2  =  new Customer();

        List<Customer> mockedList = Arrays.asList(customer1, customer2);

        //when
        when(customerRepository.findAll()).thenReturn(mockedList);

        List<Customer> customers = customerService.getCustomers();

        //then
        verify(customerRepository, times(1)).findAll();

        assertThat(customers).hasSize(2).isEqualTo(mockedList);
    }

    //GET BY ID
    @Test
    void testForGetCustomerById() {
        // given ctrl+alt+r
        var customerId = UUID.fromString("f30c2a95-96bd-4285-9dc7-354d3be8425c");

//        var customer = new Customer();
//        customer.setId(customerId);
//        customer.setEmail("NanAraujo@gmail.com");
//        customer.setPassword("KfCunu4i&");
//        customer.setName("NanAraujo");
//        customer.setBirthday(new Date(System.currentTimeMillis()));

        var customer = mockCustomer(customerId);

        //when
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));
        var actual = customerService.getCustomerById(UUID.randomUUID().toString());

        //then
        verify(customerRepository, times(1)).findById(any(UUID.class));

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(customerId);
    }

    //CREATE
    //mockCustomerRequest
    private PostCustomerRequest mockCustomerRequest() {
        return new PostCustomerRequest("NanAraujo@gmail.com", "KfCunu4i&", "NanAraujo", "1997-01-03");
    }

    @Test
    void testForCreateCustomer() throws ParseException {
        // given
        var customerRequest = mockCustomerRequest();

        //when
        customerService.saveCustomer(customerRequest);

        //then
        verify(customerRepository, times(1)).save(customerArgumentCaptor.capture());

        var actual = customerArgumentCaptor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getEmail()).isEqualTo("NanAraujo@gmail.com");
        assertThat(actual.getName()).isEqualTo("NanAraujo");

        var isPasswordMatch = BCrypt.checkpw("KfCunu4i&", actual.getPassword());
        assertThat(isPasswordMatch).isTrue();

        var sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertThat(actual.getBirthday()).isEqualTo(sdf.parse("1997-01-03"));
    }

    @Test //ตรวจสอบการทำงานเงื่อนไขเมื่อการใส่ Birthday ผิด customerService จะทำงาน
    void testForCreateCustomer_dateOfBirthInvalidate(){
        //given
        var customerRequest = new PostCustomerRequest(
                "KaiQiu@gmail.com","KfCunu4i","MonikaRai","2024-01-03"
        );

        //when
        assertThatThrownBy(() -> customerService.saveCustomer(customerRequest))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Date of birth cannot be less than 18 years from now");
    }

    @Test
    void testForCreateCustomer_dbException() {
        //given
        var customerRequest = mockCustomerRequest();

        //when
        when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("DB is down"));

        //then
        assertThatThrownBy(() -> customerService.saveCustomer(customerRequest))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Failed to create user");
    }

    //DELETE
    @Test
    void testForDeleteCustomer(){
        //given
        var customerId = UUID.fromString("72ab3c57-b31d-490f-8421-8154b6159eed");
        var customer = mockCustomer(customerId);

        //when
        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(customerId.toString());

        //then
        verify(customerRepository, times(1)).delete(customer);
    }
}
