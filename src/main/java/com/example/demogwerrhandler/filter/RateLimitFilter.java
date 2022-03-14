package com.example.demogwerrhandler.filter;

import com.example.demogwerrhandler.exception.RateLimitException;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RateLimitFilter implements GlobalFilter {

	private int Limit = 3;

	private int current = 0;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (current >= Limit) {
			throw new RateLimitException("Limit " + Limit + " 을 초과했습니다.");
		}
		current++;

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			ServerHttpResponse response = exchange.getResponse();
			response.getHeaders().add("RATE-LIMIT", Limit + "");
			response.getHeaders().add("RATE-CURRENT", current + "");
		}));
	}

}
