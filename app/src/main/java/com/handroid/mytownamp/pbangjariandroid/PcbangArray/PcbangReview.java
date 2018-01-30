package com.handroid.mytownamp.pbangjariandroid.PcbangArray;

public class PcbangReview  {

    private int postCode;
    private String username;
    private String content;
    public float starnum;



    public PcbangReview(int postCode,String username,String content,float starnum) {
        this.postCode = postCode;
        this.username=username;
        this.content=content;
        this.starnum=starnum;
    }

    public float getStarnum(){return starnum;}
}