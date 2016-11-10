package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ht.adpter.addfriendlistadapter;
import com.ht.common.Constant;
import com.ht.common.qqapp;
import com.ht.vo.qquser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class addfriendActivity extends Activity{
	private EditText edaccount;
	private Button commit;
	private ListView listview;
	private ArrayList list=new ArrayList();
	private addfriendlistadapter adapter;
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				adapter=new addfriendlistadapter(list,addfriendActivity.this);
				listview.setAdapter(adapter);
			}
			if(msg.what==2){
				Toast.makeText(addfriendActivity.this, "没有查找到用户！", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfriend);
		findview();
		serlistener();
	}
	private void findview(){
		edaccount=(EditText) findViewById(R.id.addfriend_account);
		commit=(Button) findViewById(R.id.addfriend_commit);
		listview=(ListView) findViewById(R.id.addfriend_listview);
	}
	private void serlistener(){
		commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					 Thread t=new Thread(new Runnable() {
						
						@Override
						public void run() {
							qqapp app=(qqapp) getApplication();
							String faccount=edaccount.getText().toString();
							try {
							String url="http://"+Constant.http_ip+":8080/qqbackground/queryuser?user.useraccount="+faccount;
							URL ul = new URL(url);
							HttpURLConnection urlconnection=(HttpURLConnection) ul.openConnection();
							urlconnection.setConnectTimeout(5000);
							urlconnection.setRequestMethod("GET");
							int code=urlconnection.getResponseCode();
							if(code==200){
								InputStream is=urlconnection.getInputStream();
								String json=IOUtils.toString(is);
									qquser user=new qquser();
									JSONObject obj=new JSONObject(json);
									user.setUsername(obj.optString("username"));
									user.setUseraccount(obj.optString("useraccount"));
									user.setUserheadico(obj.optString("userheadico"));
									if(user.getUsername()==null||user.getUsername().equals("")){
										handler.sendEmptyMessage(2);
									}else{
										list.add(user);
									}
								handler.sendEmptyMessage(1);
							}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					t.start();
			}
		});
	}
}
