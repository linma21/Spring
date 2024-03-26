package kr.co.ch07.repository.shop.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ch07.entity.shop.Order;
import kr.co.ch07.entity.shop.QOrder;
import kr.co.ch07.entity.shop.QOrderItem;
import kr.co.ch07.repository.shop.custom.OrderItemRepositoryCustom;
import kr.co.ch07.repository.shop.custom.OrderRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QOrder qOrder = QOrder.order;

    @Override
    public List<Order> selectOrders(){
        return jpaQueryFactory.select(qOrder).from(qOrder).fetch();
    }
    @Override
    public Order selectOrder(int orderId){
        return  jpaQueryFactory.selectFrom(qOrder).where(qOrder.orderId.eq(orderId))
                .fetchOne();
    }
}
