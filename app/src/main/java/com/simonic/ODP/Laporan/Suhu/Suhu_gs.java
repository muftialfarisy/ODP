package com.simonic.ODP.Laporan.Suhu;

public class Suhu_gs {
    String suhu;

    public Suhu_gs(String suhu, String tgl){
        this.suhu = suhu;
        this.tgl = tgl;
    }
    public String getSuhu() {
        return suhu;
    }

    public void setSuhu(String suhu) {
        this.suhu = suhu;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    String tgl;
}
