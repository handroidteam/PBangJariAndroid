package com.handroid.mytownamp.pbangjariandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import pl.polidea.view.ZoomView;

/**
 * Created by KimJeongMin on 2017-12-13.
 */

public class ViewSeat {
    private int Colums; //세로
    private int Rows;//가로
    private Context mContext;//액티비티 정보
    private int[] data;

    public ZoomView ViewSeat(Context mContext, int colums, int rows, int[] data) {
        Colums = colums;
        this.mContext = mContext;
        Rows = rows;
        this.data = data;

        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ZoomView zoomView = new ZoomView(mContext);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("자리배치"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정

        return zoomView;
    }


}
