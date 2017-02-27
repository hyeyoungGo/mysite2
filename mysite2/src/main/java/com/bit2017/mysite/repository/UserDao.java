package com.bit2017.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bit2017.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;

	public UserVo get (Long userNo){
		UserVo userVo = sqlSession.selectOne("user.getByNo", userNo);
		return userVo;
	}
	
	
	public UserVo get (String email, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		
		UserVo userVo = sqlSession.selectOne("user.getByEmailAndPassword", map);
		return userVo;
	}

	
	public boolean insert(UserVo userVo) {
		int count = sqlSession.insert("user.insert", userVo);
		return (count == 1);
	}
	
	public boolean modify(UserVo userVo) {
		int count = sqlSession.update("user.update", userVo);
		return (count == 1);
	}


	public UserVo get(String  email) {
		UserVo userVo = sqlSession.selectOne("user.getByEmail", email);
		return userVo;
	}
}
