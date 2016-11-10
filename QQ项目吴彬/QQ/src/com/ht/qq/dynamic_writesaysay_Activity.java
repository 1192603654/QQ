package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;

import com.ht.common.Constant;
import com.ht.common.qqapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class dynamic_writesaysay_Activity extends Activity {
	private Button writebtn;
	private Button exitbtn;
	private EditText writetext;
	String account = null;
	String name = null;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {
//				Intent intent = new Intent(dynamic_writesaysay_Activity.this,
//						frienddynamicActivity.class);
//				startActivity(intent);
				finish();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_writesaysay);
		findview();
		setlistener();
		account = ((qqapp) getApplication()).getUseraccount();
		name = ((qqapp) getApplication()).getUsername();
	}

	private void findview() {
		writebtn = (Button) this.findViewById(R.id.writesay_publish);
		exitbtn = (Button) this.findViewById(R.id.writesay_cancel);
		writetext = (EditText) this.findViewById(R.id.writesay_saysay);
	}

	private String GohttpGET(String url) {
		try {
			URL ul = new URL(url);
			HttpURLConnection urlconnection = (HttpURLConnection) ul
					.openConnection();
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

	private void setlistener() {
		// 发表动态
		writebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						String dynamiccontent = writetext.getText().toString();
						SimpleDateFormat formate = new SimpleDateFormat(
								"yyyy-MM-dd/HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						String dynamictime = formate.format(cal.getTime());
						// String dynamictime="今天21:12";
						try {
							String url = "http://"
									+ Constant.http_ip
									+ ":8080/qqbackground/insertdynamic?dynamic.senderaccount="
									+ account
									+ "&dynamic.sendername="
									+ URLEncoder.encode(name, "utf-8")
									+ "&dynamic.dynamiccontent="
									+ URLEncoder
											.encode(dynamiccontent, "utf-8")
									+ "&dynamic.sendertime="
									+ URLEncoder.encode(dynamictime, "utf-8");
							String json = GohttpGET(url);
							boolean bln = new Boolean(json);
							if (bln) {
								handler.sendEmptyMessage(1);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
				t.start();
			}
		});
		// 取消按钮
		exitbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(dynamic_writesaysay_Activity.this,
//						frienddynamicActivity.class);
//				startActivity(intent);
				finish();
			}
		});
		// 监听输入
		writetext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@SuppressWarnings("deprecation")
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!s.toString().equals("")) {
					writebtn.setTextColor(getResources().getColor(
							R.color.whites));
					writebtn.setEnabled(true);
				} else {
					writebtn.setEnabled(false);
				}
			}
		});
	}
}
