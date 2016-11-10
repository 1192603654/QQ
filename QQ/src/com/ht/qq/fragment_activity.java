package com.ht.qq;

import com.ht.fragment.contact_fragment;
import com.ht.fragment.dynamic_fragment;
import com.ht.fragment.message_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class fragment_activity extends FragmentActivity{
	private Button messagebtn;
	private Button contactbtn;
	private Button dynamicbtn;
	private message_fragment msgfragment;
	private contact_fragment confragment;
	private dynamic_fragment dyfragment;
	FragmentManager manager;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment);
		findview();
		setfragment();
		manager=this.getSupportFragmentManager();
		FragmentTransaction Transaction=manager.beginTransaction();
		Transaction.replace(R.id.fragment_centre, msgfragment);
		Transaction.commit();
		setlistener();
		
	}
	private void setfragment(){
		msgfragment=new message_fragment();
		confragment=new contact_fragment();
		dyfragment=new dynamic_fragment();
	}
	private void findview() {
		// TODO Auto-generated method stub
		messagebtn=(Button) this.findViewById(R.id.fragment_messagebtn);
		contactbtn=(Button) this.findViewById(R.id.fragment_contactbtn);
		dynamicbtn=(Button) this.findViewById(R.id.fragment_dynamicbtn);
	}
	private void setlistener(){
		messagebtn.setOnClickListener(messagelistener());
		contactbtn.setOnClickListener(contactlistener());
		dynamicbtn.setOnClickListener(dynamiclistener());
	}
	private OnClickListener messagelistener(){
		OnClickListener onclick=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chagebtnbg(v.getId());
				FragmentTransaction Transaction=manager.beginTransaction();
				Transaction.replace(R.id.fragment_centre, msgfragment);
				Transaction.commit();
			}
		};
		return onclick;
	}
	private OnClickListener contactlistener(){
		OnClickListener onclick=new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chagebtnbg(v.getId());
				FragmentTransaction Transaction=manager.beginTransaction();
				Transaction.replace(R.id.fragment_centre, confragment);
				Transaction.commit();
			}
		};
		return onclick;
	}
	private OnClickListener dynamiclistener(){
		OnClickListener onclick=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chagebtnbg(v.getId());
				FragmentTransaction Transaction=manager.beginTransaction();
				Transaction.replace(R.id.fragment_centre, dyfragment);
				Transaction.commit();
			}
		};
		return onclick;
	}
	private void chagebtnbg(int id){
		messagebtn.setBackgroundResource(R.drawable.skin_tab_icon_conversation_normal);
		contactbtn.setBackgroundResource(R.drawable.skin_tab_icon_contact_normal);
		dynamicbtn.setBackgroundResource(R.drawable.skin_tab_icon_plugin_normal);
		if(id==R.id.fragment_messagebtn){
			messagebtn.setBackgroundResource(R.drawable.skin_tab_icon_conversation_selected);
		}else if(id==R.id.fragment_contactbtn){
			contactbtn.setBackgroundResource(R.drawable.skin_tab_icon_contact_selected);
		}else if(id==R.id.fragment_dynamicbtn){
			dynamicbtn.setBackgroundResource(R.drawable.skin_tab_icon_plugin_selected);
		}
	}
}
