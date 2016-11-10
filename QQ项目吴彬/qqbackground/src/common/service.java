package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vo.qqdynamic;
import vo.qqfrienduser;
import vo.qqmessage;
import vo.qquser;



public class service extends Hibrenate_Dao{
	private Connection connection=DBconnection.getconnection();
	public qquser queryuser(String useraccount){
		qquser user=new qquser();
		try {
			Statement Statement=connection.createStatement();
			String sql="select * from qquser where useraccount='"+useraccount+"'";
			ResultSet re=Statement.executeQuery(sql);
			if(re.next()){
				user.setUid(re.getInt(1));
				user.setUsername(re.getString(2));
				user.setUseraccount(re.getString(3));
				user.setUserpwd(re.getString(4));
				user.setUserheadico(re.getString(5));
			}
			re.close();
			Statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	public ArrayList<qquser> queryalluser(){
		ArrayList<qquser> list=new ArrayList<qquser>();
		try {
			Statement Statement=connection.createStatement();
			ResultSet re=Statement.executeQuery("select * from qquser");
			if(re.next()){
				qquser user=new qquser();
				user.setUid(re.getInt(1));
				user.setUsername(re.getString(2));
				user.setUseraccount(re.getString(3));
				user.setUserpwd(re.getString(4));
				user.setUserheadico(re.getString(5));
				list.add(user);
			}
			re.close();
			Statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	public boolean queryuserexist(String account,String pwd){
		try {
			Statement Statement=connection.createStatement();
			String sql="select * from qquser where useraccount='"+account+"' and userpwd='"+pwd+"'";
			ResultSet re=Statement.executeQuery(sql);
			if(re.next()){
				return true;
			}
			re.close();
			Statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	public boolean insertuser(qquser u){
		boolean bln=addObj(u);
		return bln;
	}
	public boolean insertmessage(qqmessage msg){
		boolean bln=addObj(msg);
		return bln;
	}
	public ArrayList<qqmessage> querymessage(String useraccount,String frienduseraccount){
		ArrayList<qqmessage> list=new ArrayList<qqmessage>();
		try {
			Statement statement=connection.createStatement();
			String sql="select * from qqmessage where receiveraccount='"+useraccount+"' and senderaccount ='"+frienduseraccount+"' or  "
					+ " receiveraccount='"+frienduseraccount+"' and senderaccount ='"+useraccount+"'";
			ResultSet re=statement.executeQuery(sql);
			while(re.next()){
				qqmessage msg=new qqmessage();
				msg.setMid(re.getInt(1));
				msg.setSenderaccount(re.getString(2));
				msg.setSenderheadico(re.getString(3));
				msg.setSendername(re.getString(4));
				msg.setMessagecontent(re.getString(5));
				msg.setMessagetime(re.getString(6));
				msg.setReceivername(re.getString(7));
				msg.setReceiverheadico(re.getString(8));
				msg.setReceiveraccount(re.getString(9));
				msg.setReceiverstate(re.getInt(10));
				list.add(msg);
			}
			re.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	} 
	public ArrayList<qqmessage> querymessage(String useraccount){
		ArrayList<qqmessage> list=new ArrayList<qqmessage>();
		String frienduser="''";
		ArrayList<qqfrienduser> flist=queryallfrienduser(useraccount);
		for (int i = 0; i < flist.size(); i++) {
				frienduser+=",'"+flist.get(i)+"'";
		}
		try {
			Statement statement=connection.createStatement();
			String sql="SELECT  * FROM qqmessage  WHERE receiveraccount ="+useraccount+" AND MID IN("
					+ "SELECT MAX(MID) AS MID FROM qqmessage g WHERE receiveraccount ="+useraccount+" GROUP BY senderaccount)";
//			System.out.println(sql);
			ResultSet re=statement.executeQuery(sql);
			while(re.next()){
				qqmessage msg=new qqmessage();
				msg.setMid(re.getInt(1));
				msg.setSenderaccount(re.getString(2));
				msg.setSenderheadico(re.getString(3));
				msg.setSendername(re.getString(4));
				msg.setMessagecontent(re.getString(5));
				msg.setMessagetime(re.getString(6));
				msg.setReceivername(re.getString(7));
				msg.setReceiverheadico(re.getString(8));
				msg.setReceiveraccount(re.getString(9));
				list.add(msg);
			}
			re.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<qqfrienduser> queryallfrienduser(String useraccount){
		ArrayList<qqfrienduser> list=new ArrayList<qqfrienduser>();
		try {
			Statement statement=connection.createStatement();
			String sql="select * from qqfrienduser where useraccount='"+useraccount+"' or frienduseraccount ='"+useraccount+"' and frienduserstate=1";
			ResultSet re=statement.executeQuery(sql);
			while(re.next()){
				qqfrienduser f=new qqfrienduser();
				f.setFid(re.getInt(1));
				if(re.getString("useraccount").equals(useraccount)){
					f.setUsername(re.getString(2));
					f.setUserheadico(re.getString(3));
					f.setUseraccount(re.getString(4));
					f.setFrienduseraccount(re.getString(5));
					f.setFriendusername(re.getString(7));
					f.setFrienduserheadico(re.getString(8));
				}else if(re.getString("frienduseraccount").equals(useraccount)){
					f.setUsername(re.getString(7));
					f.setUserheadico(re.getString(8));
					f.setUseraccount(re.getString(5));
					f.setFrienduseraccount(re.getString(4));
					f.setFriendusername(re.getString(2));
					f.setFrienduserheadico(re.getString(3));
				}
				f.setFrienduserstate(re.getInt(6));
				f.setFriendgroup(re.getInt(9));
				list.add(f);
			}
			re.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public boolean insertfrienduser(qqfrienduser fuser){
		boolean bln=addObj(fuser);
		return bln;
	}
	public boolean updatefrienduser(qqfrienduser fuser){
		boolean bln=updateObj(fuser);
		return bln;
	}
	public boolean insertdynamic(qqdynamic dynamic){
		boolean bln=addObj(dynamic);
		return bln;
	}
	public ArrayList<qqdynamic> querydyanmic(String useraccount){
		ArrayList<qqdynamic> list=new ArrayList<qqdynamic>();
				String frienduser="'"+useraccount+"'";
				ArrayList<qqfrienduser> flist=queryallfrienduser(useraccount);
				for (int i = 0; i < flist.size(); i++) {
					frienduser+=",'"+(flist.get(i)).getFrienduseraccount()+"'";
				}
		try {
			Statement statement=connection.createStatement();
			String sql="select * from qqdynamic where senderaccount in ("+frienduser+") order by did desc";
			ResultSet re=statement.executeQuery(sql);
			while(re.next()){
				qqdynamic d=new qqdynamic();
				d.setDid(re.getInt(1));
				d.setSenderaccount(re.getString(2));
				d.setSenderheadico(re.getString(3));
				d.setSendername(re.getString(4));
				d.setDynamiccontent(re.getString(5));
				d.setSendertime(re.getString(6));
				list.add(d);
			}
			re.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
