package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;

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
	
	// 모든 정보 가져와서 list로 뿌려주기
	public List<Board> findAll(int page) {
		String sql = "SELECT * FROM board ORDER BY id DESC LIMIT ?, 4";	// 0,4 , 4,4 , 8,4 ... 
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boards = new ArrayList<Board>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page*4);	// 0 -> 0, 1 -> 4, 2 -> 8
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	// 커서를 이동하는 함수 (데이터가 있으면 처음부터 끝까지 다 읽는거)
				Board board = Board.builder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.userId(rs.getInt("userId"))
						.createDate(rs.getTimestamp("createDate"))
						.build();
				
				boards.add(board);
			}
			return boards;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int count() {
		String sql = "SELECT count(*), id FROM board";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return -1;
	}

	public DetailRespDto findById(int id) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT b.id, b.title, b.content, b.readCount, b.userId, u.username ");		// 여기 끝 문장은 띄어쓰기 해줘야 함.
		sb.append("FROM board b INNER JOIN user u ");		// 여기 끝 문장은 띄어쓰기 해줘야 함.
		sb.append("ON b.userId = u.id ");		// 여기 끝 문장은 띄어쓰기 해줘야 함.
		sb.append("WHERE b.id = ?");
		
		String sql = sb.toString();
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				DetailRespDto dto = new DetailRespDto();
				dto.setId(rs.getInt("b.id"));
				dto.setTitle(rs.getString("b.title"));
				dto.setContent(rs.getString("b.content"));
				dto.setReadCount(rs.getInt("b.readCount"));
				dto.setUserId(rs.getInt("b.userId"));
				dto.setUsername(rs.getString("u.username"));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int updateReadCount(int id) {
		String sql = "UPDATE board SET readCount = readCount+1 WHERE id = ? ";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public int deleteById(int id) {
		String sql = "DELETE FROM board WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public int update(UpdateReqDto dto) {
		String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getId());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

}
