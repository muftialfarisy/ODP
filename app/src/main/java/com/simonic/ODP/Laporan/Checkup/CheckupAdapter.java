package com.simonic.ODP.Laporan.Checkup;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.simonic.ODP.Laporan.Laporan_main;
import com.simonic.ODP.R;
import com.simonic.ODP.Register_gs;

import java.util.ArrayList;

public class CheckupAdapter extends RecyclerView.Adapter<CheckupAdapter.ViewHolder> {

    public ArrayList<Checkup_gs> reportlist = new ArrayList<>();
    Activity activity;
    public CheckupAdapter(Activity activity, ArrayList<Checkup_gs> reportlist) {
        super();
        this.activity = activity;
        this.reportlist = reportlist;

    }
    @NonNull
    @Override
    public CheckupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_checkup, parent, false);
        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return new CheckupAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckupAdapter.ViewHolder holder, final int position) {

        holder.namars.setText(reportlist.get(position).getRs());
        Checkup_gs movie = reportlist.get(position);
        holder.tglreportc.setText(reportlist.get(position).getTanggal());
        holder.hae.setText(reportlist.get(position).getHaemoglobin());
        holder.leu.setText(reportlist.get(position).getLeukosit());
        holder.trom.setText(reportlist.get(position).getTrombosit());
        holder.elek.setText(reportlist.get(position).getElektrolit());
        holder.puasa.setText(reportlist.get(position).getKadar_puasa());
        holder.setelahp.setText(reportlist.get(position).getKadar_setelah_puasa());
        holder.kol.setText(reportlist.get(position).getKolesterol());
        holder.asam.setText(reportlist.get(position).getAsam_urat());
        holder.hati.setText(reportlist.get(position).getFungsi_hati());
        holder.ginjal.setText(reportlist.get(position).getFungsi_ginjal());
        boolean isExpanded = reportlist.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.cdreportc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Checkup_gs movie = reportlist.get(position);

                movie.setExpanded(!movie.isExpanded());
                holder.btncolapse.setRotation(270f);
                notifyItemChanged(position);

            }
        });
        if(movie.isExpanded()){
            holder.btncolapse.setRotation(270f);
        }else {
            holder.btncolapse.setRotation(90f);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return (reportlist != null) ? reportlist.size() : 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView namars,tglreportc,hae,leu,trom,elek,puasa,setelahp,kol,asam,hati,ginjal;
        RelativeLayout expandableLayout;
        public ImageView btncolapse;
        public CardView cdreportc;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            namars = (TextView) itemLayoutView.findViewById(R.id.nama_rs);
            tglreportc = (TextView) itemLayoutView.findViewById(R.id.tgl_reportc);
            hae = (TextView) itemLayoutView.findViewById(R.id.txt_haemo);
            leu = (TextView) itemLayoutView.findViewById(R.id.txt_leukosit);
            trom = (TextView) itemLayoutView.findViewById(R.id.txt_trombosit);
            elek = (TextView) itemLayoutView.findViewById(R.id.txt_elektrolit);
            puasa = (TextView) itemLayoutView.findViewById(R.id.txt_gula_puasa);
            setelahp = (TextView) itemLayoutView.findViewById(R.id.txt_gula_setelah_puasa);
            kol = (TextView) itemLayoutView.findViewById(R.id.txt_kolesterol);
            asam = (TextView) itemLayoutView.findViewById(R.id.txt_asam);
            hati = (TextView) itemLayoutView.findViewById(R.id.txt_hati);
            ginjal = (TextView) itemLayoutView.findViewById(R.id.txt_ginjal);
            expandableLayout = itemLayoutView.findViewById(R.id.rl_expand);
            btncolapse = itemLayoutView.findViewById(R.id.btn_collapse);
            cdreportc = itemLayoutView.findViewById(R.id.cd_reportc);

        }
    }
}
