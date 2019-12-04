package com.zhu.aqs.service;

import com.zhu.aqs.mapper.OrderMapper;
import com.zhu.aqs.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService2 {
    @Resource
    private OrderMapper orderMapper;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void contextLoads(){
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(Thread.currentThread().getName());
       // orderMapper.insert(Order.builder().stock(16).build());
    }
}
