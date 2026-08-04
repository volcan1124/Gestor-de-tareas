package com.example.gestordetareasto_dolist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

    public interface OnRolClickListener{
        void onCambiarRol(Usuario usuario);
    }

    private ArrayList<Usuario> listaUsuarios;
    private OnRolClickListener listener;

    public UsuariosAdapter(ArrayList<Usuario> listaUsuarios,
                           OnRolClickListener listener){

        this.listaUsuarios = listaUsuarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        Usuario usuario = listaUsuarios.get(position);

        holder.tvNombre.setText(usuario.getNombre());
        holder.tvCorreo.setText(usuario.getCorreo());

        holder.tvRol.setText(usuario.getRol());

        if(usuario.getRol().equals("administrador")){

            holder.tvRol.setTextColor(Color.RED);
            holder.btnCambiarRol.setText("Cambiar a Empleado");

        }else{

            holder.tvRol.setTextColor(Color.parseColor("#2E7D32"));
            holder.btnCambiarRol.setText("Hacer Administrador");

        }

        holder.btnCambiarRol.setOnClickListener(v -> {

            if(listener!=null){
                listener.onCambiarRol(usuario);
            }

        });

    }

    @Override
    public int getItemCount(){
        return listaUsuarios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombre;
        TextView tvCorreo;
        TextView tvRol;
        Button btnCambiarRol;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            tvNombre=itemView.findViewById(R.id.tvNombre);
            tvCorreo=itemView.findViewById(R.id.tvCorreo);
            tvRol=itemView.findViewById(R.id.tvRol);
            btnCambiarRol=itemView.findViewById(R.id.btnCambiarRol);

        }

    }

}