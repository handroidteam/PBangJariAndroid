package com.handroid.mytownamp.pbangjariandroid.PcbangArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.handroid.mytownamp.pbangjariandroid.R;

import java.util.ArrayList;


public class PcbangReview_Adapter extends BaseAdapter {
    private ArrayList<PcbangReview> arr = new ArrayList<>();
    private Context mContext;
    private int layout;
    private LayoutInflater inflater;


    public PcbangReview_Adapter(Context mContext, int layout, ArrayList<PcbangReview> arr) {
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
    public PcbangReview getItem(int i) {
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



        final float starnum = getItem(position).getStarnum();
        star.setNumStars(5);
        star.setStepSize(0.5f);
        star.setRating(starnum);

        
        return view;
    }
}
