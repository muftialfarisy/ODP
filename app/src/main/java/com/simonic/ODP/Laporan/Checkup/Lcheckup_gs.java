package com.simonic.ODP.Laporan.Checkup;

import android.os.Parcel;
import android.os.Parcelable;

public class Lcheckup_gs implements Parcelable {
    String rs;
    String tanggal;
    String haemoglobin;
    String leukosit;
    String trombosit;
    String elektrolit;
    String kadar_puasa;
    private boolean expanded;

    public Lcheckup_gs(String rs,String tanggal,String haemoglobin, String leukosit,
                       String trombosit, String elektrolit, String kadar_puasa, String kadar_setelah_puasa, String kolesterol,
                       String asam_urat, String fungsi_hati, String fungsi_ginjal){
        this.rs = rs;
        this.tanggal = tanggal;
        this.haemoglobin = haemoglobin;
        this.leukosit = leukosit;
        this.trombosit = trombosit;
        this.elektrolit = elektrolit;
        this.kadar_puasa = kadar_puasa;
        this.kadar_setelah_puasa = kadar_setelah_puasa;
        this.kolesterol = kolesterol;
        this.asam_urat = asam_urat;
        this.fungsi_hati = fungsi_hati;
        this.fungsi_ginjal = fungsi_ginjal;
        this.expanded = false;
    }
    protected Lcheckup_gs(Parcel in) {
        rs = in.readString();
        tanggal = in.readString();
    }
    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getHaemoglobin() {
        return haemoglobin;
    }

    public void setHaemoglobin(String haemoglobin) {
        this.haemoglobin = haemoglobin;
    }

    public String getLeukosit() {
        return leukosit;
    }

    public void setLeukosit(String leukosit) {
        this.leukosit = leukosit;
    }

    public String getTrombosit() {
        return trombosit;
    }

    public void setTrombosit(String trombosit) {
        this.trombosit = trombosit;
    }

    public String getElektrolit() {
        return elektrolit;
    }

    public void setElektrolit(String elektrolit) {
        this.elektrolit = elektrolit;
    }

    public String getKadar_puasa() {
        return kadar_puasa;
    }

    public void setKadar_puasa(String kadar_puasa) {
        this.kadar_puasa = kadar_puasa;
    }

    public String getKadar_setelah_puasa() {
        return kadar_setelah_puasa;
    }

    public void setKadar_setelah_puasa(String kadar_setelah_puasa) {
        this.kadar_setelah_puasa = kadar_setelah_puasa;
    }

    public String getKolesterol() {
        return kolesterol;
    }

    public void setKolesterol(String kolesterol) {
        this.kolesterol = kolesterol;
    }
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    public String getAsam_urat() {
        return asam_urat;
    }

    public void setAsam_urat(String asam_urat) {
        this.asam_urat = asam_urat;
    }

    public String getFungsi_hati() {
        return fungsi_hati;
    }

    public void setFungsi_hati(String fungsi_hati) {
        this.fungsi_hati = fungsi_hati;
    }

    public String getFungsi_ginjal() {
        return fungsi_ginjal;
    }

    public void setFungsi_ginjal(String fungsi_ginjal) {
        this.fungsi_ginjal = fungsi_ginjal;
    }

    String kadar_setelah_puasa;
    String kolesterol;
    String asam_urat;
    String fungsi_hati;
    String fungsi_ginjal;

    public static final Creator<Lcheckup_gs> CREATOR = new Creator<Lcheckup_gs>() {
        @Override
        public Lcheckup_gs createFromParcel(Parcel in) {
            return new Lcheckup_gs(in);
        }

        @Override
        public Lcheckup_gs[] newArray(int size) {
            return new Lcheckup_gs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(rs);
        parcel.writeString(tanggal);
    }
}
