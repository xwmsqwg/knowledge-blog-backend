package com.xrf.iotbackend.utils;

import com.xrf.iotbackend.config.JwtProperties;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtUtils jwtUtils;

    @Test
    void generateJwt_validClaims_parsesClaims() {
        when(jwtProperties.getSecret()).thenReturn("0123456789abcdef0123456789abcdef");
        when(jwtProperties.getExpirationMs()).thenReturn(60_000L);
        String token = jwtUtils.generateJwt(Map.of(
                "id", 7,
                "username", "tester"
        ));

        Claims claims = jwtUtils.parseJWT(token);

        assertNotNull(claims);
        assertEquals(7, claims.get("id", Integer.class));
        assertEquals("tester", claims.get("username", String.class));
    }

    @Test
    void parseJWT_invalidToken_returnsNull() {
        when(jwtProperties.getSecret()).thenReturn("0123456789abcdef0123456789abcdef");

        assertNull(jwtUtils.parseJWT("false_token"));
    }
}
