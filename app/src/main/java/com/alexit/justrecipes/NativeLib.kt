package com.alexit.justrecipes

object NativeLib {
    object NativeLib {
        init {
            System.loadLibrary("native-lib")
        }

        // JNI binding - implemented in native-lib.cpp
        external fun getApiKey(): String
    }
}