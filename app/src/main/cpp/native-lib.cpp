#include <jni.h>
#include <iostream>

extern "C" {

    JNIEXPORT jstring JNICALL
    Java_com_mandiri_application_Keys_getYoutubeApiKeyNative(JNIEnv *env, jclass) {
        return env->NewStringUTF("QUl6YVN5QnY3Y1RqR1NZNHhtTjJ0WW5NU2NwcHdjUUszLVpRSVpr");
    }

    JNIEXPORT jstring JNICALL
    Java_com_mandiri_application_Keys_getMovieApiKeyNative(JNIEnv *env, jclass) {
        return env->NewStringUTF("NDkwYzAzM2RjYTA5NjNhY2I5NjM2N2MyYTU2ZDFhNjE=");
    }
}