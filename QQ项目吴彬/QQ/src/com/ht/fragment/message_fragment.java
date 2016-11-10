package com.ht.fragment;

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

import android.R.array;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class message_fragment extends Fragment {
	private messageitemadpter adpter;
	private ListView msglist;
	private ArrayList<message> list=new ArrayList<message>();
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 0) {
				msglist.setAdapter(adpter);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.message_fragment, null);
		msglist = (ListView) v.findViewById(R.id.message_listview);
		finsh();
		
		return v;
	}

	private void finsh() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				qqapp app = (qqapp) getActivity().getApplication();
				String useraccount = app.getUseraccount();
				String url = "http://" + Constant.http_ip
						+ ":8080/qqbackground/querymessage?useraccount="
						+ useraccount;
				ArrayList<message> alist = GohttpGET(url);
				list=alist;
				adpter = new messageitemadpter(list, getActivity());
				handler.sendEmptyMessage(0);
			}
		});
		t.start();
	}

	private ArrayList<message> GohttpGET(String url) {
		ArrayList<message> msglist = new ArrayList<message>();
		try {
			URL ul = new URL(url);
			HttpURLConnection urlconnection = (HttpURLConnection) ul
					.openConnection();
			urlconnection.setConnectTimeout(5000);
			urlconnection.setRequestMethod("GET");
			int code = urlconnection.getResponseCode();
			if (code == 200) {
				InputStream is = urlconnection.getInputStream();
				String json = IOUtils.toString(is);
				try {
					JSONArray list = new JSONArray(json);
					for (int i = 0; i < list.length(); i++) {
						JSONObject obj = list.getJSONObject(i);
						message m = new message();
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
}
