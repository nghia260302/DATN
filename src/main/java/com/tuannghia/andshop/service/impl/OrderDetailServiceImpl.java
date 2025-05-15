package com.tuannghia.andshop.service.impl;

import com.tuannghia.andshop.entity.Order;
import com.tuannghia.andshop.entity.OrderDetail;
import com.tuannghia.andshop.repository.OrderDetailRepository;
import com.tuannghia.andshop.service.OrderDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetailByOrder(Order order) {
        return orderDetailRepository.findByOrder(order);
    }
}
