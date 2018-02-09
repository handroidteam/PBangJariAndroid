package com.handroid.mytownamp.pbangjariandroid.PcbangArray;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.handroid.mytownamp.pbangjariandroid.R;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by Jeongmin on 2018-02-09.
 */

public class Pcbang_subway_adapter extends BaseAdapter {
    ArrayList<String> arr;
    Context mContext;
    int layout;
    LayoutInflater inflater;

    public Pcbang_subway_adapter(Context mContext, int layout, ArrayList<String> arr) {
        this.mContext = mContext;
        this.layout = layout;
        this.arr = arr;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public String getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(layout, null);

        }
        final TextView image = (TextView) view.findViewById(R.id.subway_color);
        final TextView name = (TextView) view.findViewById(R.id.subway_name);

        name.setText(getItem(i));


        switch (getItem(i)) {
            case "1호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line1));
                break;
            case "2호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line2));
                break;
            case "3호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line3));
                break;
            case "4호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line4));
                break;
            case "5호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line5));
                break;
            case "6호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line6));
                break;
            case "7호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line7));
                break;
            case "8호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line8));
                break;
            case "9호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.line9));
                break;
            case "분당선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineBundang));
                break;
            case "경의중앙선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.Gyeonguijoungang));
                break;
            case "공항철도":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineAirportRailroad));
                break;
            case "인천메트로1호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineIncheon1));
                break;
            case "인천메트로2호선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineIncheon2));
                break;
            case "수인선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineSuin));
                break;
            case "신분당선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineSinbungdang));
                break;
            case "경춘선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineGyeongChun));
                break;
            case "경강선":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineGyeonggang));
                break;
            case "우이신설로":
                image.setBackgroundColor(mContext.getResources().getColor(R.color.LineWooeSinseulLrt));
                break;


        }


        return view;
    }

}
