package com.melvin.seminario.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melvin.seminario.R;
import com.melvin.seminario.model.Denuncia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DenunciasAdapter extends RecyclerView.Adapter {

    private List<Denuncia> datos;
    private OnAdapterListener listener;

    public DenunciasAdapter(List<Denuncia> datos, OnAdapterListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_denuncia, parent, false);
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

        @BindView(R.id.textDenuncia)
        TextView denuncia;
        @BindView(R.id.textCompleto)
        TextView completo;
        @BindView(R.id.textDireccion)
        TextView direccion;
        @BindView(R.id.numeroDenuncia)
        TextView numeroDenuncia;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> listener.enDenunciaSeleccionada(datos.get(getAdapterPosition()).getId()));
        }

        void bind(Denuncia denuncia){
            this.denuncia.setText(denuncia.getAsegurado().getDetalle());
            direccion.setText(denuncia.getCalle() + " " + denuncia.getAltura());
            numeroDenuncia.setText(denuncia.getId().substring(denuncia.getId().length()-5).toUpperCase());
            if (denuncia.chechCompleto()){
                completo.setTextColor(itemView.getContext().getResources().getColor(R.color.verde));
                completo.setText("Denunciado");
            } else {
                completo.setText("Borrador");
                completo.setTextColor(itemView.getContext().getResources().getColor(R.color.amarillo));
            }
        }

        private boolean checkCompleto() {
            return true;
        }
    }

    interface  OnAdapterListener{
        void enDenunciaSeleccionada(String id);
    }

    public void setDatos(List<Denuncia> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }
}
