package kr.co.ch07.entity.shop;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"order", "product"})
@Builder
@Entity
@Table(name = "shop_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;


    private int count;

}
