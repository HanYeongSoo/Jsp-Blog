package com.cos.blog.service;


import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserDao;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.domain.user.dto.UpdateReqDto;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	// 회원가입, 회원수정, 로그인, 로그아웃, 아이디중복체크, 
	
	// 회원가입은 model로 받으면 null값 체크가 안됨
	public int 회원가입(JoinReqDto dto) {
		int result = userDao.save(dto);
		return result;	// -1이면 실패
	}
	
	public User 로그인(LoginReqDto dto) {
		
		return userDao.findByUsernameAndPassword(dto);		// null이면 실패
	}
	
	public int 회원수정(UpdateReqDto dto) {
		
		return -1;	// -1이면 실패
	}
	
//	public void 로그아웃() {
//			로그아웃은 service에서 안함 걍 controller에서 해결 (여기엔 request가 없음 )
//	}
	
	public int 유저네임중복체크(String username) {
		int result = userDao.findById(username);
		return result;		// 찾으면 1 못찾으면 -1
	}
}
