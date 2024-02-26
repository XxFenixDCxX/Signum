package org.cuatrovientos.signum.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.models.Signo;

import java.util.List;

public class CardViewSignoAdapter extends RecyclerView.Adapter<CardViewSignoAdapter.CardViewSignoHolder> {

    private final List<Signo> listSigno;

    public CardViewSignoAdapter(List<Signo> listSigno) {
        this.listSigno = listSigno;
    }

    @NonNull
    @Override
    public CardViewSignoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new CardViewSignoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewSignoHolder holder, int position) {
        holder.assignData(listSigno.get(position));
    }

    @Override
    public int getItemCount() {
        return listSigno.size();
    }

    public static class CardViewSignoHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text;

        public CardViewSignoHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewItem);
            text = itemView.findViewById(R.id.textViewItem);
        }

        public void assignData(Signo signo) {
            image.setImageResource(signo.getImagen());
            text.setText(signo.getTitulo());
        }
    }
}