package com.batch9.firebaseandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.batch9.firebaseandroid.adapter.BukuAdapter;
import com.batch9.firebaseandroid.entity.Buku;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeList extends AppCompatActivity {

    RecyclerView rvFirebase;
    Button btnTambahData;
    DatabaseReference databaseReference;

    final String URL = "https://fir-android-a8073-default-rtdb.asia-southeast1.firebasedatabase.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);
        btnTambahData = findViewById(R.id.btnTambahData);
        rvFirebase = findViewById(R.id.rvFirebase);

        databaseReference = FirebaseDatabase.getInstance(URL).getReference();

        databaseReference.child("buku").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Buku> daftarBuku = new ArrayList<>();
                for (DataSnapshot note : snapshot.getChildren()){
                    Buku buku = note.getValue(Buku.class);
                    buku.setId(note.getKey());
                    daftarBuku.add(buku);
                }
                BukuAdapter adapter = new BukuAdapter(daftarBuku,databaseReference,HomeList.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeList.this,LinearLayoutManager.VERTICAL,false);
                rvFirebase.setLayoutManager(layoutManager);
                rvFirebase.setAdapter(adapter);
                System.out.println(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnTambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeList.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}