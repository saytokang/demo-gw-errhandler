package com.example.demogwerrhandler.exception;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class CustomErrorExceptionHandler implements ErrorWebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		HttpStatus status = null;
		if (ex.getClass() == InValidHeaderKeyException.class) {
			status = HttpStatus.FORBIDDEN;
		}
		else if (ex.getClass() == NotFoundHeaderException.class) {
			status = HttpStatus.BAD_REQUEST;
		}
		else if (ex.getClass() == RateLimitException.class) {
			status = HttpStatus.TOO_MANY_REQUESTS;
		}
		else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		ServerHttpResponse response = exchange.getResponse();
		DataBuffer body = response.bufferFactory().wrap(ex.getMessage().getBytes());
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		response.setStatusCode(status);
		return response.writeWith(Mono.just(body));
	}

}
