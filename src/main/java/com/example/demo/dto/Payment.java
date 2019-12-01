package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Payment {

	private Integer orderId;
	private String username;
	private double amount;
	private String action;
	private LocalDateTime time;
	private String method;
}
