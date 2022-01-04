package com.cos.blog.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.blog.config.DB;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;

// Dao에서 DB랑 연결하는거 알지?
public class UserDao {
	// 회원가입 메소드
	public int save(JoinReqDto dto) {
		String sql = "INSERT INTO user(username, password, email, address, userRole, createDate) "
				+ "VALUES(?,?,?,?,'USER',now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getAddress());
			int result = pstmt.executeUpdate();
			return result;		// 성공이면 1
		} catch (Exception e) {
			e.printStackTrace();
		} finally {		// 무조건 실행되는 부분
			DB.close(conn, pstmt);
		}
		return -1;		// 실패면 -1
	}
	
	// 회원수정 메소드
	public void update() {
		
	}
	
	// 아이디 중복 체크 
	public void usernameCheck() {
		
	}
	
	// 아이디를 통한 회원정보 보기
	public void findById() {
		
	}
	
	// 회원가입할 때 아이디가 있는지 확인하는거
	public int findById(String username) {
		String sql = "SELECT * FROM user WHERE username=?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return 1;		// 성공이면 1
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {		// 무조건 실행되는 부분
			DB.close(conn, pstmt, rs);
		}
		return -1;		// 실패면 -1
	}

	public User findByUsernameAndPassword(LoginReqDto dto) {
		String sql = "SELECT id, username, email, address FROM user WHERE username=? AND password=?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs = pstmt.executeQuery();
			
			// Persistence API로 나중엔 편하게 가능...
			if (rs.next()) {
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.address(rs.getString("address"))
						.build();
				return user;
			}
		} catch (Exception e) {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
}
