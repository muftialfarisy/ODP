package com.simonic.ODP.Laporan.Masalah;

public class Masalah_gs {
public Masalah_gs(){

}
    public Masalah_gs(String masalah, String tgl, String jam){
        this.masalah = masalah;
        this.tgl = tgl;
        this.jam = jam;
    }
    public String getMasalah() {
        return masalah;
    }

    public void setMasalah(String masalah) {
        this.masalah = masalah;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    String masalah;
    String tgl;
    String jam;
}
