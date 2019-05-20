package com.sahabatpnj.crudfan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadAllActivity extends AppCompatActivity {
    //inisialisasi variabel
    private static final String TAG = "ReadAllActivity";
    private List<DataMahasiswa> dataMahasiswa;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all);

        recyclerView = findViewById(R.id.recyclerReadAllData); //findId recyclerView yg ada pada activity_read_all.xml

        recyclerView.setHasFixedSize(true); //agar recyclerView tergambar lebih cepat
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //menset layout manager sebagai LinearLayout(scroll kebawah)
        dataMahasiswa = new ArrayList<>(); //arraylist untuk menyimpan data mahasiswa
        AndroidNetworking.initialize(getApplicationContext()); //inisialisasi FAN
        getData(); // pemanggilan fungsi get data
    }

    public void getData(){
        //koneksi ke file read_all.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.get("http://192.168.100.13/crud_fan/read_all.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: " + response); //untuk log pada onresponse
                        // do anything with response
                        {
                            //mengambil data dari JSON array pada read_all.php
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject data = response.getJSONObject(i);
                                    //adding the product to product list
                                    dataMahasiswa.add(new DataMahasiswa(
                                            data.getInt("id_mahasiswa"), //"name:/String" diisi sesuai dengan yang di JSON pada read_all.php
                                            data.getString("nama_mahasiswa"), //"name:/String" diisi sesuai dengan yang di JSON pada read_all.php
                                            data.getString("nim_mahasiswa"), //"name:/String" diisi sesuai dengan yang di JSON pada read_all.php
                                            data.getString("kelas_mahasiswa") //"name:/String" diisi sesuai dengan yang di JSON pada read_all.php
                                    ));
                                }
                                //men inisialisasi adapter RecyclerView yang sudah kita buat sebelumnya
                                ListMahasiswaAdapter adapter = new ListMahasiswaAdapter(ReadAllActivity.this, dataMahasiswa);
                                recyclerView.setAdapter(adapter); //menset adapter yang akan digunakan pada recyclerView
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: " + error); //untuk log pada onerror
                        // handle error
                    }
                });
    }

}


