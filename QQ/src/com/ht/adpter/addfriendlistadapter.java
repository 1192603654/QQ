package com.ht.adpter;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ht.common.Constant;
import com.ht.common.qqapp;
import com.ht.qq.R;
import com.ht.vo.qquser;

public class addfriendlistadapter extends BaseAdapter{
	private ArrayList<qquser> list;
	private Activity context;
	private Button commit;
	private String fuseraccount;
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				commit.setText("“—ÃÌº”");
				commit.setEnabled(false);
			}
			if(msg.what==2){
				Toast.makeText(context, "ÃÌº” ß∞‹£°", Toast.LENGTH_SHORT).show();
			}
		}
	};
	TextView name;
	public addfriendlistadapter(ArrayList<qquser> list,Activity context) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int postion, View conview, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater infalter=LayoutInflater.from(context);
		View v=infalter.inflate(R.layout.addfriend_listview_item, null);
		name=(TextView) v.findViewById(R.id.addfriend_listview_item_username);
		qquser user=list.get(postion);
		name.setText(user.getUsername());
		commit=(Button) v.findViewById(R.id.addfriend_add);
		setlistener();
		fuseraccount=user.getUseraccount();
		return v;
	}
	private void setlistener(){
		commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread t=new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						qqapp app=(qqapp) context.getApplication();
						String username=app.getUsername();
						String useraccount=app.getUseraccount();
						String fusername=name.getText().toString();
						String url = null;
						try {
							url = "http://"+Constant.http_ip+":8080/qqbackground/insertfriend?fuser.username="+URLEncoder.encode(username, "utf-8")+""
									+ "&fuser.useraccount="+useraccount+"&fuser.frienduseraccount="+fuseraccount+"&fuser.friendusername="+URLEncoder.encode(fusername, "utf-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String json=GohttpGET(url);
						boolean bln=new Boolean(json);
						if(bln){
							handler.sendEmptyMessage(1);
						}else{
							handler.sendEmptyMessage(2);
						}
					}
				});
				t.start();
			}
		});
	}
	private  String GohttpGET(String url){
		 try {
			URL ul=new URL(url);
			HttpURLConnection urlconnection=(HttpURLConnection) ul.openConnection();
			urlconnection.setConnectTimeout(5000);
			urlconnection.setRequestMethod("GET");
			int code=urlconnection.getResponseCode();
			if(code==200){
				InputStream is=urlconnection.getInputStream();
				return IOUtils.toString(is);
			}else{
				return "Õ¯¬Á∑√Œ  ß∞‹£°";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Õ¯¬Á∑√Œ  ß∞‹£°";
		}
	}
}
