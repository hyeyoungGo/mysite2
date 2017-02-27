package com.bit2017.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;

	public List<GalleryVo> getList() {
		System.out.println(sqlSession);
		return sqlSession.selectList("gallery.getList");
	}

	public boolean restore(GalleryVo vo) {
		int count = sqlSession.insert("gallery.insert", vo);
		System.out.println("dao"+vo);
		return (count == 1);
	}

}
