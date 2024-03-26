package kr.co.ch07.repository.shop;

import kr.co.ch07.entity.shop.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void selectOrders() {
        List<Order> orders = orderRepository.selectOrders();
        log.info("selectOrders : "+orders);
    }
    @Test
    public void selectOrder() {
        Order order = orderRepository.selectOrder(1);
        log.info("selectOrder : "+order);
    }
}