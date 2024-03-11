package com.kt.edu.thirdproject.common.route;

import com.kt.edu.thirdproject.common.filter.LoggingGatewayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
public class EduRouteLocator {

    //private final AuthorizationHeaderFilter authorizationHeaderFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,LoggingGatewayFilterFactory loggingGatewayFilterFactory) {
        return builder.routes()
                .route("sample-api", r -> r.path("/api/v1/**")
                        //.filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                         //       .removeRequestHeader("Content-Type")
                         //       .addRequestHeader("Content-Type", "application/json")
                        //)
                        .filters(f -> f.filter(loggingGatewayFilterFactory.apply(new LoggingGatewayFilterFactory.Config("message", true, true)))
                        .addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8081/api/v1"))
                .build();
    }
}