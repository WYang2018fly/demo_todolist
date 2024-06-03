package com.todo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.constant.AuthExceptionConstant;
import com.todo.dto.ErrorResponseDTO;
import com.todo.exception.AuthException;
import com.todo.service.UserService;
import com.todo.service.impl.CustomUserDetailServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailServiceImpl customUserDetailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private static final List<String> WHITELIST = Arrays.asList("/api/oauth/token", "/api/admin/token");

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String requestPath = request.getServletPath();
            if (WHITELIST.contains(requestPath)) {
                chain.doFilter(request, response);
                return;
            }
            String jwt = getTokenFromRequest(request);

            if(!StringUtils.hasText(jwt)) throw new AuthException(AuthExceptionConstant.JWT_MISSING);
            String username = jwtTokenProvider.getUserFromToken(jwt);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            Boolean hasPermission = userService.checkUserRole(username, requestPath.split("/")[2]);
            if(!hasPermission) throw new AuthException(AuthExceptionConstant.JWT_NO_PERMISSION);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } catch (AuthException e) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getReason(), e.getErrMessage());
            handleException(response, errorResponseDTO);
        }
    }

    private void handleException(HttpServletResponse response, ErrorResponseDTO errorResponseDTO) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponseDTO));
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}