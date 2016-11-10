package com.ht.fragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ht.adpter.contactlistadpter;
import com.ht.common.Constant;
import com.ht.common.qqapp;
import com.ht.qq.R;
import com.ht.qq.addfriendActivity;
import com.ht.vo.qqfrienduser;

public class contact_fragment extends Fragment {

	ArrayList<String> grouplist = new ArrayList<String>(3);
	ArrayList<List<qqfrienduser>> childlist = new ArrayList<List<qqfrienduser>>();
	ArrayList<qqfrienduser> flist = new ArrayList<qqfrienduser>();
	private ExpandableListView list;
	private TextView right_add;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			if (msg.what == 1) {
				list.setAdapter(new contactlistadpter(contact_fragment.this
						.getActivity(), grouplist, childlist));
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.contact_fragment, null);
		grouplist.clear();
		grouplist.add("我的好友");
		grouplist.add("好友");
		grouplist.add("陌生人");
		findview(v);
		setlistener();
		finsh();
		return v;
	}

	private void findview(View v) {
		list = (ExpandableListView) v.findViewById(R.id.contact_expandablelist);
		right_add = (TextView) v.findViewById(R.id.add);
	}

	@Override
	public void onResume() {
		super.onResume();
		finsh();
	}

	private void setlistener() {
		right_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						addfriendActivity.class);
				startActivity(intent);
			}
		});
	}

	private void finsh() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				qqapp app = (qqapp) getActivity().getApplication();
				String useraccount = app.getUseraccount();
				String url = "http://" + Constant.http_ip
						+ ":8080/qqbackground/queryallfriend?useraccount="
						+ useraccount;
				flist = GohttpGET(url);
				childlist.clear();
				for (int i = 0; i < grouplist.size(); i++) {
					ArrayList<qqfrienduser> flistl = new ArrayList<qqfrienduser>();
					for (int j = 0; j < flist.size(); j++) {
						qqfrienduser f = flist.get(j);
						if (f.getFriendgroup() == i + 1) {
							flistl.add(f);
						}
					}
					childlist.add(flistl);
				}
				handler.sendEmptyMessage(1);
			}
		});
		t.start();
	}

	private ArrayList<qqfrienduser> GohttpGET(String url) {
		ArrayList<qqfrienduser> flist = new ArrayList<qqfrienduser>();
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
				JSONArray list = new JSONArray(json);
				for (int i = 0; i < list.length(); i++) {
					JSONObject obj = list.getJSONObject(i);
					qqfrienduser fu = new qqfrienduser();
					fu.setFriendgroup(obj.getInt("friendgroup"));
					fu.setFrienduseraccount(obj.optString("frienduseraccount"));
					fu.setFrienduserheadico(obj.optString("frienduserheadico"));
					fu.setFriendusername(obj.optString("friendusername"));
					fu.setUseraccount(obj.optString("useraccount"));
					fu.setUserheadico(obj.optString("userheadico"));
					fu.setUsername(obj.optString("username"));
					flist.add(fu);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flist;

	}
}
