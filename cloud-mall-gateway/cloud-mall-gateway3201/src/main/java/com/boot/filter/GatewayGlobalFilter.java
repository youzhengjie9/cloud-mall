package com.boot.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
//这样每个请求经过网关全局过滤器都会被拦截
public class GatewayGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        URI uri = exchange.getRequest().getURI();

        log.info(uri+" --->进入网关gateway");

        return chain.filter(exchange); //允许通过
    }

    //设置过滤器优先级
    @Override
    public int getOrder() {
        return 0;
    }
}
