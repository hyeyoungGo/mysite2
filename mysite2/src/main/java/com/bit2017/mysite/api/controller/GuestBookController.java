package com.bit2017.mysite.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2017.mysite.dto.JSONResult;
import com.bit2017.mysite.service.GuestBookService;
import com.bit2017.mysite.vo.GuestBookVo;

@Controller("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list/{page}")
	public JSONResult list(@PathVariable("page") Integer page) {
		List<GuestBookVo> list = guestbookService.getList(page);
		System.out.println(list);
		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JSONResult add(@ModelAttribute GuestBookVo vo) {
		guestbookService.addMessage( vo );
		System.out.println("cont" + vo.getNo());
		vo = guestbookService.getMessage( vo.getNo() );
		
		return JSONResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult delete(@ModelAttribute GuestBookVo vo) {
		boolean result = guestbookService.delete(vo);
		return JSONResult.success(result ? vo.getNo() : -1);
	}

}
