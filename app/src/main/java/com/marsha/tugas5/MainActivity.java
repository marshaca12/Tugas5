package com.marsha.tugas5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText etNamaPelanggan, etKodeBarang, etJumlahBarang;
    RadioButton rbGold, rbSilver, rbBiasa;
    Button btnProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNamaPelanggan = findViewById(R.id.etNamaPelanggan);
        etKodeBarang = findViewById(R.id.etKodeBarang);
        etJumlahBarang = findViewById(R.id.etJumlahBarang);
        rbGold = findViewById(R.id.rbGold);
        rbSilver = findViewById(R.id.rbSilver);
        rbBiasa = findViewById(R.id.rbBiasa);
        btnProses = findViewById(R.id.btnProses);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputValid()) {
                    String namaPelanggan = etNamaPelanggan.getText().toString().trim();
                    String kodeBarang = etKodeBarang.getText().toString().trim();
                    int jumlahBarang = Integer.parseInt(etJumlahBarang.getText().toString().trim());
                    String tipePelanggan = "";

                    if (rbGold.isChecked()) {
                        tipePelanggan = "Gold";
                    } else if (rbSilver.isChecked()) {
                        tipePelanggan = "Silver";
                    } else if (rbBiasa.isChecked()) {
                        tipePelanggan = "Biasa";
                    }

                    double totalHarga = hitungTotalHarga(kodeBarang, jumlahBarang);
                    double diskonHarga = hitungDiskonHarga(totalHarga);
                    double diskonMember = hitungDiskonMember(totalHarga, tipePelanggan);
                    double totalBayar = hitungTotalBayar(totalHarga, diskonHarga, diskonMember);

                    // Format totalBayar ke dalam format mata uang RP


                    Intent intent = new Intent(MainActivity.this, DetailTransaksiActivity.class);
                    intent.putExtra("nama_pelanggan", namaPelanggan);
                    intent.putExtra("tipe_pelanggan", tipePelanggan);
                    intent.putExtra("kode_barang", kodeBarang);
                    intent.putExtra("jumlah_barang", jumlahBarang);
                    intent.putExtra("total_harga", totalHarga);
                    intent.putExtra("diskon_harga", diskonHarga);
                    intent.putExtra("diskon_member", diskonMember);
                    intent.putExtra("total_bayar", totalBayar); // Menggunakan total yang sudah diformat
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isInputValid() {
        if (etNamaPelanggan.getText().toString().isEmpty()) {
            etNamaPelanggan.setError("Nama pelanggan harus diisi");
            return false;
        }

        if (etKodeBarang.getText().toString().isEmpty()) {
            etKodeBarang.setError("Kode barang harus diisi");
            return false;
        }

        if (etJumlahBarang.getText().toString().isEmpty()) {
            etJumlahBarang.setError("Jumlah barang harus diisi");
            return false;
        }

        return true;
    }

    private double hitungTotalHarga(String kodeBarang, int jumlahBarang) {
        double hargaBarang = 0;

        if (kodeBarang.equals("IPX")) {
            hargaBarang = 5725300;
        } else if (kodeBarang.equals("MP3")) {
            hargaBarang = 28999999;
        } else if (kodeBarang.equals("AV4")) {
            hargaBarang = 9150999;
        }

        return hargaBarang * jumlahBarang;
    }

    private double hitungDiskonHarga(double totalHarga) {
        if (totalHarga > 10000000) {
            return 100000;
        }
        return 0;
    }

    private double hitungDiskonMember(double totalHarga, String tipePelanggan) {
        double diskonMember = 0;

        if (tipePelanggan.equals("Gold")) {
            diskonMember = totalHarga * 0.1;
        } else if (tipePelanggan.equals("Silver")) {
            diskonMember = totalHarga * 0.05;
        } else if (tipePelanggan.equals("Biasa")) {
            diskonMember = totalHarga * 0.02;
        }

        return diskonMember;
    }

    private double hitungTotalBayar(double totalBayar, double diskonHarga, double diskonMember) {
        return totalBayar - diskonHarga - diskonMember;
    }
}