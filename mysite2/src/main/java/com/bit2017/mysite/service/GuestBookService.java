package com.bit2017.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2017.mysite.repository.GuestBookDao;
import com.bit2017.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestBookDao guestbookDao;
	
	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = guestbookDao.getList();
		return list;
	}
	
	public List<GuestBookVo> getList(int page) {
		return guestbookDao.getList(page);
	}

	public boolean insert(GuestBookVo vo) {
		boolean result = guestbookDao.insert(vo);
		return result;
	}

	public boolean delete(GuestBookVo vo) {
		boolean result = guestbookDao.delete(vo);
		return result;
		
	}

	public boolean modify(GuestBookVo vo) {
		boolean result = guestbookDao.modify(vo);
		return result;
	}

	public boolean addMessage(GuestBookVo vo) {
		return guestbookDao.insert(vo);
		
	}

	public GuestBookVo getMessage(Long no) {
		return guestbookDao.get(no);
	}

}
