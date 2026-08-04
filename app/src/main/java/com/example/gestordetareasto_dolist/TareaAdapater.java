package com.example.gestordetareasto_dolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;

import java.util.ArrayList;

public class TareaAdapater extends RecyclerView.Adapter<TareaAdapater.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Tarea tarea);
    }

    private ArrayList<Tarea> listaTareas;
    private OnItemClickListener listener;

    public TareaAdapater(ArrayList<Tarea> listaTareas,
                         OnItemClickListener listener){

        this.listaTareas = listaTareas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        Tarea tarea = listaTareas.get(position);

        holder.tvTitulo.setText(tarea.getTarea());
        holder.tvDescripcion.setText(tarea.getFecha());

        if (tarea.isCompletada()) {

            holder.tvEstado.setText("🟢 Completada");
            holder.tvEstado.setTextColor(Color.parseColor("#2E7D32"));

            holder.tvTitulo.setAlpha(0.6f);
            holder.tvDescripcion.setAlpha(0.6f);

        } else {

            holder.tvEstado.setText("🟡 Pendiente");
            holder.tvEstado.setTextColor(Color.parseColor("#F9A825"));

            holder.tvTitulo.setAlpha(1f);
            holder.tvDescripcion.setAlpha(1f);

        }

        holder.itemView.setOnClickListener(v -> {

            if(listener!=null){
                listener.onItemClick(tarea);
            }

        });

        String prioridad = tarea.getPrioridad();

        if (prioridad == null || prioridad.isEmpty()) {
            prioridad = "🟡 Media";
        }

        holder.tvPrioridad.setText(prioridad);

        switch (prioridad) {

            case "🔴 Alta":
                holder.tvPrioridad.setTextColor(Color.RED);
                break;

            case "🟡 Media":
                holder.tvPrioridad.setTextColor(Color.parseColor("#F9A825"));
                break;

            case "🟢 Baja":
                holder.tvPrioridad.setTextColor(Color.parseColor("#2E7D32"));
                break;

            default:
                holder.tvPrioridad.setTextColor(Color.GRAY);
                break;
        }
    }

    @Override
    public int getItemCount(){
        return listaTareas.size();
    }

    public void actualizarLista(ArrayList<Tarea> nuevaLista){

        listaTareas.clear();
        listaTareas.addAll(nuevaLista);
        notifyDataSetChanged();

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitulo;
        TextView tvDescripcion;
        TextView tvEstado;

        TextView tvPrioridad;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            tvTitulo=itemView.findViewById(R.id.tvTitulo);
            tvDescripcion=itemView.findViewById(R.id.tvDescripcion);
            tvEstado=itemView.findViewById(R.id.tvEstado);
            tvPrioridad = itemView.findViewById(R.id.tvPrioridad);
        }

    }

}