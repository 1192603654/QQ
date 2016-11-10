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

import vo.qqdynamic;

import com.google.gson.Gson;

import common.service;

public class qqdynamicaction {
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
	private qqdynamic dynamic;
	public qqdynamic getDynamic() {
		return dynamic;
	}
	public void setDynamic(qqdynamic dynamic) {
		this.dynamic = dynamic;
	}
	private String useraccount;
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	//閿熸枻鎷峰彇閿熸枻鎷锋�
	public void query(){
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();
			ArrayList<qqdynamic> list=s.querydyanmic(useraccount);
			writer.print(g.toJson(list));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void insert(){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			dynamic.setDynamiccontent(toencodingutf(dynamic.getDynamiccontent()));
			dynamic.setSendername(toencodingutf(dynamic.getSendername()));
			dynamic.setSendertime(toencodingutf(dynamic.getSendertime()));
			PrintWriter writer = response.getWriter();
			boolean bln=s.insertdynamic(dynamic);
			writer.print(g.toJson(bln));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
