package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="Orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	
	@Id
	@GeneratedValue
	private Integer orderId;
	private String username;
	@CreationTimestamp
    private LocalDateTime createTime;
	
	
	@ElementCollection
	private Map<String, Integer> productList;
	
	@Enumerated
	private OrderStatus orderStatus;
	private double orderAmount;
	
	public enum OrderStatus {
		CONFIRMED, COMPLETED, CANCELLED
	}
	
}