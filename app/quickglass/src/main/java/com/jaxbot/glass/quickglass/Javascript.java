package com.jaxbot.glass.quickglass;

import android.speech.tts.TextToSpeech;
import android.content.Context;

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

	public void say(String str) {
		tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
	}

	public int echo(int s) {
		return s;
	}
}

