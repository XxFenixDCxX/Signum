package org.cuatrovientos.signum.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.models.Categoria;

import java.util.List;

public class PracticarAdapter extends RecyclerView.Adapter<PracticarAdapter.PracticarHolder> {

    private List<Categoria> listCategoria;
    private OnItemClickListener listener;


    public PracticarAdapter(List<Categoria> listCategoria , OnItemClickListener listener){
        this.listCategoria = listCategoria;
        this.listener = listener ;
    }
    @NonNull
    @Override
    public PracticarAdapter.PracticarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item,parent, false);
        return new PracticarAdapter.PracticarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticarAdapter.PracticarHolder holder, int position) {
        holder.assignData(listCategoria.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return listCategoria.size();
    }

    public class PracticarHolder extends RecyclerView.ViewHolder {

        CircularProgressBar progressBar ;
        ImageView image  ;
        TextView title ;
        public PracticarHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.circularProgressBar);
            image = itemView.findViewById(R.id.imageViewPracticar);
            title = itemView.findViewById(R.id.textViewPracticar);
        }

        public void assignData(Categoria categoria, OnItemClickListener listener) {
            title.setText(categoria.getNombre());
            image.setImageResource(categoria.getImagen());
            progressBar.setProgress(categoria.getProgreso());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(categoria,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Categoria categoria , int position);
    }
}
