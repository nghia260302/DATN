package com.tuannghia.andshop.entity;

import com.tuannghia.andshop.entity.composite_key.OrderDetailId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(OrderDetailId.class) // Thêm định nghĩa IdClass
@Table(name = "order_details")
public class OrderDetail implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private ProductSize productSize;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;


}
