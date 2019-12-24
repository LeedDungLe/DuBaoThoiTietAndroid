package com.example.dubaothoitiet;

public class Ngay {
    public String ngay;
    public String trangthai;
    public String nhietdoMin;
    public String nhietdoMax;
    public String icon;

    public Ngay() {
    }

    public Ngay(String ngay, String trangthai, String nhietdoMin, String nhietdoMax, String icon) {
        this.ngay = ngay;
        this.trangthai = trangthai;
        this.nhietdoMin = nhietdoMin;
        this.nhietdoMax = nhietdoMax;
        this.icon = icon;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getNhietdoMin() {
        return nhietdoMin;
    }

    public void setNhietdoMin(String nhietdoMin) {
        this.nhietdoMin = nhietdoMin;
    }

    public String getNhietdoMax() {
        return nhietdoMax;
    }

    public void setNhietdoMax(String nhietdoMax) {
        this.nhietdoMax = nhietdoMax;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
