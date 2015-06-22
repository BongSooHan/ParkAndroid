package com.example.hanandroidproject;

import com.example.hanandroidproject.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AnotherActivity extends Activity{
	
	public static final int REQUEST_CODE_ANOTHER =1001;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.another);
		
		Button returnButton =(Button) findViewById(R.id.returnButton);
		returnButton.setOnClickListener(new OnClickListener () {
			
			@Override
			public void onClick(View v) {
				close();
				
			}
		});
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			close();
			return true;
		}
		return false;
	}
	
	private void close(){
		Intent resultIntent=new Intent();
		resultIntent.putExtra("name", "ºÀ¼ö");
		
		setResult(Activity.RESULT_OK,resultIntent);
		finish();
	}

}
