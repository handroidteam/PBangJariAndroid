package com.handroid.mytownamp.pbangjariandroid;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.polidea.view.ZoomView;

/**
 * Created by KimJeongMin on 2017-12-13.
 */

public class ViewSeat {
    public ZoomView SettingPcMap(Context mContext, int colums, int rows, int[] data,LinearLayout container,View inflateView,LinearLayout pcmap) {
        GridLayout grid = new GridLayout(mContext); //새로생성
        GridLayout.LayoutParams layoutParam = new GridLayout.LayoutParams(container.getLayoutParams()); //view그륩의 layoutparams 가져와서 넣기
        layoutParam.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParam.height = GridLayout.LayoutParams.WRAP_CONTENT;
        grid.setLayoutParams(layoutParam);
        grid.setColumnCount(colums); //가로세로 설정
        grid.setRowCount(rows);

                int count = 0;
        try {
            for (int c = 0; c < colums; c++) {
                for (int d = 0; d < rows; d++) {
                    TextView tmps = new TextView(mContext);
                    tmps.setHeight(100);
                    tmps.setWidth(100);
                    if (data[count] == 0) {
                        tmps.setBackgroundResource(R.drawable.unusable1);
                    } else if (data[count] == 1) {
                        tmps.setBackgroundResource(R.drawable.usable1);
                    } else if (data[count] == 2) {
                        tmps.setBackgroundResource(R.drawable.checking1);
                    } else {
                        tmps.setText("");
                    }
                    grid.addView(tmps);
                    count++;
                }


            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("asd", "asdasd");
        }
        pcmap.addView(grid);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        ZoomView zoomView = new ZoomView(mContext);
        zoomView.addView(inflateView);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("자리배치"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.zoomTo(2f, 500f, 300); //배율, 시작점 , 화면크기 측정후 입력받는 데이터 크기 비례해서 조절

        return zoomView;
    }


}
