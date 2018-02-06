package com.handroid.mytownamp.pbangjariandroid.Common;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handroid.mytownamp.pbangjariandroid.R;

import pl.polidea.view.ZoomView;

import static android.widget.ImageView.ScaleType.FIT_CENTER;

/**
 * Created by KimJeongMin on 2017-12-13.
 */

public class ViewSeat {
    public ZoomView SettingPcMap(Context mContext, int rows, int colums, int[] data, LinearLayout container, View inflateView, LinearLayout pcmap) {
        //ScrollView rosScroll = new ScrollView(mContext);
       // HorizontalScrollView colScroll = new HorizontalScrollView(mContext);


        GridLayout grid = new GridLayout(mContext); //새로생성
        GridLayout.LayoutParams layoutParam = new GridLayout.LayoutParams(container.getLayoutParams()); //view그륩의 layoutparams 가져와서 넣기
        layoutParam.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParam.height = GridLayout.LayoutParams.WRAP_CONTENT;

        grid.setLayoutParams(layoutParam);
        grid.setColumnCount(colums); //가로세로 설정
        grid.setRowCount(rows );

        int count = 0;
        try {
            for (int c = 0; c < data.length; c++) {
                ImageView imageView= new ImageView(mContext);
              // imageView.setScaleType(FIT_CENTER);
                imageView.setMaxWidth(90);
                imageView.setMaxHeight(50);

                TextView tmps = new TextView(mContext);
               // tmps.setMaxWidth(15);

                tmps.setWidth(90);
                tmps.setHeight(70);
                tmps.setGravity(Gravity.CENTER);
                  /*  if (data[count] == 0) {
                        tmps.setBackgroundResource(R.drawable.unusable1);
                    } else if (data[count] == 1) {
                        tmps.setBackgroundResource(R.drawable.usable1);
                    } else if (data[count] == 2) {
                        tmps.setBackgroundResource(R.drawable.checking1);
                    } else {
                        tmps.setText("");
                    }*/
                if (data[count] == 0) {
                    imageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.unusable2));
                    imageView.setVisibility(View.INVISIBLE);
                   // tmps.setBackgroundResource(R.drawable.unusable1);
                } else {
                    //tmps.setBackgroundResource(R.drawable.usable1);
                    imageView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.usable2));

                }

                grid.addView(imageView);
                count++;


            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("Viewseat", "ArrayIndexOutOfBoundsException");
        }

       // rosScroll.addView(colScroll);
       // colScroll.addView(grid);
        pcmap.addView(grid);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        ZoomView zoomView = new ZoomView(mContext);
        zoomView.addView(inflateView);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(3f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("자리배치"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(40); // 미니 맵 내용 글씨 크기 설정
        //zoomView.zoomTo(2f, 500f, 300); //배율, 시작점 , 화면크기 측정후 입력받는 데이터 크기 비례해서 조절

        return zoomView;
    }


}
