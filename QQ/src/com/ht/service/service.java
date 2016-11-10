package com.ht.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ht.adpter.messageitemadpter;
import com.ht.bean.message;
import com.ht.common.Constant;
import com.ht.common.qqapp;
import com.ht.qq.R;
import com.ht.vo.qqmessage;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class service extends Service{
	private messageitemadpter adpter;
	private ListView msglist;
	private String faccount;
	private ArrayList<qqmessage> list=new ArrayList<qqmessage>();
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				
			}
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
//	private void finsh() {
//		// TODO Auto-generated method stub
//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				qqapp app = (qqapp) getApplication();
//				String sendaccount = app.getUseraccount();
//				String url = "http://"
//						+ Constant.http_ip
//						+ ":8080/qqbackground/charusermessage?msg.senderaccount="
//						+ sendaccount + "&msg.receiveraccount=" + faccount;
//				String json = GohttpGET(url);
//				try {
//					JSONArray array = new JSONArray(json);
//					for (int i = 0; i < array.length(); i++) {
//						JSONObject obj = array.getJSONObject(i);
//						qqmessage m = new qqmessage();
//						m.setMessagecontent(obj.optString("messagecontent"));
//						m.setMessagetime(obj.optString("messagetime"));
//						m.setReceiveraccount(obj.optString("receiveraccount"));
//						m.setReceiverheadico(obj.optString("receiverheadico"));
//						m.setReceivername(obj.optString("receivername"));
//						m.setSenderaccount(obj.optString("senderaccount"));
//						m.setSendername(obj.optString("sendername"));
//						m.setSendheadico(obj.optString("sendheadico"));
//						list.add(m);
//					}
//					handler.sendEmptyMessage(1);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//		t.start();
//	}
	private ArrayList<message >  GohttpGET(String url) {
		ArrayList<message > msglist=new ArrayList<message>();
		try {
			URL ul = new URL(url);
			HttpURLConnection urlconnection = (HttpURLConnection) ul
					.openConnection();
			urlconnection.setConnectTimeout(5000);
			urlconnection.setRequestMethod("GET");
			int code = urlconnection.getResponseCode();
			if (code == 200) {
				InputStream is = urlconnection.getInputStream();
				String json=IOUtils.toString(is);
				try {
					JSONArray list=new JSONArray(json);
					for (int i = 0; i < list.length(); i++) {
						JSONObject obj=list.getJSONObject(i);
						message m=new message();
						m.setAccount(obj.optString("senderaccount"));
						m.setContent(obj.optString("messagecontent"));
						m.setHeadico(obj.optString("senderheadico"));
						m.setMessagetime(obj.optString("messagetime"));
						m.setUsername(obj.optString("sendername"));
						msglist.add(m);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msglist;

	}
//	private String GohttpGET(String url) {
//		try {
//			URL ul = new URL(url);
//			HttpURLConnection urlconnection = (HttpURLConnection) ul.openConnection();
//			urlconnection.setConnectTimeout(5000);
//			urlconnection.setRequestMethod("GET");
//			int code = urlconnection.getResponseCode();
//			if (code == 200) {
//				InputStream is = urlconnection.getInputStream();
//				return IOUtils.toString(is);
//			} else {
//				return "ÍøÂç·ÃÎÊÊ§°Ü£¡";
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "ÍøÂç·ÃÎÊÊ§°Ü£¡";
//		}
//	}
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		LayoutInflater inflater=LayoutInflater.from(this);
		final View v = inflater.inflate(R.layout.message_fragment, null);
		msglist = (ListView) v.findViewById(R.id.message_listview);
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				qqapp app=(qqapp) getApplication();
				String useraccount=app.getUseraccount();
				String url="http://+"+Constant.http_ip+":8080/qqbackground/querymessage?useraccount="+useraccount;
				ArrayList<message > list=GohttpGET(url);
				adpter = new messageitemadpter(list,(Activity) v.getContext());
				msglist.setAdapter(adpter);
			}
		});
		t.start();
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
