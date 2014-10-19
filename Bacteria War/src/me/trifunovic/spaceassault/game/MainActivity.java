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

public class MainActivity extends Activity implements OnClickListener {

private Button btnStart;
private Button btnSetting;

//	private int SCREEN_WIDTH, SCREEN_HEIGHT; // ÆÁÄ»¸ß¿í
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SetFullWindows();
		setupView();
		setListener();
	}
	
	private void SetFullWindows() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ÎÞtitle
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// È«ÆÁ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**ÉèÖÃ¼àÌýÆ÷*/
	private void setListener() {
		btnStart.setOnClickListener(this);
		btnSetting.setOnClickListener(this);
	}
	
	private void setupView() {
		setContentView(R.layout.mymain);
		btnStart=(Button) findViewById(R.id.button1);
		btnSetting=(Button) findViewById(R.id.button2);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button1:
			Intent intent1 = new Intent(MainActivity.this, GameActivity.class); // Æô¶¯Activity
			startActivity(intent1);
			break;
		case R.id.button2:
			Intent intent3 = new Intent(MainActivity.this, PreferenceScreen.class); // Æô¶¯Activity
			startActivity(intent3);
			break;
		default:
			break;
		}
	}
}
