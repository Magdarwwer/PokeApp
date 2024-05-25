package edu.url.salle.magdalena.morag.pokeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Stat;


public class StatAdapter extends RecyclerView.Adapter<StatAdapter.ViewHolder> {

    private ArrayList<Stat> stats;

    public StatAdapter(ArrayList<Stat> stats) {
        this.stats = stats;
    }

    public StatAdapter() {
        this.stats = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stat stat = stats.get(position);
        holder.textViewStat.setText(stat.getName() + ": " + stat.getBaseStat());
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

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stat> stats) {
        this.stats = stats;
    }
}