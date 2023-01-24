package com.hryshchenko.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Util class containing tools for implementation pagination.
 * 
 * @author Hryshchenko
 *
 */
public class Paginator {
	private static final Logger log = LogManager.getLogger(Paginator.class);

	/**
	 * Getting request with pagination attributes.
	 * 
	 * @param request
	 * @param list
	 * @param page
	 * @return HttpServletRequest with pagination attributes.
	 */
	public static HttpServletRequest setPagination(HttpServletRequest request, List<?> list, int page) {
		log.trace("Starting setting up pagination.");

		int start = 0;
		int end = 10;
		int size = list.size();
		for (int i = page; i > 1; i--) {
			start += 10;
			end += 10;
		}
		if (start >= size) {
			log.trace("Start index is too big, nothing is found.");
			String message = Localizator.getLocalizedString(request, "paginator.nothing");
			request.setAttribute("nothingFound", message);
			return request;
		} else if (end > size) {
			end = size;
		}
		List<?> subList = list.subList(start, end);
		log.debug("Pagination is set up.");
		return setPaginationAttributes(request, subList, page, end, size);
	}

	/**
	 * Setting attributes for pagination.
	 * 
	 * @param request
	 * @param subList
	 * @param page
	 * @param end
	 * @param size
	 * @return Request with attributes.
	 */
	private static HttpServletRequest setPaginationAttributes(HttpServletRequest request, List<?> subList, int page,
			int end, int size) {
		if (subList != null) {
			if (page > 1) {
				int prev = page - 1;
				request.setAttribute("prev", prev);
			}
			if (end < size) {
				int next = page + 1;
				request.setAttribute("next", next);
			}
			request.setAttribute("list", subList);
		}
		return request;
	}

	/**
	 * Private constructor.
	 * 
	 */
	private Paginator() {

	}
}
