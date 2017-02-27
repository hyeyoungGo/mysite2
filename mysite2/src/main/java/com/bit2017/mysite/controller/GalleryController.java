package com.bit2017.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bit2017.mysite.service.GalleryService;
import com.bit2017.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping(value={"gallery", ""})
	public String list(@RequestParam (value="no", required=true, defaultValue="") Long no,
					   Model model) {
		System.out.println();
		List<GalleryVo> list = galleryService.getList();
		System.out.println("con"+no);
		model.addAttribute("list", list);
		System.out.println("con"+list);
		for(GalleryVo vo : list){
			vo.toString();
		}
			
		return "gallery/list";
	}
	
	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file,
						 @ModelAttribute GalleryVo vo) {
		boolean url = galleryService.restore(vo,file);
		System.out.println(url + "//" + file);
		
		
		return "redirect:/gallery";
	}

}
