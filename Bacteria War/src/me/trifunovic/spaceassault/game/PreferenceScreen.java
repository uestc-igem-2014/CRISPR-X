package me.trifunovic.spaceassault.game;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class PreferenceScreen extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//SetFullWindows();
		setupView();
	}
	
	private void setupView() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		addPreferencesFromResource(R.layout.options);
	}

	private void SetFullWindows() {
		// ÎÞtitle
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// È«ÆÁ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
