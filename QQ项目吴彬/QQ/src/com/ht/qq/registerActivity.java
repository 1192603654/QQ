package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.ht.common.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class registerActivity extends Activity{
	private EditText username;
	private EditText userpwd;
	private Button commit;
	private String account;
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				Toast.makeText(registerActivity.this, "’À∫≈ªÚ√‹¬Î”–ŒÛ£°", Toast.LENGTH_LONG).show();
			}else if(msg.what==2){
				Toast.makeText(registerActivity.this, "«ÎºÏ≤ÈÕ¯¬Á£°", Toast.LENGTH_LONG).show();
			}else if(msg.what==3){
				Intent intent=new Intent(registerActivity.this, LoginActivity.class);
				intent.putExtra("account", account);
				startActivity(intent);
				finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_test);
		findview();
		setlintener();
	}
	private void findview(){
		username=(EditText) this.findViewById(R.id.register_username);
		userpwd=(EditText) this.findViewById(R.id.register_userpwd);
		commit=(Button) this.findViewById(R.id.register_commit);
	}
	private void setlintener(){
		commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uname=username.getText().toString();
				String upwd=userpwd.getText().toString();
					Thread t=new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							//Ã·Ωª◊¢≤·
						
							 try {
								 
								 String uname=username.getText().toString();
									String upwd=userpwd.getText().toString();
									account=getaccount();
									String url="http://"+Constant.http_ip+":8080/qqbackground/insertuser?user.username="+URLEncoder.encode(uname, "utf-8")
												+"&user.userpwd="+upwd
												+"&user.userheadico="
												+"&user.useraccount="+account;
									URL ul=new URL(url);
									HttpURLConnection urlconnection=(HttpURLConnection) ul.openConnection();
									urlconnection.setConnectTimeout(5000);
									urlconnection.setRequestMethod("GET");
									int code=urlconnection.getResponseCode();
									if(code==200){
										InputStream is=urlconnection.getInputStream();
										String json=IOUtils.toString(is);
										boolean bln=new Boolean(json);
										if(bln){
											handler.sendEmptyMessage(3);
										}else{
											handler.sendEmptyMessage(1);
										}
									}else{
										handler.sendEmptyMessage(2);
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
	private String getaccount(){
		Random ran=new Random();
		int i=ran.nextInt(100000000);
		i=i+99999999;
		return ""+i;
	}
}
