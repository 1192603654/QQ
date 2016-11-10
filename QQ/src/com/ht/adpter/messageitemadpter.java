package com.ht.adpter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ht.bean.message;
import com.ht.qq.R;
import com.ht.qq.StartActivity;
import com.ht.qq.charActivity;

public class messageitemadpter extends BaseAdapter{
	private ArrayList<message> list;
	private Activity context;
	public messageitemadpter(ArrayList<message> list,Activity context) {
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = null;
		handlor han=new handlor();
		message m=list.get(position);
		if(convertView==null){
			LayoutInflater Inflater=LayoutInflater.from(context);
			v=Inflater.inflate(R.layout.message_listview_item, null);
			han.headico=(ImageView) v.findViewById(R.id.message_listview_item_headico);
			han.message=(TextView) v.findViewById(R.id.message_listview_item_content);
			han.username=(TextView) v.findViewById(R.id.message_listview_item_username);
			han.messagetime=(TextView) v.findViewById(R.id.message_listview_item_messagetime);
			convertView=v;
			convertView.setTag(han);
		}else{
			v=convertView;
			han.headico=(ImageView) v.findViewById(R.id.message_listview_item_headico);
			han.message=(TextView) v.findViewById(R.id.message_listview_item_content);
			han.username=(TextView) v.findViewById(R.id.message_listview_item_username);
			han.messagetime=(TextView) v.findViewById(R.id.message_listview_item_messagetime);
		}
		
		han.message.setText(m.getContent());
		han.username.setText(m.getUsername());
		han.messagetime.setText(m.getMessagetime());
		setlistener(v, m);
		return v;
	}
	private void setlistener(View v,final message m) {
		// TODO Auto-generated method stub
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context,charActivity.class);
				intent.putExtra("faccount", m.getAccount());
				intent.putExtra("fname", m.getUsername());
				context.startActivity(intent);
			}
		});
	}
	class handlor{
		ImageView headico;
		TextView username,message,messagetime;
	}
}
