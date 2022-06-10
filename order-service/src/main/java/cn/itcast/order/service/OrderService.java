package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.利用Resttemplate调用http请求,查询用户信息
        // 2.1 创建url
        String url = "http://user-server/user/" + order.getUserId();
        User forObject = restTemplate.getForObject(url, User.class);
        // 3.封装用户信息到order对象
        order.setUser(forObject);
        // 4.返回
        return order;
    }
}
