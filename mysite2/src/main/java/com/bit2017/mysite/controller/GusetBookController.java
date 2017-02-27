package com.bit2017.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2017.mysite.dto.JSONResult;
import com.bit2017.mysite.service.GuestBookService;
import com.bit2017.mysite.vo.GuestBookVo;


@Controller
@RequestMapping("/guestbook")
public class GusetBookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@RequestMapping(value={"/list",""})
	public String list(Model model) {
		List<GuestBookVo> list = guestbookService.getList();
		model.addAttribute("list", list);
		
		return "/guestbook/list";
		
	}
	
	@RequestMapping("/list-ajax")
	public String list(){
		return "/guestbook/list-ajax";
	}
	
	@RequestMapping("/insert")
	public String add(@ModelAttribute GuestBookVo vo) {
		guestbookService.insert(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping("/deleteform/{no}")
	public String deleteform(
			Model model,
			@PathVariable("no") Long no) {
		model.addAttribute("no", no);
		
		return "/guestbook/deleteform";
	}
	
	@RequestMapping("/delete")
	public String delete(
			@ModelAttribute GuestBookVo vo) {
		System.out.println(vo);
		guestbookService.delete(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping("/modifyform/{no}")
	public String modifyform(
			Model model,
			@PathVariable("no") Long no) {
		
		model.addAttribute("no", no);
		
		return "/guestbook/modifyform";
	}
	
	@RequestMapping("/modify")
	public String modify(
			@ModelAttribute GuestBookVo vo) {
		guestbookService.modify(vo);
		return "redirect:/guestbook/list";
	}
	
	

}
