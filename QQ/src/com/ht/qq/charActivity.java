package com.ht.qq;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ht.adpter.charadapter;
import com.ht.common.Constant;
import com.ht.common.qqapp;
import com.ht.vo.qqmessage;

public class charActivity extends Activity {
	private ArrayList<qqmessage> list = new ArrayList<qqmessage>();
	String faccount;
	String fname;
	private charadapter adapter;
	private ListView listview;
	private Button sendbtn;
	private EditText sended;
	TextView textname;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {
				adapter.setData(list);
				adapter.notifyDataSetChanged();
				listview.setAdapter(adapter);
			}else if(msg.what==2){
//				listview.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
				finsh();
				handler.sendEmptyMessageDelayed(2, 2000);
				
			}
		}
	};
	private void findview() {
		// TODO Auto-generated method stub
		listview = (ListView) this.findViewById(R.id.chat_list_view);
		sendbtn = (Button) this.findViewById(R.id.btn_send);
		textname = (TextView) this.findViewById(R.id.chat_people_tv);
		sended = (EditText) this.findViewById(R.id.et_content);
	}             

	private void setlistener() {
		// TODO Auto-generated method stub
		sendbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String content=sended.getText().toString();
				sended.setText("");
				qqapp app=(qqapp) getApplication();
				final String account=app.getUseraccount();
				final String name=app.getUsername();
				Thread t=new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
//						SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日HH:mm:ss");       
//						Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
//						String    time    =    formatter.format(curDate); 
						String time="今天";
						try{
							String url="http://"+Constant.http_ip+":8080/qqbackground/sendmessage?msg.senderaccount="+account+""
									+ "&msg.sendername="+URLEncoder.encode(name, "utf-8")+"&msg.messagecontent="+URLEncoder.encode(content, "utf-8")+"&msg.messagetime="+URLEncoder.encode(time, "utf-8") +""
									+ "&msg.receivername="+URLEncoder.encode(fname, "utf-8")+"&msg.receiveraccount="+faccount;
							String json=GohttpGET(url);
							boolean bln=new Boolean(json);
							if(bln){
								handler.sendEmptyMessage(2);
							}
						}catch(Exception e){
							
						}
					}
				});
				if(content.equals("")){
					Toast.makeText(charActivity.this,"不能发空消息！", Toast.LENGTH_SHORT).show();
				}else{
					t.start();
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		findview();
		Intent intent = getIntent();
		faccount = intent.getStringExtra("faccount");
		fname = intent.getStringExtra("fname");
		textname.setText(fname);
		setlistener();
		adapter = new charadapter(list, charActivity.this);
		handler.sendEmptyMessage(2);
	}
	private void finsh() {
		// TODO Auto-generated method stub
		qqapp app = (qqapp) getApplication();
		final String sendaccount = app.getUseraccount();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				String url = "http://"
						+ Constant.http_ip
						+ ":8080/qqbackground/charusermessage?msg.senderaccount="
						+ sendaccount + "&msg.receiveraccount=" + faccount;
				
				String json = GohttpGET(url);
				list.clear();
				try {
					JSONArray array = new JSONArray(json);
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						qqmessage m = new qqmessage();
						m.setMessagecontent(obj.optString("messagecontent"));
						m.setMessagetime(obj.optString("messagetime"));
						m.setReceiveraccount(obj.optString("receiveraccount"));
						m.setReceiverheadico(obj.optString("receiverheadico"));
						m.setReceivername(obj.optString("receivername"));
						m.setSenderaccount(obj.optString("senderaccount"));
						m.setSendername(obj.optString("sendername"));
						m.setSendheadico(obj.optString("sendheadico"));
						list.add(m);
					}
					handler.sendEmptyMessage(1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
            //do something...
        	handler.removeMessages(2);
             return super.onKeyDown(keyCode, event);
         }
         return super.onKeyDown(keyCode, event);
     }
	private String GohttpGET(String url) {
		try {
			URL ul = new URL(url);
			HttpURLConnection urlconnection = (HttpURLConnection) ul.openConnection();
			urlconnection.setConnectTimeout(5000);
			urlconnection.setRequestMethod("GET");
			int code = urlconnection.getResponseCode();
			if (code == 200) {
				InputStream is = urlconnection.getInputStream();
				return IOUtils.toString(is);
			} else {
				return "网络访问失败！";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "网络访问失败！";
		}
	}
}
