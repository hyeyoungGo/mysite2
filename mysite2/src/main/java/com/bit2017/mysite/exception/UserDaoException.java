package com.bit2017.mysite.exception;

public class UserDaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;  //객체를 디스크에 저장할때 사용
	
	public UserDaoException(String message) {
		super(message);
	}
	
	public UserDaoException() {
		super("UserDao Exception Occurs");
	}

}
