package com.bit2017.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2017.mysite.repository.BoardDao;
import com.bit2017.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public List<BoardVo> list(Long pageNo) {
		List<BoardVo> list = boardDao.getList(pageNo);
		return list;
	}
	
	public boolean write(BoardVo vo) {
		Integer groupNo = vo.getGroupNo();
		
		if(groupNo != null) {
			Integer orderNo = vo.getOrderNo();
			Integer depth = vo.getDepth();
			
			boardDao.increaseGroupOrder(groupNo, orderNo);
			vo.setOrderNo(orderNo + 1);
			vo.setDepth(depth + 1);
		}
		return boardDao.write(vo) == 1;
	}

	public void delete(BoardVo vo) {
		boardDao.delete(vo);
	}
	
	public void modify(BoardVo vo) {
		boardDao.modify(vo);
	}

	public BoardVo view(Long boardNo) {
		boardDao.updateHit(boardNo);
		return boardDao.get(boardNo);
	}
	
	public BoardVo getMessage(Long boardNo) {
		BoardVo boardVo = boardDao.get(boardNo);
		if(boardVo != null) {
			boardDao.updateHit(boardNo);
		}
		return boardVo;
	}

	
}
