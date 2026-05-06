package com.example.gestordetareasto_dolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class TareaAdapater extends RecyclerView.Adapter<TareaAdapater.TareaViewHolder> {

    private List<Tarea> listaTarea;

    public TareaAdapater(List<Tarea> listaTarea){
        this.listaTarea = listaTarea;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int posicion){
        Tarea TareaActual = listaTarea.get(posicion);

        holder.tvId.setText(String.valueOf(TareaActual.getId()));
        holder.tvTarea.setText(TareaActual.getTarea());
        holder.tvFecha.setText("$ " + TareaActual.getFecha());
    }

    @Override
    public int getItemCount(){
        return listaTarea.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTarea, tvFecha;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvItemId);
            tvTarea = itemView.findViewById(R.id.tvItemTarea);
            tvFecha = itemView.findViewById(R.id.tvItemFecha);
        }
    }
}
