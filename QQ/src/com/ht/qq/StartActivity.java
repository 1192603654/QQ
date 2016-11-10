package com.ht.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class StartActivity extends Activity{
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message message) {
			// TODO Auto-generated method stub
			super.dispatchMessage(message);
			if(message.what==0){
				Intent intent=new Intent(StartActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_start);
		handler.sendEmptyMessageDelayed(0, 2000);
	}
}
