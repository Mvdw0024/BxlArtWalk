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
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;

public class CbArtAdapter extends RecyclerView.Adapter<CbArtAdapter.ArtViewHolder> implements Filterable {

    class ArtViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvArtist;
        final ImageView ivPhoto;

        final View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //welke card(rij)?
                int position = getAdapterPosition();

                Bundle data = new Bundle();
                data.putSerializable("passedArt", itemsCbArt.get(position));

                //navigatie starten

                Navigation.findNavController(view).navigate(R.id.artList_to_detail, data);
            }
        };
        //default constructor(zonder parameter)

        public ArtViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_artListCart_titleOfTheArt);
            tvArtist = itemView.findViewById(R.id.tv_artListCard_autor);
//            tvYear = itemView.findViewById(R.id.tv_detail_yearOfTheArt);
            ivPhoto = itemView.findViewById(R.id.iv_artListCard_photo);
        }
    }

    private ArrayList<CbArt> itemsCbArt;
    private ArrayList<CbArt> OGItemsCbArt;
    private ArrayList<StreetArt> items;
    private ArrayList<StreetArt> OGItems;

    public CbArtAdapter() {
        itemsCbArt = new ArrayList<>();
        OGItemsCbArt = new ArrayList<>();
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

        CbArt currentArt = itemsCbArt.get(position);
        if (currentArt.getCharacters() == "") {
            holder.tvTitle.setText("Unknown");
        } else {
            holder.tvTitle.setText(currentArt.getCharacters());
        }

        //holder.ivPhoto.setImageIcon(currentArt.getphotoUrl());
        if (currentArt.getAuthors() == "") {
            holder.tvArtist.setText("Unknown");
        } else {
            holder.tvArtist.setText(currentArt.getAuthors());
        }

        //        holder.tvYear.setText(currentArt.getYear());

StreetArt currentStreetArt = items.get(position);
        if (currentStreetArt.getWorkname() ==""){
            holder.tvTitle.setText("Unknown");
        }else{
            holder.tvTitle.setText(currentStreetArt.getWorkname());
        }
        if (currentStreetArt.getArtists() == "") {
            holder.tvArtist.setText("Unknown");
        } else {
            holder.tvArtist.setText(currentStreetArt.getArtists());
        }


    }

    @Override
    public int getItemCount() {
        int cbArtSize = itemsCbArt.size();
        int streetArtSize = items.size();
        return cbArtSize + streetArtSize;
    }


    public void addItems(ArrayList<CbArt> cbArts, ArrayList<StreetArt> streetArts) {
        itemsCbArt.clear();
        items.clear();
        itemsCbArt.addAll(cbArts);
        items.addAll(streetArts);

        OGItemsCbArt.clear();
        OGItems.clear();
        OGItemsCbArt.addAll(cbArts);
       OGItems.addAll(streetArts);
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString();


                if (input.isEmpty()) {
                    itemsCbArt = OGItemsCbArt;
                } else {
                    itemsCbArt = OGItemsCbArt;
                    ArrayList<CbArt> tempList = new ArrayList<>();
                    for (CbArt element : itemsCbArt) {
                        if (element.getCharacters().contains(input)) {
                            tempList.add(element);
                        } else {
                            if (element.getAuthors().contains(input)) {
                                tempList.add(element);
                            }

                        }
                    }

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


