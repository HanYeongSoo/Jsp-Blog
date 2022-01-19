package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.DeleteReqDto;
import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;


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
			
			// 계산 : (전체 페이지 수랑 한 페이지 몇 개  - 총 몇 페이지 나와야 되는지 계산) - 3page라면 맥스값은2
			// page == 2가 되는 순간 inEnd라는 변수값에 true
			// request.setAttribute("isEnd", true);	
			int boardCount = boardService.글개수();
			int lastPage = (boardCount - 1) / 4;
			
			// 막대bar 표시 관련
			double currentPosition =(double)page / (lastPage)*100;
			/*
			 * 		0/3*100 = 0;
			 * 		1/3*100 = 33.3;
			 * 		2/3*100 = 66.6;
			 * 		3/3*100 = 100;
			 */
			
			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition",  currentPosition);
			
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			dis.forward(request, response);
			
		} else if (cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			DetailRespDto dto = boardService.글상세보기(id);	// board테이블 + user테이블 = 조인된 데이터가 필요!
			if (dto == null) {
				Script.back(response, "상세보기에 실패하였습니다.");
			} else {
				request.setAttribute("dto", dto);
//			System.out.println("DetailRespDto : " + dto); 
				
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);
			}
		} else if (cmd.equals("delete")) {
			/*
			// 1. 요청 받은 json 데이터를 자바 오브젝트로 패싱
			BufferedReader br = request.getReader();
			String data = br.readLine();
			
			// 읽기만 해서 사용할 수 있는 dto가 필요함! (DeleteReqDto 생성)
			Gson gson = new Gson();
			DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class);
//			System.out.println	("data : " + data);	data : {"boardId"=1(게시글을 누가 썻느지에 따라 달라짐)}
//			System.out.println("dto : " + dto);		dto : DeleteReqDto(boardId=1(여기두))
			
			
			// 2. DB에서 id값으로 글 삭제
			int result = boardService.글삭제(dto.getBoardId());
			
			// 3. 응답할 json 데이터를 생성
			DeleteRespDto respDto = new DeleteRespDto();
			if (result == 1) {
				respDto.setStatus("ok");
			} else {
				respDto.setStatus("fail");
			}
			String respData = gson.toJson(respDto);
//			System.out.println("respData : " + respData);	restData : {"status"."ok"}
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
			*/
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			int result = boardService.글삭제(id);
			
			CommonRespDto<String> commonRespDto = new CommonRespDto();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공");
			
			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
			 
		} else if (cmd.equals("updateForm")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id);
			request.setAttribute("dto", dto);
			
			RequestDispatcher dis = request.getRequestDispatcher("board/updateForm.jsp");
			dis.forward(request, response);
		} else if (cmd.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			UpdateReqDto dto = new UpdateReqDto();
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);
			
			int result = boardService.글수정(dto);
			// 고민해보세요. 왜 RequestDispatcher 안 썼는지..detail.jsp 호출
			if (result == 1) {
				response.sendRedirect("/blog/board?cmd=detail&id=" + id);
			} else {
				Script.back(response, "글 수정에 실패하였습니다.");
			}
		}
		
	} 
}
