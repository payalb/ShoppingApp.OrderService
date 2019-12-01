
package com.example.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.example.demo.dao.OrderRepository;
import com.example.demo.dto.Order;
import com.example.demo.dto.Order.OrderStatus;
import com.example.demo.dto.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
//@ExposesResourceFor(Order.class)
@RequestMapping("/users/{username}/orders")

@CrossOrigin
public class OrderController {
	@Autowired
	OrderRepository orderService;

	@Autowired
	RestTemplate template;

//http://localhost:8082/order?username=admin&page=1&size=10
	//http://localhost:8082/users/admin/orders?page=1&size=10
	@GetMapping
	public Page<Order> getOrderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, @PathVariable String username) {
		PageRequest request = PageRequest.of(page - 1, size);
		Page<Order> orderPage;

		orderPage = orderService.queryFirst10ByUsername(username, request);

		return orderPage;
	}
	/*
	@GetMapping(path = "update")
	public boolean updateOrderStatus(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "status") String status) {
		Order order = orderService.getOne(id);
		OrderStatus stat;

		switch (status) {
		case "CONFIRMED":
			stat = OrderStatus.CONFIRMED;
			break;
		case "CANCELLED":
			stat = OrderStatus.CANCELLED;
			break;

		case "COMPLETED":
			stat = OrderStatus.COMPLETED;
			break;
		default:
			stat = null;

		}
		if (stat != null && order != null) {
			order.setOrderStatus(stat);
			orderService.save(order);
			return true;
		}

		return false;
	}
*/
	// relationships : resources: users, order
	// ? filtering, pagination

	@PostMapping(path="/add")
    //public ResponseEntity addOrder(@RequestParam(value="username") String username, @RequestParam(value="total") double price) {
    public DeferredResult<ResponseEntity> addOrder(@RequestBody Order order, @PathVariable String username) {
    	DeferredResult<ResponseEntity> df = new DeferredResult<>();
    	order.setCreateTime(LocalDateTime.now());
    	order.setOrderStatus(OrderStatus.COMPLETED);
      
    	Thread t = new Thread(() -> {
    	     //update inventory for products: asynchronous 
            HttpHeaders header= new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            Payment p1= new Payment();
            ObjectMapper mapper= new ObjectMapper();
            String json;
			try {
				json = mapper.writeValueAsString(p1);
				RequestEntity<String> entity;
				entity = new RequestEntity(json ,HttpMethod.POST,new URI("/payments"));
				ResponseEntity<String> response=template.exchange(entity, String.class);
				df.setResult(ResponseEntity.ok().build());
				
				template.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081"));
				template.delete("/accounts/{accountId}/EmptyCart", username);
				
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}	catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		});
    	t.start();
    	return df;
	}
	

	
}
