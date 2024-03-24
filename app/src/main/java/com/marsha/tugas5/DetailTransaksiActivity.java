package com.marsha.tugas5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailTransaksiActivity extends AppCompatActivity {
    TextView tvJudulDetailTransaksi, tvInformasiTransaksi;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        tvJudulDetailTransaksi = findViewById(R.id.tvJudulDetailTransaksi);
        tvInformasiTransaksi = findViewById(R.id.tvInformasiTransaksi);
        btnShare = findViewById(R.id.btnShare);

        Intent intent = getIntent();
        if (intent != null) {
            String namaPelanggan = intent.getStringExtra("nama_pelanggan");
            String tipePelanggan = intent.getStringExtra("tipe_pelanggan");
            String kodeBarang = intent.getStringExtra("kode_barang");
            int jumlahBarang = intent.getIntExtra("jumlah_barang", 0);
            double totalHarga = intent.getDoubleExtra("total_harga", 0);
            double diskonHarga = intent.getDoubleExtra("diskon_harga", 0);
            double diskonMember = intent.getDoubleExtra("diskon_member", 0);
            double totalBayar = intent.getDoubleExtra("total_bayar", 0);

            // Format total bayar ke dalam format mata uang RP
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String formattedTotalHarga = formatter.format(totalHarga);
            String formattedDiskonHarga = formatter.format(diskonHarga);
            String formattedDiskonMember = formatter.format(diskonMember);
            String formattedTotalBayar = formatter.format(totalBayar);

            String informasiTransaksi = "TOKO HP Marsha Cahyani Dwisyakilla\nJalan Taman Karya\n\n"
                    + getText(R.string.tipe_pelanggan) + tipePelanggan +"\n"
                    + getText(R.string.text1)+ "\n"
                    + getText(R.string.text2)+ namaPelanggan + "\n"
                    + getText(R.string.text3) + jumlahBarang + "\n"
                    + getText(R.string.text4) + getNamaBarang(kodeBarang) + "\n"
                    + getText(R.string.text5)+ formattedTotalHarga + "\n"
                    + getText(R.string.text6) + formattedTotalHarga + "\n"
                    + getText(R.string.text7)+ formattedDiskonHarga + "\n"
                    + getText(R.string.text8)+ formattedDiskonMember + "\n"
                    +getText(R.string.text9)+formattedTotalBayar + "\n"
                    + getText(R.string.text10)+"\n"
                    + getText(R.string.text11);

            tvJudulDetailTransaksi.setText("Detail Transaksi");
            tvInformasiTransaksi.setText(informasiTransaksi);
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareStrukPembayaran();
            }
        });
    }

    private String getNamaBarang(String kodeBarang) {
        String namaBarang = "";

        switch (kodeBarang) {
            case "IPX":
                namaBarang = "Iphone X";
                break;
            case "MP3":
                namaBarang = "Macbook Pro M3";
                break;
            case "AV4":
                namaBarang = "Asus Vivobook 14";
                break;
        }

        return namaBarang;
    }

    private void shareStrukPembayaran() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String informasiTransaksi = tvInformasiTransaksi.getText().toString();
        intent.putExtra(Intent.EXTRA_TEXT, informasiTransaksi);

        startActivity(Intent.createChooser(intent, "Bagikan Struk Pembayaran Melalui"));
    }
}
