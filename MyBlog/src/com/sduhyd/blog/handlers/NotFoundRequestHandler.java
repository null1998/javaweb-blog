package com.sduhyd.blog.handlers;

import com.sduhyd.blog.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundRequestHandler implements RequestHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(404);
		try {
			response.setContentType("application/json");
			response.getWriter().print("{\"code\": -1, \"message\": \"Not found!\"}");
		} catch(Exception e ) {
			e.printStackTrace();
		}
	}
}
