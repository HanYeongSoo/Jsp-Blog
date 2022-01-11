package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;

public class BoardService {
	
	private BoardDao boardDao;

	public BoardService() {
		boardDao = new BoardDao();
	}
	
	public int 글쓰기(SaveReqDto dto) {
		int result = boardDao.save(dto);
		
		return result;
	}

	public List<Board> 글목록보기(int page) {
		
		return boardDao.findAll(page);
	}

	public int 글개수() {
		return boardDao.count();
	}

	public DetailRespDto 글상세보기(int id) {
		// 여기서 조회수 업데이트 치기
		return boardDao.findById(id);
	}

}
