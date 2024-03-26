package kr.co.ch07.repository.shop.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.entity.shop.Customer;
import kr.co.ch07.entity.shop.QCustomer;
import kr.co.ch07.repository.shop.custom.CustomerRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static kr.co.ch07.entity.shop.QCustomer.*;
/*
    - CustomerRepositoryCustom 구현 클래스
    - RepositoryCustom에서 접미사 Custom 대신 Impl 접미사 네이밍 규칙
 */
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // Q 도메인 클래스(QueryDSL 이 사용하는 앤터티) 선언
    QCustomer qCustomer = customer;

    @Override
    public List<Customer> selectCustomers() {
        // select QueryDSL 문법
        return jpaQueryFactory.select(qCustomer).from(qCustomer).fetch();
    }

    @Override
    public Customer selectCustomer(String custId) {
        return jpaQueryFactory
                .selectFrom(qCustomer)
                .where(qCustomer.custId.eq(custId))
                .fetchOne();
    }
}
