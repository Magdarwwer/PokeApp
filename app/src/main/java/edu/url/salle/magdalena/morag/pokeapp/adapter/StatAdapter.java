package edu.url.salle.magdalena.morag.pokeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.R;

public class StatAdapter extends RecyclerView.Adapter<StatAdapter.ViewHolder> {

    private ArrayList<String> stats;


    public StatAdapter(ArrayList<String> stats) {
        if (stats == null) {
            this.stats = new ArrayList<>();
        } else {
            this.stats = stats;
        }
    }
    public StatAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stat = stats.get(position);
        holder.textViewStat.setText(stat);
    }

    @Override
    public int getItemCount() {
        return stats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStat = itemView.findViewById(R.id.textViewStat);
        }
    }

    public ArrayList<String> getStats() {
        return stats;
    }

    public void setStats(ArrayList<String> stats) {
        this.stats = stats;
    }
}
