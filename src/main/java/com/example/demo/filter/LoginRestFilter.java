package com.example.demo.filter;

import java.io.IOException;

import com.example.demo.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/rest/room/*", "/rest/rooms/*" }) // 需要登入才能訪問的路徑
public class LoginRestFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String method = request.getMethod();
		HttpSession session = request.getSession();
		//		System.out.println(method);
		//		System.out.println("過濾器中 session ID = " + session.getId());
		//		System.out.println(session.getAttribute("userCert"));
		// 開放 GET 查詢
		if ("GET".equalsIgnoreCase(method) || "OPTIONS".equalsIgnoreCase(method)) {
			chain.doFilter(request, response);
			return;
		}

		// 非 GET 時驗證登入狀態
		if (session != null && session.getAttribute("userCert") != null) {
			//			System.out.println("HI");
			chain.doFilter(request, response); // 已登入，放行
		} else {
			// 未登入，回傳 401
			//			System.out.println("error");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json;charset=UTF-8");
			ApiResponse<?> apiResponse = ApiResponse.error(401, "請先登入");
			// 利用 ObjectMapper 將指定物件轉 json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(apiResponse);
			response.getWriter().write(json);
		}
	}

}