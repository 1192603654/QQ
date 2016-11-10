package com.ht.SQlite;



import com.ht.common.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class logSQlite extends SQLiteOpenHelper{

	public logSQlite(Context contect) {
		// TODO Auto-generated constructor stub
		//创建数据库
		super(contect, Constant.DATABASE_NAME , null, 2);
	}
	public logSQlite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//创建表
		StringBuffer buffer=new StringBuffer();
		buffer.append("create table "+ Constant.TABLE_USER_NAME+"(")
		.append(Constant.USER_UID+" integer primary key autoincrement ,")
		.append(Constant.USER_UNAME +" text ,")
		.append(Constant.USER_PWD +" text )");
		db.execSQL(buffer.toString());
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(oldVersion<newVersion){
			db.execSQL("drop table  if exists "+Constant.TABLE_USER_NAME);
			onCreate(db);
		}
		//升级	
		
	}

}
