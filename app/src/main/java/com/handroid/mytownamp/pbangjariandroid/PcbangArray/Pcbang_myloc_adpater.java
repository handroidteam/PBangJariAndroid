package com.handroid.mytownamp.pbangjariandroid.PcbangArray;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.handroid.mytownamp.pbangjariandroid.R;

import java.util.ArrayList;

/**
 * Created by Jeongmin on 2018-01-26.
 */

public class Pcbang_myloc_adpater extends BaseAdapter {
    private ArrayList<Pcbang_myloc_info> arr = new ArrayList<>();
    private Context mContext;
    private int layout;
    private LayoutInflater inflater;


    public Pcbang_myloc_adpater(Context mContext, int layout, ArrayList<Pcbang_myloc_info> arr) {
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
    public Pcbang_myloc_info getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, null);
        }
        final TextView pcbangName = (TextView) view.findViewById(R.id.Pcbang_image);
        final TextView pcbanginfo = (TextView) view.findViewById(R.id.pcbang_info);
        final RatingBar star = (RatingBar) view.findViewById(R.id.pcbang_rating);


        final Double ratingScore = getItem(position).getratingScore();
        star.setNumStars(5);
        star.setStepSize(0.5f);
        star.setRating(ratingScore.floatValue());


        Double x = getItem(position).getMyloc_Latitude();
        Double y = getItem(position).getMyloc_Longitude();
        Double a = getItem(position).getLatitude();
        Double b = getItem(position).getLongitude();

        Double distance = Math.sqrt(Math.pow(Math.abs(x - a) * 114000, 2) + Math.pow(Math.abs(y - b) * 88000, 2));

        Log.d("fav_distance", "distance" + distance);

        pcbangName.setText(getItem(position).getPcBangName());
        pcbanginfo.setText("\n주소 : " + getItem(position).getroadAddress() + "\n" + "거리 " + (int) Math.floor(distance) + "M");

        return view;
    }
}
