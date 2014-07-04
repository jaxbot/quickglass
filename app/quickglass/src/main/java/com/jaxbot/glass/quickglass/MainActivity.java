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
import android.net.Uri;

import com.google.android.glass.app.Card;
import com.jaxbot.glass.barcode.scan.CaptureActivity;
import android.speech.tts.TextToSpeech;

import android.webkit.JavascriptInterface;

public class MainActivity extends Activity {
	final int SCAN_QR = 4;

	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		webview = new WebView(this);

		// Prevent ugly white flash on load
		// see: http://stackoverflow.com/questions/9476151/webview-flashing-with-white-background-if-hardware-acceleration-is-enabled-an
		webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		webview.setBackgroundColor(Color.argb(1, 0, 0, 0));

		setContentView(webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new Javascript(this), "Glass");

		showPage();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SCAN_QR) {
			if (resultCode == RESULT_OK) {
				Bundle res = data.getExtras();
				String qrdata = res.getString("qr_data").toString();
				Log.d("Q", qrdata);
				webview.loadUrl("javascript:scanBarcodeCallback(\"" + qrdata + "\");");
			}
		}
	}

	void showPage() {
		try {
			InputStream input = getResources().openRawResource(R.raw.card);
			byte[] buffer = new byte[input.available()];
			input.read(buffer);
			String data = new String(buffer);

			if (getIntent().getExtras() != null) {
				ArrayList<String> voiceResults = getIntent().getExtras()
					.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

				if (voiceResults != null) {
					String voiceData = voiceResults.get(0);
					if (voiceData != null && voiceData != "")
						data += "<script>if (voicePromptCallback) voicePromptCallback(\"" + voiceData + "\");</script>";
				}
			}

			webview.loadData(data, "text/html", null);
		} catch (Exception e) {
			webview.loadData("Error: <b>" + e.toString() + "</b>", "text/html", null);
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

	public class Javascript {
		private TextToSpeech tts;
		private Context context;

		public Javascript(Context context) {
			tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
				@Override
				public void onInit(int status) {
					// Do nothing.
				}
			});

			this.context = context;
		}

		@JavascriptInterface
		public void say(String str) {
			tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
		}

		@JavascriptInterface
		public int echo(int s) {
			return s;
		}

		@JavascriptInterface
		public void scanBarcode() {
			Intent intent = new Intent(context, CaptureActivity.class);
			startActivityForResult(intent, SCAN_QR);
		}
	}
}

