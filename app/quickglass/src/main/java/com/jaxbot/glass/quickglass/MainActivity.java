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

import com.google.android.glass.app.Card;

public class MainActivity extends Activity {
	private TextToSpeech tts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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
		Card card = new Card(this);
		card.setText(getResources().getText(R.string.app_name).toString());
		View cardView = card.toView();
		setContentView(cardView);
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

