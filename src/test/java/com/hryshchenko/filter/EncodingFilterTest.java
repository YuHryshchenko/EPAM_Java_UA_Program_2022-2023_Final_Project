package com.hryshchenko.filter;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EncodingFilterTest {

   private final FilterConfig config = mock(FilterConfig.class);

    @Test
    void testDoFilter() throws ServletException {
        when(config.getInitParameter("encoding")).thenReturn("UTF-8");
        EncodingFilter filter = new EncodingFilter();
        filter.init(config);
    }

}
