package com.roni.library.sdk.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.roni.library.sdk.LifecycleObserver;
import com.roni.library.sdk.NativeRuntime;
import com.roni.library.sdk.RuntimeSession;

/**
 * Standard host Activity tying Activity lifecycle events directly into NativeRuntimeSDK.
 */
public class NativeRuntimeActivity extends AppCompatActivity implements LifecycleObserver {

    protected NativeRuntime runtime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runtime = NativeRuntime.getInstance();
        if (!runtime.isInitialized()) {
            runtime.init(this);
        }
        runtime.addObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (runtime != null && runtime.isInitialized()) {
            runtime.resumeSession();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (runtime != null && runtime.isInitialized()) {
            runtime.pauseSession();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (runtime != null) {
            runtime.removeObserver(this);
        }
    }

    @Override
    public void onInitialized(NativeRuntime runtime) {}

    @Override
    public void onSessionStarted(RuntimeSession session) {}

    @Override
    public void onSessionPaused(RuntimeSession session) {}

    @Override
    public void onSessionResumed(RuntimeSession session) {}

    @Override
    public void onSessionStopped(RuntimeSession session) {}

    @Override
    public void onReleased() {}

    @Override
    public void onError(int errorCode, String errorMessage) {}
}
