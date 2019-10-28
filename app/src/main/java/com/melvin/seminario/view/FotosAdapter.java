package com.melvin.seminario.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.melvin.seminario.R;
import com.melvin.seminario.model.Foto;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FotosAdapter extends RecyclerView.Adapter {

    private List<Foto> datos;

    public FotosAdapter(List<Foto> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_foto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textViewFoto)
        TextView nombre;
        @BindView(R.id.imageViewFoto)
        ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind (Foto foto){
            nombre.setText(foto.getNombre());
            File file = new File(foto.getFilepath());

            Glide.with(itemView)
                    .load(file)
                    .into(this.foto);
        }
    }
}
