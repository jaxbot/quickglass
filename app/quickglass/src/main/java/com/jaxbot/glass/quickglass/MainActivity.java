package com.jaxbot.glass.quickglass;

import android.app.Activity;
import android.view.View;
import android.os.BatteryManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;

import java.io.InputStream;

import android.webkit.WebView;

import com.google.android.glass.app.Card;

public class MainActivity extends Activity {
	private TextToSpeech tts;
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
        
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // Do nothing.
				tts.speak(getResources().getText(R.string.app_name).toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		// Display it.
		/*Card card = new Card(this);
		card.setText(getResources().getText(R.string.app_name).toString());
		View cardView = card.toView();
		setContentView(cardView);
		*/

		WebView webview = new WebView(this);
		setContentView(webview);

		webview.getSettings().setJavaScriptEnabled(true);

		//webview.loadUrl("http://google.com/");
		try {
			InputStream input = getResources().openRawResource(R.raw.card);
			byte[] buffer = new byte[input.available()];
			input.read(buffer);
			String data = new String(buffer);

			webview.loadData(data, "text/html", null);
		} catch (Exception e) {
			webview.loadData("<b>" + e.toString() + "</b>", "text/html", null);
			e.printStackTrace();
		}

	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
    public void onDestroy() {
		tts.shutdown();
        
        super.onDestroy();
	}
}

