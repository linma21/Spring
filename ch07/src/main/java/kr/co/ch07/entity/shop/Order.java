package kr.co.ch07.entity.shop;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "customer")
@Builder
@Entity
@Table(name = "shop_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "orderer")
    private Customer customer;

    private int orderStatus;
    private int orderPrice;

    @CreationTimestamp
    public LocalDateTime orderDate;

    @OneToMany(mappedBy = "order")
    public List<OrderItem> orderItems;
}
