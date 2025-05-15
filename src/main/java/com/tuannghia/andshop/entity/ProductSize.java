package com.tuannghia.andshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product_size")
public class ProductSize extends AbstractBase {

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "clothe_id", nullable = false)
    private Clothe clothe;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "total_revenue")
    private Double totalRevenue;

    @JsonIgnore
    @OneToMany(mappedBy = "productSize", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetail.setProductSize(this);
        orderDetails.add(orderDetail);
    }
}
