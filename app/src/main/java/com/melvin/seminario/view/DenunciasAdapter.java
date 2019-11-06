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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> listener.enDenunciaSeleccionada(datos.get(getAdapterPosition()).getId()));
        }

        void bind(Denuncia denuncia){
            this.denuncia.setText("Denuncia del dia " + denuncia.getFecha());
            if (denuncia.chechCompleto()){
                completo.setTextColor(itemView.getContext().getResources().getColor(R.color.verde));
                completo.setText("COMPLETO");
            } else {
                completo.setText("INCOMPLETO");
                completo.setTextColor(itemView.getContext().getResources().getColor(R.color.rojo));
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
