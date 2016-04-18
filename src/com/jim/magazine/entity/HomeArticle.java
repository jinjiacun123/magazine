package com.jim.magazine.entity;

public class HomeArticle {
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	private String content;
	private String p1;
	private String p2;
	
	public ImageEntity getP1_e() {
		return p1_e;
	}
	public void setP1_e(ImageEntity p1_e) {
		this.p1_e = p1_e;
	}
	public ImageEntity getP2_e() {
		return p2_e;
	}
	public void setP2_e(ImageEntity p2_e) {
		this.p2_e = p2_e;
	}
	private ImageEntity p1_e;
	private ImageEntity p2_e;
}
