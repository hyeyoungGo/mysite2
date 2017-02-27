package com.bit2017.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bit2017.mysite.repository.GalleryDao;
import com.bit2017.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;
	private static final String SAVE_PATH = "/upload";
	private static final String URL = "gallery/";
	
	public List<GalleryVo> getList() {
		return galleryDao.getList();
	}

	public boolean restore(GalleryVo vo, MultipartFile file) {
		System.out.println("ser"+file);
		String url = "";
		try {
			if(file.isEmpty() == true) {
				return url != null;
			}
			System.out.println("ser"+url);
			String orgFile = file.getOriginalFilename();
			Long fileSize = file.getSize();
			String extName = orgFile.substring(orgFile.lastIndexOf(".") + 1, orgFile.length());
			String saveFile = generateSaveFileName(extName);
			url = URL + saveFile;
			
			System.out.println("ser"+orgFile+"ser"+saveFile);
			writeFile(file, saveFile);
			
			vo.setOrgFile(orgFile);
			vo.setSaveFile(saveFile);
			
		} catch (IOException e) {
			new RuntimeException("upload file :" + e);
		}
		System.out.println("ser"+file + vo);
		return galleryDao.restore(vo);
	}
	
	private void writeFile(MultipartFile file, String saveFile) throws IOException {
		byte[] data = file.getBytes();
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFile);
		fos.write(data);
		fos.close();
		
	}

	public String generateSaveFileName(String extName) {
		String fileName = "";
		
		Calendar calender = Calendar.getInstance();
		fileName += calender.get(Calendar.YEAR);
		fileName += calender.get(Calendar.MONTH);
		fileName += calender.get(Calendar.DATE);
		fileName += calender.get(Calendar.HOUR);
		fileName += calender.get(Calendar.MINUTE);
		fileName += calender.get(Calendar.SECOND);
		fileName += calender.get(Calendar.MILLISECOND);
		fileName += ("." + extName);
		System.out.println("servi gener"+fileName);
		
		return fileName;
		
	}

}
