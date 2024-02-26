package org.cuatrovientos.signum.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.cuatrovientos.signum.R;
import org.cuatrovientos.signum.models.Signo;

import java.util.List;

public class FiltroSignosAdapter extends ArrayAdapter<Signo> {
    private Context context;

    public FiltroSignosAdapter(@NonNull Context context, int resource, List<Signo> signos) {
        super(context, resource, signos);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.autocomplete_item, parent, false);
        }

        // Obtén el elemento actual
        Signo signo = getItem(position);

        // Configura la vista personalizada
        TextView wordTextView = convertView.findViewById(R.id.wordTextView);

        if (signo != null) {
            wordTextView.setText(signo.getTitulo());
        }

        return convertView;
    }


}
