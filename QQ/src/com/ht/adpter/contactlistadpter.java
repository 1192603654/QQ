package com.ht.adpter;

import java.util.ArrayList;
import java.util.List;

import com.ht.qq.R;
import com.ht.qq.charActivity;
import com.ht.qq.fragment_activity;
import com.ht.vo.qqfrienduser;
import com.ht.vo.qqmessage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class contactlistadpter extends BaseExpandableListAdapter{
	ArrayList<String> grouplist=new ArrayList<String>();
	ArrayList<List<qqfrienduser>> childlist=new ArrayList<List<qqfrienduser>>();
	Activity activity;
	public contactlistadpter(Activity activity,ArrayList groplist,ArrayList<List<qqfrienduser>> cildlist) {
		// TODO Auto-generated constructor stub
		this.grouplist=groplist;
		this.childlist=cildlist;
		this.activity=activity;
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return grouplist.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childlist.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return grouplist.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childlist.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String name=grouplist.get(groupPosition);
		LayoutInflater in=LayoutInflater.from(activity);
		View v=in.inflate(R.layout.contact_fragment_group, null);
		TextView text=(TextView) v.findViewById(R.id.contact_fragment_groupname);
		text.setText(name);
		ImageView im=(ImageView) v.findViewById(R.id.fragmenr_left_group);
		if(isExpanded){
			im.setImageResource(R.drawable.skin_indicator_expanded_theme_version2);
		}else{
			im.setImageResource(R.drawable.skin_indicator_unexpanded);
		}
		
		return v;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		qqfrienduser f=childlist.get(groupPosition).get(childPosition);
		LayoutInflater in=LayoutInflater.from(activity);
		View v=in.inflate(R.layout.contact_fragment_friendlist, null);
		TextView textname=(TextView) v.findViewById(R.id.contact_friendname);
		textname.setText(f.getFriendusername());
		TextView textmsg=(TextView) v.findViewById(R.id.contact_friendmessage);
		textmsg.setText("[5GÔÚÏß]"+f.getFriendusername());
		setclidlistener(v,f);
		return v;
	}
	private void setclidlistener(View v,final qqfrienduser f) {
		// TODO Auto-generated method stub
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(activity,charActivity.class);
				intent.putExtra("faccount", f.getFrienduseraccount());
				intent.putExtra("fname", f.getFriendusername());
				activity.startActivity(intent);
			}
		});
		
	}
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
