package com.handroid.mytownamp.pbangjariandroid.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
 * Created by KimJeongMin on 2017-12-17.
 */

public class IntroActivity extends Activity {
    Animation move;
    TextView imag;
    Handler handler = new Handler();

    Runnable r = new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        imag = (TextView) findViewById(R.id.textView);
        move = AnimationUtils.loadAnimation(
                IntroActivity.this, R.anim.introanim);
        imag.startAnimation(move);
        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//테스트
                checkPermission();
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
* move = AnimationUtils.loadAnimation(MainActivity.this, R.anim.move);
                imag.startAnimation(move);
* */
