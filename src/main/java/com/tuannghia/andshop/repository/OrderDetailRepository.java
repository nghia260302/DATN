package com.tuannghia.andshop.repository;

import com.tuannghia.andshop.entity.Order;
import com.tuannghia.andshop.entity.OrderDetail;
import com.tuannghia.andshop.entity.composite_key.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrder(Order order);

    @Query("SELECT od.quantity FROM OrderDetail od WHERE od.order.id = :orderId AND od.clothe.id = :clotheId")
    int findByBookAndOrOrder(Long orderId, Long clotheId);

    List<OrderDetail> findByClotheId(long clotheId);
}
