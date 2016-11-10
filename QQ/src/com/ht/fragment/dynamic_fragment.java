package com.ht.fragment;

import com.ht.qq.R;
import com.ht.qq.fragment_activity;
import com.ht.qq.frienddynamicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class dynamic_fragment extends Fragment{
	private RelativeLayout fdynamic;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.dynamic_fragment, null);
		findview(v);
		setlistener();
		return v;
	}
	private void findview(View v) {
		// TODO Auto-generated method stub
		fdynamic=(RelativeLayout) v.findViewById(R.id.ll_dynamic);
	}
	private void setlistener() {
		// TODO Auto-generated method stub
		fdynamic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),frienddynamicActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}
}
