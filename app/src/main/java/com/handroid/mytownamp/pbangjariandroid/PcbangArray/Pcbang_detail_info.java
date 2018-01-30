package com.handroid.mytownamp.pbangjariandroid.PcbangArray;

/**
 * Created by Jeongmin on 2018-01-26.
 */

public class Pcbang_detail_info {
    private String pcBangName;//
    private String pcBangTel;
    private String postCode;
    private String roadAddress;
    private String detailAddress;
    private Double ratingScore;
    private Double Latitude;
    private Double Longitude;
    private String CPU;
    private String RAM;
    private String VGA;



    public Pcbang_detail_info(String pcBangName,
                              String pcBangTel,
                              String postCode,
                              String roadAddress,
                              String _id,
                              String detailAddress,
                              Double ratingScore,
                              Double Latitude,
                              Double Longitude,
                              String CPU,
                              String RAM,
                              String VGA) {
        this.pcBangName = pcBangName;
        this.pcBangTel = pcBangTel;
        this.postCode = postCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.ratingScore = ratingScore;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.CPU=CPU;
        this.RAM=RAM;
        this.VGA=VGA;
    }


    public Double getLatitude() {
        return Latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public String getPcBangName() {
        return pcBangName;
    }

    public String getpcBangTel() {
        return pcBangTel;
    }

    public String getpostCode() {
        return postCode;
    }

    public String getroadAddress() {
        return roadAddress;
    }

    public String getdetailAddress() {
        return detailAddress;
    }

    public Double getratingScore() {
        return ratingScore;
    }

    public String getCPU(){return CPU;}
    public String getRAM(){return RAM;}

    public String getVGA(){return VGA;}




}