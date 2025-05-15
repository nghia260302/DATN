package com.tuannghia.andshop.service;

import com.tuannghia.andshop.entity.Order;
import com.tuannghia.andshop.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetailByOrder(Order order);
}
