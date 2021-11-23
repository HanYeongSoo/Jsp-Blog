package com.cos.blog.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// localhost:8181/blog/test(GET, POST)
@WebServlet("/test")
public class ApiServerTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ApiServerTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getContentType() 을 할 필요가 x
		String food = request.getParameter("food");
		String method = request.getParameter("method");

		//		DB에 insert 하고 끝
		response.setContentType("text/html; charset=utf-8");
		
//		int result = 1; // 정상
//		PrintWriter out = response.getWriter();
//		if (result == 1) {
//			out.println("{\"food\": " + food + " , \"method\": " + method +"}");
//		} else {
//			out.println("{\"error\":\"fail\"}");
//		}
//		out.println(result);
//		out.flush();
	}

}
