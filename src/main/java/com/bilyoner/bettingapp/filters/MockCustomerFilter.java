package com.bilyoner.bettingapp.filters;

import com.bilyoner.bettingapp.dto.response.ExceptionResponseDto;
import com.bilyoner.bettingapp.exceptions.ApiExceptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MockCustomerFilter extends OncePerRequestFilter {

    private final Set<String> mockCustomers = Set.of("fatih", "defne", "yeliz");
    private final ObjectMapper objectMapper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String customerId = request.getHeader("X-Customer-Id");
        boolean invalidCustomer = Objects.isNull(customerId) || !mockCustomers.contains(customerId);

        if (invalidCustomer) {
            unauthorizedException(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> protectedPaths = List.of("/api/");
        return protectedPaths.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    private void unauthorizedException(HttpServletResponse httpResponse) throws IOException {
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json");

        ApiExceptionType exceptionType = ApiExceptionType.UNAUTHORIZED_CUSTOMER;
        ExceptionResponseDto exceptionResponse = ExceptionResponseDto.buildExceptionResponse(exceptionType, null);

        PrintWriter writer = httpResponse.getWriter();
        writer.write(objectMapper.writeValueAsString(exceptionResponse));
        writer.flush();
    }
}

