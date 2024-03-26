package kr.co.ch07.repository.shop;

import kr.co.ch07.entity.shop.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void selectCustomers() {
        List<Customer> customers = customerRepository.selectCustomers();
        log.info("selectCustomers : "+customers);
    }

    @Test
    public void selectCustomer() {
        Customer customer = customerRepository.selectCustomer("a101");
        log.info("selectCustomer : "+customer);
    }
}