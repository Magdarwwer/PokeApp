package edu.url.salle.magdalena.morag.pokeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.url.salle.magdalena.morag.pokeapp.R;
import edu.url.salle.magdalena.morag.pokeapp.model.Ability;

public class AbilityAdapter extends RecyclerView.Adapter<AbilityAdapter.ViewHolder> {

    private ArrayList<Ability> abilities;
    private Set<Ability> selectedAbilities;
    private AbilityClickListener abilityClickListener;

    public AbilityAdapter() {
        this.abilities = new ArrayList<>();
        this.selectedAbilities = new HashSet<>();
    }

    public interface AbilityClickListener {
        void onAbilityClicked(Ability ability);
    }

    public void setAbilityClickListener(AbilityClickListener listener) {
        this.abilityClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ability, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ability ability = abilities.get(position);
        holder.textViewAbility.setText(ability.getName());

        // Toggle selection on click
        holder.itemView.setOnClickListener(v -> {
            handleAbilitySelection(ability);
            if (abilityClickListener != null) {
                abilityClickListener.onAbilityClicked(ability);
            }
            notifyDataSetChanged();
        });

        if (selectedAbilities.contains(ability)) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(R.color.selectedAbilityColor));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(android.R.color.transparent));
        }
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

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public Set<Ability> getSelectedAbilities() {
        return selectedAbilities;
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
        notifyDataSetChanged();
    }

    private void handleAbilitySelection(Ability ability) {
        selectedAbilities.clear();
        selectedAbilities.add(ability);

        Random random = new Random();
        int probability = random.nextInt(100);
        if (ability.isHidden()) {
            if (probability < 75) {
                Ability alternateAbility = getRandomAlternateAbility(ability);
                if (alternateAbility != null) {
                    selectedAbilities.add(alternateAbility);
                }
            }
        } else {
            if (probability < 50) {
                Ability alternateAbility = getRandomAlternateAbility(ability);
                if (alternateAbility != null) {
                    selectedAbilities.add(alternateAbility);
                }
            }
        }
    }

    private Ability getRandomAlternateAbility(Ability selectedAbility) {
        // Get a random alternate ability that is not the selected one
        ArrayList<Ability> alternateAbilities = new ArrayList<>(abilities);
        alternateAbilities.remove(selectedAbility);
        if (!alternateAbilities.isEmpty()) {
            Random random = new Random();
            return alternateAbilities.get(random.nextInt(alternateAbilities.size()));
        }
        return null;
    }
}
