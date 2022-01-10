package com.cos.blog.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.util.Script;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		BoardService boardService = new BoardService();
		
		HttpSession session = request.getSession();
		// http://localhost:8080/blog/board?cmd=savaForm
		if (cmd.equals("saveForm")) {
			User loginUser = (User)session.getAttribute("loginUser");
			// 로그인을 했나 안했나
			if (loginUser != null) {
				RequestDispatcher dis = request.getRequestDispatcher("board/saveForm.jsp");
				dis.forward(request, response);
			} else {
				RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
				dis.forward(request, response);
			}
		} else if (cmd.equals("save")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
//			System.out.println("content 확인 : " + content);
			
			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			int result = boardService.글쓰기(dto);
			
			if (result == 1) {
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "글쓰기에 실패하셨습니다.");
			}
		} else if (cmd.equals("list")) {
			int page = Integer.parseInt(request.getParameter("page"));	// 최초 : 0 , Next : 1 , Next : 2 ...
			List<Board> boards =  boardService.글목록보기(page);
			request.setAttribute("boards", boards);
			
			int totalBoard = 5;
			int maxBoard = 4 / totalBoard;
			boolean isEnd = false;
			if (maxBoard % totalBoard == 0) {
				isEnd = false;
				request.setAttribute("isEnd", false);	
			} else {
				isEnd = true;
				request.setAttribute("isEnd", true);	
			}
			
			// 계산 : (전체 페이지 수랑 한 페이지 몇 개  - 총 몇 페이지 나와야 되는지 계산) - 3page라면 맥스값은2
			// page == 2가 되는 순간 inEnd라는 변수값에 true
			// request.setAttribute("isEnd", true);	
			
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			dis.forward(request, response);
			
		}
		
	}
}
