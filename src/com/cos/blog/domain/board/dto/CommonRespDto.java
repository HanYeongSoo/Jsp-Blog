package com.cos.blog.domain.board.dto;

import lombok.Data;

@Data
public class CommonRespDto<T> {
	private int statusCode;		// 1이면 정상, -1이면 에러
	private T data;
}
