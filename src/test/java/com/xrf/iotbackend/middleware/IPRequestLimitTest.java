package com.xrf.iotbackend.middleware;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class IPRequestLimitTest {

    // 同一 IP 前 100 次放行，第 101 次抛出限流异常。
    @Test
    void allowsOneHundredRequestsAndNextThrowException() throws Exception {
        IPRequestLimit limiter = spy(new IPRequestLimit());
        doReturn(0L).when(limiter).currentTimeMillis();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("192.0.2.1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        for (int i = 0; i < 100; i++) {
            assertTrue(limiter.preHandle(request, response, null));
        }

        BusinessException exception = assertThrows(BusinessException.class, () -> limiter.preHandle(request, response, null));
        assertEquals(ErrorCode.TOO_MANY_REQUESTS, exception.getErrorCode());
    }
}
