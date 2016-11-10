package com.ht.pupwindow;

import com.ht.fragment.dynamic_fragment;
import com.ht.qq.R;
import com.ht.qq.dynamic_writesaysay_Activity;
import com.ht.qq.frienddynamicActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;

public class Dynamic_pupwindow extends PopupWindow{
	private View conentView;
	public Dynamic_pupwindow(final Activity context) {
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=LayoutInflater.from(context);
		conentView=inflater.inflate(R.layout.dynamic_popupwindow, null);
		
		 int h = context.getWindowManager().getDefaultDisplay().getHeight();
	        int w = context.getWindowManager().getDefaultDisplay().getWidth(); 
	        
	        // ����SelectPicPopupWindow��View  
	        this.setContentView(conentView);  
//	        // ����SelectPicPopupWindow��������Ŀ�  
//	        this.setWidth(w / 2 + 50);  
//	        // ����SelectPicPopupWindow��������ĸ�  
//	        this.setHeight(LayoutParams.WRAP_CONTENT);  
	        // ����SelectPicPopupWindow��������ɵ��  
	        this.setFocusable(true);  
	        this.setOutsideTouchable(true);  
	        // ˢ��״̬  
	        this.update();  
	        // ʵ����һ��ColorDrawable��ɫΪ��͸��  
	        ColorDrawable dw = new ColorDrawable(0000000000);  
	        // ��back���������ط�ʹ����ʧ,������������ܴ���OnDismisslistener �����������ؼ��仯�Ȳ���  
	        this.setBackgroundDrawable(dw);  
	        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
	        // ����SelectPicPopupWindow�������嶯��Ч��  
	        this.setAnimationStyle(R.style.AnimationPreview); 
	        
	        this.setWidth(LayoutParams.MATCH_PARENT);
	        this.setHeight(LayoutParams.WRAP_CONTENT);
	        
	        ImageView img=(ImageView) conentView.findViewById(R.id.popup_exit);
	        img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Dynamic_pupwindow.this.dismiss();
				}
			});
	        ImageView saysay=(ImageView) conentView.findViewById(R.id.popup_shuoshuo);
	        saysay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,dynamic_writesaysay_Activity.class);
					context.startActivity(intent);
					Dynamic_pupwindow.this.dismiss();
				}
			});
	}
	public void showPopupWindow(View parent) {  
        if (!this.isShowing()) {  
            this.showAsDropDown(parent);
        } else {  
            this.dismiss();  
        }  
    }  
}
