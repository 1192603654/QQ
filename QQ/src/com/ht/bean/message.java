package com.ht.bean;

public class message {
	private String account;
	private String headico;
	private String username;
	private String content;
	private String messagetime;
	public String getHeadico() {
		return headico;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setHeadico(String headico) {
		this.headico = headico;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMessagetime() {
		return messagetime;
	}
	public void setMessagetime(String messagetime) {
		this.messagetime = messagetime;
	}
}
