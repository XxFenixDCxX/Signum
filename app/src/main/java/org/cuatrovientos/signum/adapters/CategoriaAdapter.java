package org.cuatrovientos.signum.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.models.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaHolder> {

    private List<Categoria> listCategoria;
    private OnItemClickListener listener;

    public CategoriaAdapter(List<Categoria> listCategoria , OnItemClickListener listener){
        this.listCategoria = listCategoria;
        this.listener = listener ;
    }

    @NonNull
    @Override
    public CategoriaAdapter.CategoriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item,parent, false);
        return new CategoriaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.CategoriaHolder holder, int position) {
        holder.assignData(listCategoria.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return listCategoria.size();
    }

    public class CategoriaHolder extends RecyclerView.ViewHolder {
        ImageView image ;

        TextView title ;
        public CategoriaHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewCategoria);
            title = itemView.findViewById(R.id.textViewCategoria);
        }

        public void assignData(Categoria categoria, OnItemClickListener listener) {
            title.setText(categoria.getNombre());
            image.setImageResource(categoria.getImagen());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(categoria,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Categoria categoria , int position);
    }
}
