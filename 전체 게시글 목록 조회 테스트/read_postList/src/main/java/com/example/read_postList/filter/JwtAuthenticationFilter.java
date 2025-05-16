package com.example.read_postList.filter;

import com.example.read_postList.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                    ? jwtProvider.removeBearer(authorizationHeader) : null;

            if (token == null || !jwtProvider.isValidToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtProvider.getUsernameFromJwt(token);

            setAuthenticationContext(request, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(HttpServletRequest request, String username) {

        AbstractAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.NO_AUTHORITIES);

        // 요청에 대한 세부 정보를 설정
        // : 생성된 인증 토큰에 요청의 세부 사항을 설정
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 빈 SecurityContext 객체 생성 후, 인증 토큰 주입
        // : 사용자가 인증되었다는 정보를 담음
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);

        // SecurityContextHolder에 생성된 컨텍스트를 설정
        SecurityContextHolder.setContext(securityContext);
    }
}
