package com.springframework.springfundamental.service;

import com.springframework.springfundamental.constants.ErrorMessage;
import com.springframework.springfundamental.dto.customer.PostCustomerRequest;
import com.springframework.springfundamental.entity.Customer;
import com.springframework.springfundamental.exception.InvalidException;
import com.springframework.springfundamental.exception.NotFoundException;
import com.springframework.springfundamental.repository.CustomerRepository;
import com.springframework.springfundamental.utils.DateFormatUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Slf4j //  ทำหน้าที่เรียกการใช้งาน logging และ สร้าง object สำหรับ log
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers(){ return customerRepository.findAll();}

    //GET BY ID
    public Customer getCustomerById(String id){
        log.info("Get user by id: {}",id); //ตรง {} จะแทนที่ด้วย id
        return customerRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Customer")));
    }

    //CREATE
    public Customer saveCustomer(PostCustomerRequest request){
        try {
            // Validate pre-requisite
            log.info("Create user with username: {}", request.email());
            validateCustomer(request);

            //hashPassword
            var hashPassword = BCrypt.hashpw(request.password(), BCrypt.gensalt());

            // Create user
            var customer = new Customer();
            customer.setEmail(request.email());
            customer.setPassword(hashPassword);
            customer.setName(request.name());
            customer.setBirthday(Date.valueOf(request.birthday()));

            return customerRepository.save(customer);

        }catch (InvalidException e){
            log.error("Invalid request: {}", e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error("Failed to create user: {}", e.getMessage(), e);
            throw new InvalidException("Failed to create user");
        }
    }

    //DELETE
    public void  deleteCustomer(String id){
        var exisingCustomer = getCustomerById(id);
        customerRepository.delete(exisingCustomer);
    }

    public void validateCustomer(PostCustomerRequest request){
        // Validate Age
        var dateOfBirth = DateFormatUtils.stringToLocalDate(request.birthday()); //เปลี่ยน string เป็น LocalDate
        var currentDate = LocalDate.now();
        var period = Period.between(dateOfBirth,currentDate); //คำนวณช่วงเวลา (period) ระหว่างสองวันที่ในรูปแบบของ LocalDate

        if (period.getYears() < 18){
            log.debug("Input date of birth: {}", request.birthday());
            throw new InvalidException("Date of birth cannot be less than 18 years from now");
        }
    }
}
