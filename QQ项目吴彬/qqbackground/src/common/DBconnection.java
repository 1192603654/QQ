package common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBconnection {
	private static String username="root";
	private static String password="tiger";
	private static String drivate="com.mysql.jdbc.Driver";
	private static String url="jdbc:mysql://127.0.0.1:3306/qq?useUnicode=true&amp;characterEncoding=utf-8";
	static{
		try {
			Class.forName(drivate);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static  Connection getconnection(){
		Connection conn=null;
		try {
			if(conn==null||conn.isClosed()){
				conn=DriverManager.getConnection(url, username, password);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		System.out.println(DBconnection.getconnection());
	}
}
