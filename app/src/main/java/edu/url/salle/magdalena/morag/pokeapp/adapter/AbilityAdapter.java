package edu.url.salle.magdalena.morag.pokeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.url.salle.magdalena.morag.pokeapp.R;

public class AbilityAdapter extends RecyclerView.Adapter<AbilityAdapter.ViewHolder> {

    private ArrayList<String> abilities;

    public AbilityAdapter(ArrayList<String> abilities) {
        this.abilities = abilities;
    }
    public AbilityAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ability, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ability = abilities.get(position);
        holder.textViewAbility.setText(ability);
    }

    @Override
    public int getItemCount() {
        return abilities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAbility;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAbility = itemView.findViewById(R.id.textViewAbility);
        }
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }
}
