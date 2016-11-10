package com.ht.adpter;

import java.util.ArrayList;

import com.ht.qq.R;
import com.ht.qq.R.layout;
import com.ht.vo.qqdynamic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class dynamiclistadpter extends BaseAdapter{
	ArrayList<qqdynamic> list;
	Context context;
	public dynamiclistadpter(ArrayList<qqdynamic> list,Context context) {
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
		LayoutInflater inflater=LayoutInflater.from(context);
		View v=inflater.inflate(R.layout.dynamic_listview_item, null);
		qqdynamic d=list.get(position);
		TextView textname=(TextView) v.findViewById(R.id.dynamic_username);
		TextView texttime=(TextView) v.findViewById(R.id.dynamic_time);
		TextView textcontent=(TextView) v.findViewById(R.id.dynamic_content);
		ImageView imghead=(ImageView) v.findViewById(R.id.dynamic_headico);
		textname.setText(d.getSendername());
		texttime.setText(d.getSendertime());
		textcontent.setText(d.getDynamiccontent());
		return v;
	}

}
