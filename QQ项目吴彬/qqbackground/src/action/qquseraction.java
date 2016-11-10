package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import vo.qquser;
import common.service;

public class qquseraction {
	ApplicationContext Context = new ClassPathXmlApplicationContext(
			"config-resource/applicationContext.xml");
	service s = (service) Context.getBean("service");
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	private qquser user;
	Gson g = new Gson();
	public qquser getUser() {
		return user;
	}

	public void setUser(qquser user) {
		this.user = user;
	}

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

	public void insert() {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		user.setUsername(toencodingutf(user.getUsername()));
		try {
			PrintWriter writer = response.getWriter();
			boolean bln = s.insertuser(user);
			writer.print(g.toJson(bln));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void log() {
		try {
			PrintWriter writer = response.getWriter();
			boolean bln = s.queryuserexist(user.getUseraccount(),
					user.getUserpwd());
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
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PrintWriter writer = response.getWriter();
			qquser u = s.queryuser(user.getUseraccount());
			writer.print(g.toJson(u));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
