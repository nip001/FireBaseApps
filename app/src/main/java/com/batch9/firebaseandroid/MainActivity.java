package com.batch9.firebaseandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.batch9.firebaseandroid.entity.Buku;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText editNamaBuku,editAuthor,editJumlahHalaman;
    DatabaseReference database;
    Buku buku;

    final String URL = "https://fir-android-a8073-default-rtdb.asia-southeast1.firebasedatabase.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editAuthor = findViewById(R.id.editAuthor);
        editJumlahHalaman = findViewById(R.id.editHalaman);
        editNamaBuku = findViewById(R.id.editNamaBuku);
        btnSubmit = findViewById(R.id.btnSubmit);
        database = FirebaseDatabase.getInstance(URL).getReference();

        if(getIntent().getParcelableExtra("databuku") != null){
            buku=getIntent().getParcelableExtra("databuku");
            editNamaBuku.setText(buku.getNamaBuku());
            editAuthor.setText(buku.getAuthor());
            editJumlahHalaman.setText(String.valueOf(buku.getJumlahHalaman()));
        }else{
            buku = new Buku();
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buku.setNamaBuku(editNamaBuku.getText().toString());
                buku.setAuthor(editAuthor.getText().toString());
                buku.setJumlahHalaman(Integer.parseInt(editJumlahHalaman.getText().toString()));
                if(buku.getId()==null){
                    database.child("buku").push().setValue(buku).addOnSuccessListener(
                            MainActivity.this,
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editAuthor.setText("");
                                    editJumlahHalaman.setText("");
                                    editNamaBuku.setText("");
                                    Toast.makeText(MainActivity.this, "Data Telah Ditambahkan", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }else{
                    database.child("buku").child(buku.getId()).setValue(buku).addOnSuccessListener(
                            MainActivity.this,
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    editAuthor.setText("");
                                    editJumlahHalaman.setText("");
                                    editNamaBuku.setText("");
                                    Toast.makeText(MainActivity.this, "Data Telah Ditambahkan", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }


            }
        });
    }
}