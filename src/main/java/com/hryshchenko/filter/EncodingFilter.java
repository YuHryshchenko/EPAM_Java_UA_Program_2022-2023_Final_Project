package com.hryshchenko.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Encoding Filter.
 * 
 * @author Hryshchenko
 *
 */
public class EncodingFilter implements Filter {
	private static final Logger log = LogManager.getLogger(EncodingFilter.class);
	private String encoding;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Starting initialization Encoding Filter");
		encoding = filterConfig.getInitParameter("encoding");
		log.debug("Ending initialization Encoding Filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("Starting Encoding Filter");
		request.setCharacterEncoding(encoding);
		log.debug("Finishing Encoding Filter");
		chain.doFilter(request, response);
	}
}
