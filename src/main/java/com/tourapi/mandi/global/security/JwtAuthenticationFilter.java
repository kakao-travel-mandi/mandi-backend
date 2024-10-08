package com.tourapi.mandi.global.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.entity.constant.Role;
import com.tourapi.mandi.global.redis.service.BlackListTokenService;
import com.tourapi.mandi.global.util.ApiUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private final BlackListTokenService blackListTokenService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager, BlackListTokenService blackListTokenService) {
        super(authenticationManager);
        this.blackListTokenService = blackListTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwt = request.getHeader(JwtProvider.HEADER);

        if (jwt == null) {
            chain.doFilter(request, response);
            return;
        }

        if (!jwt.startsWith("Bearer")) {
            log.error("잘못된 토큰입니다");
            throw new JWTDecodeException("토큰 형식이 잘못되었습니다.");
        }

        // accessToken이 블랙 리스트에 있는지 확인
        blackListTokenService.validAccessToken(jwt);

        try {
            DecodedJWT decodedJwt = JwtProvider.verify(jwt);
            Long id = decodedJwt.getClaim("id").asLong();
            String role = decodedJwt.getClaim("role").asString();
            User user = User.builder().userId(id).role(Role.valueOf(role)).build();
            CustomUserDetails myUserDetails = new CustomUserDetails(user);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            myUserDetails,
                            myUserDetails.getPassword(),
                            myUserDetails.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("디버그 : 인증 객체 만들어짐");
        } catch (SignatureVerificationException sve) {
            log.error("토큰 검증 실패");
        } catch (TokenExpiredException tee) {
            log.error("토큰 만료됨");
            throw new TokenExpiredException("토큰이 만료되었습니다.",tee.getExpiredOn());
        } catch (JWTDecodeException jde) {
            log.error("잘못된 토큰입니다");
            throw new JWTDecodeException("토큰 형식이 잘못되었습니다.");
        }
        chain.doFilter(request, response);
    }

}
