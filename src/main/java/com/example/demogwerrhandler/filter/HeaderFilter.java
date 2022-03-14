package com.example.demogwerrhandler.filter;

import com.example.demogwerrhandler.exception.InValidHeaderKeyException;
import com.example.demogwerrhandler.exception.NotFoundHeaderException;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class HeaderFilter implements GlobalFilter {

	private String key = "H_X";

	private String value = "1234";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders headers = request.getHeaders();
		boolean hasKey = headers.containsKey(key);
		if (!hasKey) {
			throw new NotFoundHeaderException("H_X 해더명이 없습니다.");
		}

		if (!value.equals(headers.getFirst(key))) {
			throw new InValidHeaderKeyException("잘못된 해더값입니다.");
		}

		return chain.filter(exchange);
	}

}
