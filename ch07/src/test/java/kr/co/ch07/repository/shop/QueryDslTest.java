package kr.co.ch07.repository.shop;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.dto.CustomerDTO;
import kr.co.ch07.dto.ProductAggDTO;
import kr.co.ch07.entity.shop.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
public class QueryDslTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QCustomer qCustomer = QCustomer.customer;
    QProduct qProduct   = QProduct.product;
    QOrder qOrder       = QOrder.order;


    public void test01(){

        List<Product> products = jpaQueryFactory
                .select(qProduct)
                .from(qProduct)
                .fetch();

        log.info("test01 : "+ products);
    }

    public void test02(){

        // select custId, name, age from qCustomer
        List<Customer> qCustomers = jpaQueryFactory
                .select(
                        Projections.fields(
                                Customer.class,
                                qCustomer.custId,
                                qCustomer.name,
                                qCustomer.age
                        )
                )
                .from(qCustomer)
                .fetch();
        log.info("test02 : "+ qCustomers);
    }

    public void test03(){
        // eq : equal | ne : not equal
        // select * from qCustomer where name = '김유신'
        List<Customer> qCustomers1 = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(qCustomer.name.eq("김유신"))
                .fetch();
        // select * from qCustomer where name != '김유신'
        List<Customer> qCustomers2 = jpaQueryFactory
                .selectFrom(qCustomer)
                .where(qCustomer.name.ne("김유신"))
                .fetch();
        log.info("test03 : " + qCustomers1);
        log.info("test03 : " + qCustomers2);
    }

    public void test04(){
        List<Customer> qCustomers1 = jpaQueryFactory
                .selectFrom(qCustomer).where(qCustomer.age.gt(30)).fetch();  // -where age > 30
        List<Customer> qCustomers2 = jpaQueryFactory
                .selectFrom(qCustomer).where(qCustomer.age.goe(30)).fetch();  // -where age >= 30
        List<Customer> qCustomers3 = jpaQueryFactory
                .selectFrom(qCustomer).where(qCustomer.age.lt(30)).fetch();  // -where age < 30
        List<Customer> qCustomers4 = jpaQueryFactory
                .selectFrom(qCustomer).where(qCustomer.age.loe(30)).fetch();  // -where age <= 30

        log.info("test04 : "+qCustomers1);
        log.info("test04 : "+qCustomers2);
        log.info("test04 : "+qCustomers3);
        log.info("test04 : "+qCustomers4);
    }
    public void test05(){
        List<Customer> customers = jpaQueryFactory
                .selectFrom(qCustomer).where(qCustomer.age.in(21, 31, 41)).fetch();
        log.info("test05 : "+customers);
    }

    public void test06(){
        List<Customer> customers = jpaQueryFactory
                                    .selectFrom(qCustomer)
                                    .where(qCustomer.name.like("%신"))
                                    .fetch();
        log.info("test06 : " + customers);
    }

    public void test07(){
        List<Product> products = jpaQueryFactory.selectFrom(qProduct)
                                                .where(qProduct.stock.gt(0))
                                                .orderBy(qProduct.price.desc())
                                                .fetch();

        log.info("test07 : "+ products);
    }

    public void test08(){
        // select ~ where stock > 0 order by price asc limit 0, 3
        List<Product> products = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.stock.gt(0))
                .orderBy(qProduct.price.asc())
                .offset(0)
                .limit(3)
                .fetch();

        log.info("test08 : "+ products);
    }

    public void test09(){
        ProductAggDTO productAggDTO = jpaQueryFactory.select(
                Projections.fields(
                        ProductAggDTO.class,
                        qProduct.price.sum().as("priceSum"),
                        qProduct.price.avg().as("priceAvg"),
                        qProduct.price.max().as("priceMax"),
                        qProduct.price.min().as("priceMin")
                        )
                )
                .from(qProduct)
                .fetchOne();
        log.info("test09 : "+ productAggDTO);
    }

    @Test
    public void test10(){
        // select ~ where orderStatus = 1 group by `orderer` having custId >= 2;
        List<CustomerDTO> orders = jpaQueryFactory
                .select(
                        Projections.fields(
                                CustomerDTO.class,
                                qOrder.customer.custId,
                                qOrder.customer.name,
                                qOrder.customer.custId.count().as("orderCount")
                        )
                )
                .from(qOrder)
                .where(qOrder.orderStatus.eq(1))
                .groupBy(qOrder.customer.custId)
                .having(qOrder.customer.custId.count().goe(2))
                .fetch();

        log.info("test10 : " + orders);
    }

    @Transactional
    public void test11(){
        List<Tuple> orders = jpaQueryFactory.select()
                .from(qOrder)
                .join(qCustomer)
                .on(qOrder.customer.eq(qCustomer))
                .fetch();
        log.info("test11 : "+ orders);
    }
    public void test12(){

        String name = "김유신";
        Integer age = 21;

        // 동적 쿼리 생성 builder
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(name != null){
            booleanBuilder.and(qCustomer.name.eq(name));
        }

        if(age != null){
            booleanBuilder.and(qCustomer.age.goe(age));
        }
        List<Customer> customers = jpaQueryFactory.selectFrom(qCustomer)
                .where(booleanBuilder)
                .fetch();
        log.info("test12 : " + customers);

    }

    public void test13(){

        String keyword = "유신";
        // 동적 쿼리 생성 expression
        BooleanExpression expression = qCustomer
                                            .name.containsIgnoreCase(keyword)
                                            .or(qCustomer.addr.containsIgnoreCase(keyword));

        List<Customer> customers = jpaQueryFactory.selectFrom(qCustomer)
                .where(expression)
                .fetch();

        log.info("test13 : "+ customers);
    }
}
