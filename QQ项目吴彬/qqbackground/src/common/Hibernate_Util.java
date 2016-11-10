package common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Hibernate_Util {
	private static SessionFactory sessionf = null;

	public static SessionFactory getSessionFactory() {
		if (sessionf == null) {  
			//解析xml文件获取session工厂
			ApplicationContext context= new ClassPathXmlApplicationContext("config-resource/applicationContext.xml");
			sessionf=(SessionFactory)context.getBean("sFactory");
		}
		return sessionf;
	}
	
	public static Session getSession(){
		return getSessionFactory().openSession();
	}
	public static void main(String[] args) {
		System.out.println(Hibernate_Util.getSession());
	}
}	
