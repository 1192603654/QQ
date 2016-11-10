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

import com.google.gson.Gson;

import vo.qqmessage;
import common.service;

public class qqmessageaction {
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

	private qqmessage msg;

	public qqmessage getMsg() {
		return msg;
	}

	public void setMsg(qqmessage msg) {
		this.msg = msg;
	}

	public void send() {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		msg.setMessagecontent(toencodingutf(msg.getMessagecontent()));
		msg.setMessagetime(toencodingutf(msg.getMessagetime()));
		msg.setReceivername(toencodingutf(msg.getReceivername()));
		msg.setSendername(toencodingutf(msg.getSendername()));
		try {
			PrintWriter writer = response.getWriter();
			boolean bln = s.insertmessage(msg);
			writer.print(g.toJson(bln));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void query() {
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();
			ArrayList<qqmessage> list = s.querymessage(useraccount);
			writer.print(g.toJson(list));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void charuser(){
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();
			ArrayList<qqmessage> list = s.querymessage(msg.getSenderaccount(),msg.getReceiveraccount());
			writer.print(g.toJson(list));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String useraccount;

	public String getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}

}
