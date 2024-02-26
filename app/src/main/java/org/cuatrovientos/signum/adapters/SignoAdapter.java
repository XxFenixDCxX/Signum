package org.cuatrovientos.signum.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.models.Categoria;
import org.cuatrovientos.signum.models.Signo;

import java.util.List;

public class SignoAdapter extends RecyclerView.Adapter<SignoAdapter.SignoHolder>  {

    private List<Signo> listSigno;




    public SignoAdapter(List<Signo> listSigno ){
        this.listSigno = listSigno;
    }

    @NonNull
    @Override
    public SignoAdapter.SignoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.signo1_item,parent, false);
        return new SignoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignoAdapter.SignoHolder holder, int position) {
        holder.assignData(listSigno.get(position));
    }

    @Override
    public int getItemCount() {
        return listSigno.size();
    }

    public class SignoHolder extends RecyclerView.ViewHolder {

        ImageView image ;
        TextView text ;
        public SignoHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewSigno);
            text = itemView.findViewById(R.id.textViewSigno);
        }

        public void assignData(Signo signo) {
            image.setImageResource(signo.getImagen());
            text.setText(signo.getTitulo());
        }
    }


}
