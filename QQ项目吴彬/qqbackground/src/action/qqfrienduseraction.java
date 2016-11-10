package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vo.qqfrienduser;
import vo.qqmessage;

import com.google.gson.Gson;

import common.service;

public class qqfrienduseraction {
	ApplicationContext Context = new ClassPathXmlApplicationContext(
			"config-resource/applicationContext.xml");
	service s = (service) Context.getBean("service");
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	Gson g = new Gson();
	public String toencodingutf(String str) {
		String s = null;
		try {
			s = new String(str.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	private qqfrienduser fuser;
	private String useraccount;
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public qqfrienduser getFuser() {
		return fuser;
	}
	public void setFuser(qqfrienduser fuser) {
		this.fuser = fuser;
	}
	public void insert() {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fuser.setFriendusername(toencodingutf(fuser.getFriendusername()));
		fuser.setUsername(toencodingutf(fuser.getUsername()));
		try {
			PrintWriter writer = response.getWriter();
			fuser.setFriendgroup(1);
			fuser.setFrienduserstate(1);
			boolean bln=s.insertfrienduser(fuser);
			writer.print(g.toJson(bln));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void update(){
		try {
			fuser.setFrienduserstate(1);
			PrintWriter writer = response.getWriter();
			boolean bln=s.updatefrienduser(fuser);
			writer.print(g.toJson(bln));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void queryall(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PrintWriter writer = response.getWriter();
			ArrayList<qqfrienduser> list=s.queryallfrienduser(useraccount);
			writer.print(g.toJson(list));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
