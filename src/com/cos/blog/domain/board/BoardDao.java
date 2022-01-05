package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.SaveReqDto;

public class BoardDao {

	public int save(SaveReqDto dto) {
		String sql = "INSERT INTO board(userId, title, content, createDate) "
				+ "VALUES(?,?,?,now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			return result;		// 성공이면 1
		} catch (Exception e) {
			e.printStackTrace();
		} finally {		// 무조건 실행되는 부분
			DB.close(conn, pstmt);
		}
		return -1;		// 실패면 -1
	}

}
