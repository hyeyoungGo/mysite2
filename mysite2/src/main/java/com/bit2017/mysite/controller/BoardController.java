package com.bit2017.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2017.mysite.service.BoardService;
import com.bit2017.mysite.vo.BoardVo;
import com.bit2017.mysite.vo.UserVo;
import com.bit2017.security.Auth;
import com.bit2017.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value= {"/list", ""})
	public String list(
			@RequestParam (value="pageNo", required=true, defaultValue="1") Long pageNo,
			Model model) {
		List<BoardVo> list = boardService.list(pageNo);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	/*@Auth
	@RequestMapping("/writeform")
	public String writeform() {
		return "board/writeform";
	}*/
	
	/*@RequestMapping("/replyform/{no}")
	public String replyform() {
		return "board/reply";
	}*/
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/writeform";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser,
						@ModelAttribute BoardVo vo,
						@RequestParam(value="no", required=true, defaultValue="") Long boardNo) {
		vo.setUserNo(authUser.getNo());
		boardService.write(vo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String reply(@RequestParam(value="no", required=true, defaultValue="0") Long boardNo,
						@RequestParam(value="kwd", required=true, defaultValue="") String keyword,
						Model model) {
		BoardVo boardVo = boardService.getMessage(boardNo);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("keyword", keyword);
		return "board/reply";
	}	
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser,
						 @ModelAttribute BoardVo vo,
						 @PathVariable("no") Long no){
		vo.setUserNo(authUser.getNo());
		boardService.delete(vo);
		
		return "redirect:/board";
	}
	
	@RequestMapping("/view/{no}")
	public String view(Model model,
					   @ModelAttribute BoardVo vo,
					   @PathVariable ("no") Long boardNo) {
		vo = boardService.view(boardNo);
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/modifyform/{no}")
	public String modifyform(@ModelAttribute BoardVo vo,
							 @PathVariable("no") Long boardNo,
							 Model model) {
		vo = boardService.view(boardNo);
		model.addAttribute("vo", vo);
		return "board/modify";
	}

	@Auth
	@RequestMapping("/modify")
	public String modify(@AuthUser UserVo authUser,
						 @ModelAttribute BoardVo vo,
						 @RequestParam(value="no", required=true, defaultValue="") Long no,
						 Model model) {
		vo.setUserNo(authUser.getNo());
		boardService.modify(vo);
		model.addAttribute("no", no);
		return "redirect:/board";
	}

}
