package com.example.kayo.myapplication.mvp;

import android.content.Intent;
import android.os.Bundle;

public interface PresenterDelegate {
    void onNewIntent(Intent mIntent);

    void onCreate(Bundle bundle,Intent mIntent);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
