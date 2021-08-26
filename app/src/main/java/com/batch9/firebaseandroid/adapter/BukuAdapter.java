package com.batch9.firebaseandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.batch9.firebaseandroid.MainActivity;
import com.batch9.firebaseandroid.R;
import com.batch9.firebaseandroid.entity.Buku;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuViewHolder> {

    private List<Buku> buku;
    private Context context;
    private DatabaseReference databaseReference;

    public BukuAdapter(List<Buku> buku,DatabaseReference databaseReference, Context context) {
        this.buku = buku;
        this.context = context;
        this.databaseReference=databaseReference;
    }

    @NonNull
    @Override
    public BukuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_firebase,parent,false);
        return new BukuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuViewHolder holder, int position) {
        holder.txtJudul.setText(buku.get(position).getNamaBuku());
        holder.txtAuthorBuku.setText(buku.get(position).getAuthor());
        holder.txtJmlHalaman.setText(String.valueOf(buku.get(position).getJumlahHalaman()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelete(buku.get(position).getId());
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(buku.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return buku.size();
    }

    private void updateData(Buku databuku){
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("databuku",databuku);
        context.startActivity(i);
    }

    private void onDelete(String key){
        databaseReference.child("buku").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Berhasil di hapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class BukuViewHolder extends RecyclerView.ViewHolder{
        TextView txtJudul,txtAuthorBuku,txtJmlHalaman;

        Button btnDelete,btnUpdate;

        public BukuViewHolder(@NonNull View v){
            super(v);
            txtAuthorBuku=v.findViewById(R.id.txtAuthorBuku);
            txtJmlHalaman=v.findViewById(R.id.txtJmlHalaman);
            txtJudul=v.findViewById(R.id.txtJudul);
            btnDelete = v.findViewById(R.id.btnDelete);
            btnUpdate = v.findViewById(R.id.btnUpdate);
        }
    }
}
