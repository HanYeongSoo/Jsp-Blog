package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.service.UserService;
import com.cos.blog.util.Script;

// http:localhost:8181/blog/user
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
	
	// http:localhost:8181/blog/user ?cmd=머시기
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		UserService userService = new UserService();

		// http:localhost:8181/blog/user?cmd=loginForm
		// 로그인 관련
		if (cmd.equals("loginForm")) {
			// 서비스 호출, 아이디 기억 (호출이 아니고 어떤 기능이 들어가면 service가 필요)
//			response.sendRedirect("user/loginForm.jsp"); 		필터가 들어가면서 redirect를 쓰면 x 대신에 dispatcher를 써야함
			RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
			dis.forward(request, response);
			
		} else if (cmd.equals("login")) {
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			
			User userEntity = userService.로그인(dto);
			
			if (userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", userEntity);		// 인증 주체 (principal, loginUser, sessionUser 등등으로 사용)
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "로그인 실패");
			}
			
			// 회원가입
		} else if (cmd.equals("joinForm")) {
			RequestDispatcher dis = request.getRequestDispatcher("user/joinForm.jsp");
			dis.forward(request, response);
		} else if (cmd.equals("join")) {
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			dto.setAddress(address);
			System.out.println("회원가입 : " + dto);
//			userService.회원가입(dto);
			
			int result = userService.회원가입(dto);
			if (result == 1) {		// 회원가입 성공 시
				response.sendRedirect("index.jsp");
			} else {		// 회원가입 실패 시
				Script.back(response, "회원가입 실패");
			} 
			
			// 회원가입 시 username 중복 체크
		} else if (cmd.equals("usernameCheck")) {
			BufferedReader br = request.getReader();
			String username = br.readLine();
			request.getParameter("username");
			int result = userService.유저네임중복체크(username);
			
			PrintWriter out = response.getWriter();
			if (result == 1) {
				out.print("ok");
			} else {
				out.print("fail");
			}
			out.flush();
			
			// 로그아웃 기능 구현
		} else if (cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
	}

}
