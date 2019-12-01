package com.example.demo.service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.example.demo.dao.OrderRepository;

import com.example.demo.dto.Order;
import com.example.demo.dto.Order.OrderStatus;




@Service
public class DataPopulator implements CommandLineRunner {
	
	@Autowired OrderRepository order;
	
	@Override
	public void run(String... args) throws Exception {
		Map<String, Integer> items = new HashMap<>();
		items.put("Duracell AAA batteries 4 ct", 2);
		Order o1 = Order.builder().orderId(1).username("admin").orderStatus(OrderStatus.COMPLETED).orderAmount(30.5).productList(items).createTime(LocalDateTime.now()).build();
		Order o2 = Order.builder().orderId(2).username("admin").orderStatus(OrderStatus.COMPLETED).orderAmount(30.5).productList(items).createTime(LocalDateTime.now()).build();
		Order o3 = Order.builder().orderId(3).username("admin").orderStatus(OrderStatus.COMPLETED).orderAmount(30.5).productList(items).createTime(LocalDateTime.now()).build();
		Order o4 = Order.builder().orderId(4).username("admin").orderStatus(OrderStatus.COMPLETED).orderAmount(30.5).productList(items).createTime(LocalDateTime.now()).build();
		
		order.save(o1);
		order.save(o2);
		order.save(o3);
		order.save(o4);

	}
	
}