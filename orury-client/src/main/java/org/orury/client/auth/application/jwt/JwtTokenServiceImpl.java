package org.orury.client.auth.application.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.orury.common.error.code.TokenErrorCode;
import org.orury.common.error.exception.AuthException;
import org.orury.domain.auth.domain.RefreshTokenReader;
import org.orury.domain.auth.domain.RefreshTokenStore;
import org.orury.domain.auth.domain.dto.JwtToken;
import org.orury.domain.user.domain.dto.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.orury.domain.global.constants.Constants.ROLE_USER;

@Service
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String JWT_TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_HEADER_NAME = "Authorization";

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7L; // 7일
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 14L; // 14일

    // 비회원 전용 토큰
    private static final long NO_USER_ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30L; // 30분

    private final SecretKey secretKey;
    private final RefreshTokenReader refreshTokenReader;
    private final RefreshTokenStore refreshTokenStore;

    public JwtTokenServiceImpl(@Value("${spring.jwt.secret}") String secret, RefreshTokenReader refreshTokenReader, RefreshTokenStore refreshTokenStore) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.refreshTokenReader = refreshTokenReader;
        this.refreshTokenStore = refreshTokenStore;
    }

    @Override
    public Authentication getAuthenticationFromRequest(HttpServletRequest request) {
        var token = getTokenFromRequest(request, TokenErrorCode.INVALID_ACCESS_TOKEN);
        return getAuthenticationFromAccessToken(token);
    }

    private String getTokenFromRequest(HttpServletRequest request, TokenErrorCode tokenErrorCode) {
        String tokenHeader = request.getHeader(TOKEN_HEADER_NAME);

        // Token 헤더가 없거나 Bearer 토큰이 아닌 경우
        if (tokenHeader == null || !tokenHeader.startsWith(JWT_TOKEN_PREFIX)) {
            throw new AuthException(tokenErrorCode);
        }

        // Token 추출
        return tokenHeader.split(" ")[1].trim();
    }

    private Authentication getAuthenticationFromAccessToken(String accessToken) {
        Claims claims;

        try {
            claims = parseToken(accessToken);
        } catch (final MalformedJwtException | IllegalArgumentException exception) {
            throw new AuthException(TokenErrorCode.INVALID_ACCESS_TOKEN);
        } catch (final ExpiredJwtException exception) {
            if (Objects.nonNull(exception.getClaims().get("email"))) {
                throw new AuthException(TokenErrorCode.EXPIRED_NO_USER_TOKEN);
            }
            throw new AuthException(TokenErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (final JwtException exception) {
            throw new AuthException(TokenErrorCode.EXPIRED_ACCESS_TOKEN);
        }

        // 비회원은 토큰에 email 필드가 있으므로, 해당 필드로 검증 가능
        if (Objects.nonNull(claims.get("email"))) {
            // 임시 Authentication 세팅
            UserPrincipal userDetails = UserPrincipal.fromToken(0L, claims.get("email").toString(), ROLE_USER.getMessage());
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER.getMessage()));

            return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
        }

        UserPrincipal userDetails = UserPrincipal.fromToken((long) (int) claims.get("id"), claims.getSubject(), ROLE_USER.getMessage());
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER.getMessage()));

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    @Override
    public JwtToken reissueJwtTokens(HttpServletRequest request) {
        // Refresh 토큰 추출
        String refreshToken = getTokenFromRequest(request, TokenErrorCode.INVALID_REFRESH_TOKEN);

        // Refresh 토큰 검증
        Claims claims;
        try {
            claims = parseToken(refreshToken);
        } catch (final MalformedJwtException | IllegalArgumentException exception) {
            log.error("### Error when parsing token: {}", exception.getMessage());
            throw new AuthException(TokenErrorCode.INVALID_REFRESH_TOKEN);
        } catch (final JwtException exception) {
            log.error("### Error when parsing token: {}", exception.getMessage());
            throw new AuthException(TokenErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        if (!refreshTokenReader.existsByValue(refreshToken))
            throw new AuthException(TokenErrorCode.EXPIRED_REFRESH_TOKEN);

        // Access 토큰, Refresh 토큰 모두 재발급
        return issueJwtTokens((long) (int) claims.get("id"), claims.getSubject());
    }

    @Override
    public JwtToken issueJwtTokens(Long id, String email) {
        String accessToken = createJwtToken(id, email, ACCESS_TOKEN_EXPIRATION_TIME);
        String refreshToken = createJwtToken(id, email, REFRESH_TOKEN_EXPIRATION_TIME);

        refreshTokenStore.save(id, refreshToken);

        return JwtToken.of(accessToken, refreshToken);
    }

    @Override
    public JwtToken issueNoUserJwtTokens(String email) {
        String accessToken = noUserCreateJwtToken(email);

        return JwtToken.of(accessToken, null);
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String createJwtToken(Long id, String email, long expirationTime) {
        return Jwts.builder()
                .subject(email)
                .claim("id", id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    private String noUserCreateJwtToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JwtTokenServiceImpl.NO_USER_ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
}
