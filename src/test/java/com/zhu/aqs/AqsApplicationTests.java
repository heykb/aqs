package com.zhu.aqs;

import com.zhu.aqs.mapper.OrderMapper;
import com.zhu.aqs.model.Order;
import com.zhu.aqs.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class AqsApplicationTests {

    @Resource
    private OrderMapper orderMapper;


    @Test
    public void test(){
        List<Order> orders = orderMapper.select0();
        System.out.println(orders.get(0).getStock().value());

    }


}
