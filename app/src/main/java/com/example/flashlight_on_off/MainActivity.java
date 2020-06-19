package com.example.flashlight_on_off;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    ConstraintLayout main_layout;
    TextView text_on;
    TextView text_off;
    Switch switch_on_off;
    CameraManager camManager;
    String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // AdView adView = new AdView(this);
        // adView.setAdSize(AdSize.BANNER);
        // adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        main_layout = (ConstraintLayout) findViewById(R.id.main_layout);
        text_on = (TextView) findViewById(R.id.text_on);
        text_off = (TextView) findViewById(R.id.text_off);
        switch_on_off = (Switch) findViewById(R.id.switch_on_off);

        switch_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                cameraId = null;

                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        try {
                            cameraId = camManager.getCameraIdList()[0];
                            camManager.setTorchMode(cameraId, true);   //Turn ON
                            main_layout.setBackgroundColor(getResources().getColor(R.color.color_main_layout_on));
                            text_on.setTextColor(getResources().getColor(R.color.color_text_on));
                            text_off.setTextColor(getResources().getColor(R.color.color_text_on));
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        try {
                            cameraId = camManager.getCameraIdList()[0];
                            camManager.setTorchMode(cameraId, false);
                            main_layout.setBackgroundColor(getResources().getColor(R.color.color_main_layout_off));
                            text_on.setTextColor(getResources().getColor(R.color.color_text_off));
                            text_off.setTextColor(getResources().getColor(R.color.color_text_off));
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
