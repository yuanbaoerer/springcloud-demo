package org.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Rt 是 响应时间（Response Time）
 * 全局 filter
 */
@Component
@Slf4j
public class RtGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String uri = request.getURI().toString();
        long start = System.currentTimeMillis();
        log.info("请求【{}】开始，时间：{}", uri, start);
//        ============ 以上是前置逻辑 ============
//        注意这里的异步处理，所以需要doFinally
        Mono<Void> filter = chain.filter(exchange)
                .doFinally((result) -> {
                    // ======= 以下是后置逻辑 ============
                    long end = System.currentTimeMillis();
                    log.info("请求【{}】结束，耗时：{}ms", uri, end - start);
                }); // 放行（异步，所以需要doFinally）10s

        return filter;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
