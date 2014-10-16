package me.trifunovic.spaceassault.game;

import me.trifunovic.spaceassault.game.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.ImageView;

public class MainActivity extends Activity {

//	private int SCREEN_WIDTH, SCREEN_HEIGHT; // 屏幕高宽
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SetFullWindows();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		SCREEN_WIDTH = dm.widthPixels;
//		SCREEN_HEIGHT = dm.heightPixels;

		setContentView(R.layout.mymain);
//		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		Button btnStart =  (Button) findViewById(R.id.button1);
		
//		iv.setAlpha(90);

		btnStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, GameActivity.class); // 启动Activity
				startActivity(intent);
			}
		});
	}
	
	private void SetFullWindows() {
		// 无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
