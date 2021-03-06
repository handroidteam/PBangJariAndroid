package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.handroid.mytownamp.pbangjariandroid.R;

/**
 * Created by Jeongmin on 2018-02-02.
 */

public class Pcbang_Intro_Activity extends Activity {
    Animation move;
    TextView imag;
    Handler handler = new Handler();

    Runnable r = new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(Pcbang_Intro_Activity.this, Pcbang_Main_Activity.class);
            startActivity(intent);
            finish();
        }
    };

    //key0 kjm qwe123

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        imag = findViewById(R.id.textView);
        move = AnimationUtils.loadAnimation(
                Pcbang_Intro_Activity.this, R.anim.introanim);
        imag.startAnimation(move);
        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//테스트
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;   //SDK의 레벨을 받아온다.
                if (currentapiVersion >= Build.VERSION_CODES.M) {
                    // 현재 디바이스의 버전이 마시멜로우 이상일 경우.
                    checkPermission();
                } else {
                    // 현재 디바이스의 버전이 마시멜로우 미만일 경우.
                    handler.postDelayed(r, 500);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    123);


        } else {
            // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
            Log.d("data_permission", "Permission 항상허용");
            handler.postDelayed(r, 500);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("data_permission", "Permission always ??");
                    handler.postDelayed(r, 500);
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d("data_permission", "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r);
    }
}

/*
* move = AnimationUtils.loadAnimation(Pcbang_Intro_Activity.this, R.anim.move);
                imag.startAnimation(move);
* */

