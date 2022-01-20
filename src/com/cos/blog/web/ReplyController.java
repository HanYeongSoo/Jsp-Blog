package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.reply.dto.SaveReqDto;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.ReplyService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyController() {
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
		// http://localhost:8080/blog/reply?cmd=save
		ReplyService replyService = new ReplyService();
		
		HttpSession session = request.getSession();
		
		if (cmd.equals("save")) {
//			int userId = Integer.parseInt(request.getParameter("userId"));
//			int boardId = Integer.parseInt(request.getParameter("boardId"));
//			String content = request.getParameter("content");
			
//			SaveReqDto dto = new SaveReqDto();
//			dto.setUserId(userId);
//			dto.setBoardId(boardId);
//			dto.setContent(content);
			
			BufferedReader br = request.getReader();
			String reqData = br.readLine();
			Gson gson = new Gson();
			SaveReqDto dto = gson.fromJson(reqData, SaveReqDto.class);
//			System.out.println("dto : " + dto);
			
			int result = replyService.댓글쓰기(dto);
			
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);	// 1 or -1
			commonRespDto.setData("댓글 입력 성공");
			
			String responseData = gson.toJson(commonRespDto);
			Script.responseData(response, responseData);
			
//			if (result == 1) {
////				response.sendRedirect("/blog/board?cmd=detail&id="+boardId);
//			} else {
////				Script.back(response, "댓글이 성공적으로 달리지 않았습니다.");
//				Script.responseData(response, responseData);
//			}
		}
		
	}
}
