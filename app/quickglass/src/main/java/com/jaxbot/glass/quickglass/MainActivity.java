package com.jaxbot.glass.quickglass;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.res.Resources;
import android.speech.RecognizerIntent;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Color;

import android.webkit.WebView;

import com.google.android.glass.app.Card;

public class MainActivity extends Activity {
	WebView webview;
	String voiceData = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		webview = new WebView(this);

		// Prevent ugly white flash on load
		// see: http://stackoverflow.com/questions/9476151/webview-flashing-with-white-background-if-hardware-acceleration-is-enabled-an
		webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		webview.setBackgroundColor(Color.argb(1, 0, 0, 0));
	}

	@Override
	protected void onStart() {
		super.onStart();

		setContentView(webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new Javascript(this), "Glass");

		showPage();
	}


	void showPage() {
		try {
			InputStream input = getResources().openRawResource(R.raw.card);
			byte[] buffer = new byte[input.available()];
			input.read(buffer);
			String data = new String(buffer);

			webview.loadData(data, "text/html", null);
		} catch (Exception e) { webview.loadData("Error: <b>" + e.toString() + "</b>", "text/html", null);
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

