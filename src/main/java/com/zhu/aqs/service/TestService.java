package com.zhu.aqs.service;

import com.zhu.aqs.mapper.OrderMapper;
import com.zhu.aqs.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestService2 testService2;
    @Resource
    private OrderMapper orderMapper;
    /*@Transactional
    public void test(){

        System.out.println(Thread.currentThread().getName());
        //orderMapper.insert(Order.builder().stock(12).build());
        testService2.contextLoads();
        int i =  15/0;
    }*/
}
