package com.ht.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ht.adpter.dynamiclistadpter;
import com.ht.common.Constant;
import com.ht.common.qqapp;
import com.ht.pupwindow.Dynamic_pupwindow;
import com.ht.vo.qqdynamic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class frienddynamicActivity extends Activity {
	private dynamiclistadpter adapter;
	private ListView listview;
	private Button rightadd;
	private TextView leftback;
	private View top_p;
	private ArrayList<qqdynamic> dlist = new ArrayList<qqdynamic>();
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {
				adapter = new dynamiclistadpter(dlist,
						frienddynamicActivity.this);
				listview.setAdapter(adapter);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frienddyanmic);
		findview();
		setlistener();
		LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.frienddynamic_headitem, null);
		listview.addHeaderView(v);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		finsh();
	}
	private void finsh() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				qqapp app = (qqapp) getApplication();
				String url = "http://" + Constant.http_ip
						+ ":8080/qqbackground/querydynamic?useraccount="
						+ app.getUseraccount();
				String json = GohttpGET(url);
				dlist.clear();
				try {
					JSONArray josnarray = new JSONArray(json);
					for (int i = 0; i < josnarray.length(); i++) {
						JSONObject obj = josnarray.getJSONObject(i);
						qqdynamic qd = new qqdynamic();
						qd.setDynamiccontent(obj.optString("dynamiccontent"));
						qd.setSenderaccount(obj.optString("senderaccount"));
						qd.setSenderheadico(obj.optString("senderheadico"));
						qd.setSendername(obj.optString("sendername"));
						qd.setSendertime(obj.getString("sendertime"));
						dlist.add(qd);
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

	private void findview() {
		listview = (ListView) this.findViewById(R.id.friend_dynamic_item);
		rightadd = (Button) this.findViewById(R.id.friend_dynamic_add);
		leftback = (TextView) this
				.findViewById(R.id.friend_dynamic_backdynamic);
		top_p = this.findViewById(R.id.top_popup);
	}

	private void setlistener() {
		leftback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frienddynamicActivity.this.finish();
			}
		});
		rightadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dynamic_pupwindow popup = new Dynamic_pupwindow(
						frienddynamicActivity.this);
				popup.showPopupWindow(top_p);
			}
		});
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
				return "ÍøÂç·ÃÎÊÊ§°Ü£¡";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ÍøÂç·ÃÎÊÊ§°Ü£¡";
		}
	}
}
