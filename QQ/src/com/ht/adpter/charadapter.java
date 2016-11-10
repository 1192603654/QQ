package com.ht.adpter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.ht.common.Constant;
import com.ht.common.qqapp;
import com.ht.qq.R;
import com.ht.vo.qqmessage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class charadapter extends BaseAdapter{
	private ArrayList<qqmessage> list;
	private Activity context;
	public charadapter(ArrayList list,Activity context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
	}
	
	public void setData(ArrayList list){
		this.list = list;
		list.clone();
		notifyDataSetChanged();
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
		LayoutInflater inflater=LayoutInflater.from(context);
		qqapp app=(qqapp) context.getApplication();
		View left=inflater.inflate(R.layout.chat_item_left, null);
		View right=inflater.inflate(R.layout.chat_item_right, null);
		qqmessage m=list.get(position);
		if(m.getSenderaccount().equals(app.getUseraccount())){
			TextView textmsg=(TextView) right.findViewById(R.id.chat_right_tv);
			textmsg.setText(m.getMessagecontent());
			return right;
		}else{
			TextView textmsg=(TextView) left.findViewById(R.id.chat_left_tv);
			textmsg.setText(m.getMessagecontent());
		}
		return left;
	}
}
