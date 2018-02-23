package com.handroid.mytownamp.pbangjariandroid.PcbangArray;

import java.io.Serializable;

/**
 * Created by Jeongmin on 2018-01-26.
 */

public class Pcbang_myloc_info implements Serializable {
    private String pcBangName;//
    private String pcBangTel;
    private String _id;
    private String roadAddress;
    private String detailAddress;
    private Double ratingScore;
    private Double Latitude;
    private Double Longitude;
    private Double myloc_Latitude;
    private Double myloc_Longitude;


    public Pcbang_myloc_info(String pcBangName, String pcBangTel, String roadAddress, String _id,String detailAddress, Double ratingScore,Double Latitude,Double Longitude,Double myloc_Latitude,Double myloc_Longitude) {
        this.pcBangName = pcBangName;
        this.pcBangTel = pcBangTel;

        this._id=_id;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.ratingScore=ratingScore;
        this.Latitude=Latitude;
        this.Longitude=Longitude;
        this.myloc_Latitude=myloc_Latitude;
        this.myloc_Longitude=myloc_Longitude;
    }

    public void setPcBangName(String pcBangName) {
        this.pcBangName = pcBangName;
    }

    public void setpcBangTel(String pcBangTel) {
        this.pcBangTel = pcBangTel;
    }


    public void setroadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void setdetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
    public void set_id(String _id){this._id=_id;}
    public void setLatitude(Double Latitude){this.Latitude=Latitude;}

    public void setLongitude(Double Longitude){this.Longitude=Longitude;}


    public void setMyloc_Latitude(Double myloc_Latitude){this.myloc_Latitude=myloc_Latitude;}

    public void setMyloc_Longitude(Double myloc_Longitude){this.myloc_Longitude=myloc_Longitude;}

    public Double getMyloc_Latitude(){return myloc_Latitude;}

    public Double getMyloc_Longitude(){return myloc_Longitude;}


    public String get_id(){return  _id;}
    public Double getLatitude(){return Latitude;}

    public Double getLongitude(){return Longitude;}

    public String getPcBangName() {
        return pcBangName;
    }

    public String getpcBangTel() {
        return pcBangTel;
    }



    public String getroadAddress() {
        return roadAddress;
    }

    public String getdetailAddress() {
        return detailAddress;
    }

    public Double getratingScore(){return ratingScore;}



}