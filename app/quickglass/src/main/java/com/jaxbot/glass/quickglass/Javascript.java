package com.jaxbot.glass.quickglass;

import android.webkit.JavascriptInterface;
import android.speech.tts.TextToSpeech;

public class Javascript {
	private TextToSpeech tts;

	public Javascript() {
        tts = new TextToSpeech(this);
	}

	@JavascriptInterface
	public void say(String str) {
		tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
	}

    @JavascriptInterface
	public int echo(int s) {
		return s;
	}
}

