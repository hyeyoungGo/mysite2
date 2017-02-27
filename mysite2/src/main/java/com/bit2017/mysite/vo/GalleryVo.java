package com.bit2017.mysite.vo;

public class GalleryVo {
	private Long no;
	private String orgFile;
	private String saveFile;
	private String comments;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getOrgFile() {
		return orgFile;
	}
	public void setOrgFile(String orgFile) {
		this.orgFile = orgFile;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		return "FileUploadVo [no=" + no + ", orgFile=" + orgFile + ", saveFile=" + saveFile + ", comments=" + comments
				+ "]";
	}
	
}
