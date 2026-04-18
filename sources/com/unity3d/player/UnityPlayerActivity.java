package com.unity3d.player;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public class UnityPlayerActivity extends Activity implements IUnityPlayerLifecycleEvents {
    protected UnityPlayer mUnityPlayer;

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerQuitted() {
    }

    protected String updateUnityCommandLineArguments(String cmdLine) {
        return cmdLine;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        getIntent().putExtra("unity", updateUnityCommandLineArguments(getIntent().getStringExtra("unity")));
        UnityPlayer unityPlayer = new UnityPlayer(this, this);
        this.mUnityPlayer = unityPlayer;
        setContentView(unityPlayer);
        this.mUnityPlayer.requestFocus();
    }

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerUnloaded() {
        moveTaskToBack(true);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        this.mUnityPlayer.newIntent(intent);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mUnityPlayer.destroy();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mUnityPlayer.pause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mUnityPlayer.resume();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        this.mUnityPlayer.lowMemory();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == 15) {
            this.mUnityPlayer.lowMemory();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mUnityPlayer.configurationChanged(newConfig);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.mUnityPlayer.windowFocusChanged(hasFocus);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == 2) {
            return this.mUnityPlayer.injectEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    @Override // android.app.Activity
    public boolean onGenericMotionEvent(MotionEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }
}
