package com.mandiri.application;

import android.util.Base64;
import android.util.Log;

import kotlin.text.Charsets;

/**
 * Created by Eminarti Sianturi on 02/09/22
 */
public class Keys {

    static {
        try {
            System.loadLibrary("native-lib");
        } catch (UnsatisfiedLinkError e) {
            // do nothing
            Log.e("Keys", e.getMessage());
        }
    }

    public static native String getYoutubeApiKeyNative();

    public static native String getMovieApiKeyNative();

    public static String getYoutubeApiKey() {
        try {
            return getDecodedString(getYoutubeApiKeyNative());
        } catch (UnsatisfiedLinkError e) {
            return "";
        }
    }

    public static String getMovieApiKey() {
        try {
            return getDecodedString(getMovieApiKeyNative());
        } catch (UnsatisfiedLinkError e) {
            return "";
        }
    }

    private static String getDecodedString(String key) {
        return new String(Base64.decode(key, Base64.DEFAULT), Charsets.UTF_8);
    }
}
