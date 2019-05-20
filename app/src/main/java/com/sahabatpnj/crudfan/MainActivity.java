package com.sahabatpnj.crudfan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; //untuk melihat log
    private EditText etNim, etNama, etKelas; //pembuatan variabel edittext
    private Button btnTambah, btnLihat; //pembuatan variabel button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: inisialisasi"); //untuk log pada oncreate

        etNim = findViewById(R.id.editTextMainNim); //inisialisasi value etNim
        etNama = findViewById(R.id.editTextMainNama); //inisialisasi value etNama
        etKelas = findViewById(R.id.editTextMainKelas); //inisialisasi value etKelas
        btnTambah = findViewById(R.id.buttonMainTambah); //inisialisasi value btnTambah
        btnLihat = findViewById(R.id.buttonMainLihat); //inisialisasi value btnLihat

        AndroidNetworking.initialize(getApplicationContext()); //inisialisasi library FAN
        aksiButton();//memanggil fungsi aksiButton()
    }
    public void aksiButton(){
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = etNim.getText().toString(); //mengambil Value etNim menjadi string
                String nama = etNama.getText().toString(); //mengambil Value etNim menjadi string
                String kelas = etKelas.getText().toString(); //mengambil Value etNim menjadi string
                if (nim.equals("")||nama.equals("")||kelas.equals("")){
                    Toast.makeText(getApplicationContext(),"Semua data harus diisi" , Toast.LENGTH_SHORT).show();
                    //memunculkan toast saat form masih ada yang kosong
                } else {
                    tambahData(nim,nama,kelas); //memanggil fungsi tambahData()
                    etNim.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    etNama.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                    etKelas.setText(""); //mengosongkan form setelah data berhasil ditambahkan
                }
            }
        });
        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //untuk pindah keActivity lain saat buttonlihat dipencet
                Intent intent = new Intent(MainActivity.this, ReadAllActivity.class);
                startActivity(intent);
            }
        });
    }


    public void tambahData(String nim, String nama, String kelas){
        AndroidNetworking.post("http://192.168.100.13/crud_fan/create.php") //koneksi ke file create.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
                .addBodyParameter("id_mahasiswa", "") //id bersifat Auto_Increment tidak perlu diisi/(diisi NULL) cek create.php
                .addBodyParameter("nim_mahasiswa",nim) //mengirimkan data nim_mahasiswa yang akan diisi dengan varibel nim
                .addBodyParameter("nama_mahasiswa", nama) //mengirimkan data nama_mahasiswa yang akan diisi dengan varibel nama
                .addBodyParameter("kelas_mahasiswa", kelas) //mengirimkan data kelas_mahasiswa yang akan diisi dengan varibel kelas
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Handle Response
                        Log.d(TAG, "onResponse: " + response); //untuk log pada onresponse
                        Toast.makeText(getApplicationContext(),"Data berhasil ditambahkan" , Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data berhasil ditambahkan

                    }
                    @Override
                    public void onError(ANError error) {
                        //Handle Error
                        Log.d(TAG, "onError: Failed" + error); //untuk log pada onerror
                        Toast.makeText(getApplicationContext(),"Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        //memunculkan Toast saat data gagal ditambahkan
                    }
                });
    }
}
