package com.ht.qq;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ht.SQlite.logSQlite;
import com.ht.common.Constant;
import com.ht.common.qqapp;

public class LoginActivity extends Activity {

	private EditText useraccount;
	private EditText userpwd;
	private Button commit;
	private TextView registerbtn;
	ProgressDialog dialog;
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				Intent intent=new Intent(LoginActivity.this,fragment_activity.class);
				startActivity(intent);
				dialog.dismiss();
				finish();
			}
			if(msg.what==2){
				Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findview();
		Intent intent=getIntent();
		useraccount.setText(intent.getStringExtra("account"));
		setlistener();
//		logSQlite log=new logSQlite(LoginActivity.this);
//		//创建数据库
//		SQLiteDatabase db=log.getReadableDatabase();
//		Cursor cursor=db.query(Constant.TABLE_USER_NAME, new String[]{Constant.USER_UNAME}, "", null, null, null, null);
	}
	private void findview(){
		useraccount=(EditText) this.findViewById(R.id.log_useraccount);
		userpwd=(EditText) this.findViewById(R.id.log_userpwd);
		commit=(Button) this.findViewById(R.id.log_commit);
		registerbtn=(TextView) this.findViewById(R.id.log_resgister);
	}
	private void setlistener(){
		useraccount.addTextChangedListener(useraccountlistener());
		commit.setOnClickListener(commitclicklistener());
		registerbtn.setOnClickListener(registerlistener());
	}
	private OnClickListener registerlistener(){
		OnClickListener l=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,registerActivity.class);
				startActivity(intent);
				finish();
			}
		};
		return l;
	}
	private TextWatcher useraccountlistener(){
		TextWatcher t=new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String password=null;
				logSQlite log=new logSQlite(LoginActivity.this);
				//创建数据库
				SQLiteDatabase db=log.getReadableDatabase();
				Cursor cursor=db.query(Constant.TABLE_USER_NAME, new String[]{Constant.USER_PWD}, Constant.USER_UNAME+" = ?", new String[]{s.toString()}, null, null, null);
				if(!cursor.isLast()){
					while (cursor.moveToNext()) {
						password=cursor.getString(cursor.getColumnIndex(Constant.USER_PWD));
						userpwd.setText(password);
					}
					if(cursor.getCount()<=0){
						userpwd.setText("");
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		};
		return t;
	}
	private OnClickListener commitclicklistener(){
		OnClickListener l=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog=new ProgressDialog(LoginActivity.this);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setMessage("登录中。。。");
				dialog.show();
				//请求网络
				Thread th=new Thread(new Runnable() {
						
						@Override
						public void run() {
							String username=useraccount.getText().toString();
							String password=userpwd.getText().toString();
							String bn = null;
							logSQlite log=new logSQlite(LoginActivity.this);
							//创建数据库
							SQLiteDatabase db=log.getReadableDatabase();
							//插入数据
							ContentValues values=new ContentValues();
							values.put(Constant.USER_UNAME, username);
							values.put(Constant.USER_PWD, password);
							//查找有没有一样的账号
							Cursor cursor=db.query(Constant.TABLE_USER_NAME, new String[]{Constant.USER_UNAME}, Constant.USER_UNAME+" = ?", new String[]{username}, null, null, null);
							if(!cursor.isLast()){
								while (cursor.moveToNext()) {
									bn=cursor.getString(cursor.getColumnIndex(Constant.USER_UNAME));
								}
							}
							String url="http://"+Constant.http_ip+":8080/qqbackground/loguser?user.useraccount="+username+"&user.userpwd="+password;
							String json=GohttpGET(url);
							
							boolean bon=new Boolean(json);
							//登录成功
							if(bon){
								if(bn==null){
									db.insert(Constant.TABLE_USER_NAME, Constant.USER_UID, values);
								}
								String urldata="http://"+Constant.http_ip+":8080/qqbackground/queryuser?user.useraccount="+username;
								String jsonstr=GohttpGET(urldata);
								try {
									JSONObject user=new JSONObject(jsonstr);
									qqapp app=(qqapp) getApplication();
									app.setUsername(user.optString("username"));
									app.setUseraccount(user.optString("useraccount"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								db.close();
								cursor.close();
								handler.sendEmptyMessage(1);
							}else{
							//登录失败
								handler.sendEmptyMessage(2);
							}
						}
					});
					th.start();
			}
		};
		return l;
	}
	
	private String GohttpGET(String url){
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
				return "网络访问失败！";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "网络访问失败！";
		}
	}
}
