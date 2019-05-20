package com.sahabatpnj.crudfan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ListMahasiswaAdapter extends RecyclerView.Adapter<ListMahasiswaAdapter.ViewHolder> {

    private Context mContext;
    private List<DataMahasiswa> dataMahasiswa; //inisialisasi List dengan object DataMahasiswa
    private DataMahasiswa data;


    //construktor ListMahasiswaAdapter
    public ListMahasiswaAdapter(Context mContext,List<DataMahasiswa> dataMahasiswa) {
        this.mContext = mContext;
        this.dataMahasiswa = dataMahasiswa;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view yang akan digunakan yaitu layout list_mahasiswa_row.xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mahasiswa_row, parent, false);
        ViewHolder holder = new ViewHolder(v); //inisialisasi ViewHolder
        return holder;
    } //fungsi yang dijalankan saat ViewHolder dibuat

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        data = dataMahasiswa.get(position); //inisialisasi object DataMahasiwa
        holder.mNama.setText(data.getNamaMahasiswa()); //menset value view "mNama" sesuai data dari getNamaMahasiswa();
        holder.mNim.setText(data.getNimMahasiswa()); //menset value view "mNim" sesuai data dari getNimMahasiswa();

//        holder.mLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ReadOneActivity.class);
//                intent.putExtra("nim_mhs", data.getNimMahasiswa());
//                mContext.startActivity(intent);
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return dataMahasiswa.size(); //mengambil item sesuai urutan
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mNama, mNim; //inisialisasi variabel
        RelativeLayout mLayout; //inisialisasi parent layout

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.mParentList);
            mNama = itemView.findViewById(R.id.textListMahasiswaNama); //find layout sesuai dengan yg di list_mahasiswa_row.xml
            mNim = itemView.findViewById(R.id .textListMahasiswaNim); //find layout sesuai dengan yg di list_mahasiswa_row.xml
        }
    }
}
