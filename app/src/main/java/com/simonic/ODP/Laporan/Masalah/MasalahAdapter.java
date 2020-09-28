package com.simonic.ODP.Laporan.Masalah;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simonic.ODP.Laporan.Checkup.CheckupAdapter;
import com.simonic.ODP.Laporan.Checkup.Checkup_gs;
import com.simonic.ODP.R;

import java.util.ArrayList;

public class MasalahAdapter extends RecyclerView.Adapter<MasalahAdapter.ViewHolder> {
    public ArrayList<Masalah_gs> masalahlist = new ArrayList<>();
    Activity activity;

    public MasalahAdapter(Activity activity, ArrayList<Masalah_gs> masalahlist) {
        super();
        this.activity = activity;
        this.masalahlist = masalahlist;

    }
    @NonNull
    @Override
    public MasalahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.masalah_custom, parent, false);
        // create ViewHolder

        return new MasalahAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MasalahAdapter.ViewHolder holder, int position) {
        holder.masalah.setText(masalahlist.get(position).getMasalah());
        holder.tglm.setText(masalahlist.get(position).getTgl());
        holder.jamm.setText(masalahlist.get(position).getJam());
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return (masalahlist != null) ? masalahlist.size() : 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView masalah,tglm,jamm;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            masalah = itemLayoutView.findViewById(R.id.txt_masalah);
            tglm = itemLayoutView.findViewById(R.id.txt_tglmasalah);
            jamm = itemLayoutView.findViewById(R.id.txt_jammasalah);
        }
    }
}
