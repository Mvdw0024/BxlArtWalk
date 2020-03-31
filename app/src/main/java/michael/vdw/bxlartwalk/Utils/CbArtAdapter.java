package michael.vdw.bxlartwalk.Utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.R;

public class CbArtAdapter extends RecyclerView.Adapter<CbArtAdapter.ArtViewHolder> implements Filterable {

    class ArtViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvArtist, tvYear;
        final ImageView ivPhoto;

        final View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //welke card(rij)?
                int position = getAdapterPosition();

                Bundle data = new Bundle();
                data.putSerializable("passedArt", items.get(position));

                //navigatie starten

                Navigation.findNavController(view).navigate(R.id.artList_to_detail, data);
            }
        };
        //default constructor(zonder parameter)

        public ArtViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_detail_titleOfTheArt);
            tvArtist = itemView.findViewById(R.id.tv_detail_authorOfTheArt);
            tvYear = itemView.findViewById(R.id.tv_detail_yearOfTheArt);
            ivPhoto = itemView.findViewById(R.id.iv_detail_photo);
        }
    }

    private ArrayList<CbArt> items;
    private ArrayList<CbArt> OGItems;

    public CbArtAdapter() {
        items = new ArrayList<>();
        OGItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View card = layoutInflater.inflate(R.layout.art_card, parent, false);

        return new ArtViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtViewHolder holder, int position) {
        CbArt currentArt = items.get(position);
        holder.tvTitle.setText(currentArt.getCharacters());
        //holder.ivPhoto.setImageIcon(currentArt.getphotoUrl());
        holder.tvArtist.setText(currentArt.getAuthors());
        holder.tvYear.setText(currentArt.getYear());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(ArrayList<CbArt> cbArts) {
        items.clear();
        items.addAll(cbArts);
        OGItems.clear();
        OGItems.addAll(cbArts);
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString();
                if(input.isEmpty()) {
                    items = OGItems;
                }else{
                    items = OGItems;
                    ArrayList<CbArt> tempList = new ArrayList<>();
                    for (CbArt element : items){
                        if (element.getCharacters().contains(input)) {
                            tempList.add(element);
                        }else{ if (element.getAuthors().contains(input)){
                            tempList.add(element);
                        }

                        }
                    }
                    items = tempList;
                }

                return null;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }
}


