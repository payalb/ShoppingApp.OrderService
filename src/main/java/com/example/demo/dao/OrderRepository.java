package com.example.demo.dao;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.Order;
import com.example.demo.dto.Order.OrderStatus;


@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer>{

		//@Query(nativeQuery=true, value="select * from Order where createTime > ?2 and username=?1")
		//List<Order> findOrderByUsernameAndAfterDate( String username, LocalDateTime date );

		//@Query(nativeQuery=true, value="select * from orders where username= ?1")
		List<Order> findOrderByUsername( String username );
		//http://localhost:8082/orders/search/queryFirst10ByUsername?username=admin&page=0&size=10
		
		Page<Order> queryFirst10ByUsername(String username, Pageable pageable);
		@Modifying
		 @Query(value = "update orders set order_amount = ?2 where order_id = ?1",nativeQuery = true)  
	     void updateAmountById(int id, double amount);
	
		
		@Modifying
	
		// @Query(nativeQuery=true, value="update orders set order_status = ?2 where order_id=?1 ")
	
	     //void updateStatusById(int id, int status);
		
		@Query("update Order set orderStatus = ?2 where orderId=?1 ")
		
	     void updateStatusById(int id, OrderStatus status);
	
}

