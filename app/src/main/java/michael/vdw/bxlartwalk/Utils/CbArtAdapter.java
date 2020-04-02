package michael.vdw.bxlartwalk.Utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
                data.putSerializable("passedCbArt", itemsCbArt.get(position));
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
    private ArrayList<StreetArt> itemsStreetArt;
    private ArrayList<StreetArt> OGItemsStreetArt;

    public CbArtAdapter(FragmentActivity fragmentActivity) {
        //CbArt
        itemsCbArt = new ArrayList<>();
        OGItemsCbArt = new ArrayList<>();
        //streetArt
        itemsStreetArt = new ArrayList<>();
        OGItemsStreetArt = new ArrayList<>();
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

        Log.d("position", "" + position);
        Log.d("itemsCbArt", "" + itemsCbArt);
        //TODO: itemsCbArt blijkt een lege array te zijn: [] -> dus index 0 is iets dat niet bestaat, vandaar de IndexOutOfBoundsException error
        CbArt currentCbArt = itemsCbArt.get(position);
        if (currentCbArt.getCharacters() == "") {
            holder.tvTitle.setText("Unknown");
        } else {
            holder.tvTitle.setText(currentCbArt.getCharacters());
        }

        //holder.ivPhoto.setImageIcon(currentArt.getphotoUrl());
        if (currentCbArt.getAuthors() == "") {
            holder.tvArtist.setText("Unknown");
        } else {
            holder.tvArtist.setText(currentCbArt.getAuthors());
        }

        //        holder.tvYear.setText(currentArt.getYear());

        StreetArt currentStreetArt = itemsStreetArt.get(position);
        if (currentStreetArt.getWorkname() == "") {
            holder.tvTitle.setText("Unknown");
        } else {
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
        int streetArtSize = itemsStreetArt.size();
        return cbArtSize + streetArtSize;
    }


    public void addCbItems(ArrayList<CbArt> cbArts) {
        itemsCbArt.clear();
        itemsCbArt.addAll(cbArts);
        OGItemsCbArt = cbArts;

/* hier maak je 2 aparte Arraylists, volgens mij is dit niet correct // M
        OGItemsCbArt.clear();
        OGItemsCbArt.addAll(cbArts);
        */
    }

    public void addStreetItems(ArrayList<StreetArt> streetArts) {
        itemsStreetArt.clear();
        itemsStreetArt.addAll(streetArts);
        OGItemsStreetArt = streetArts;
/* hier maak je 2 aparte Arraylists, volgens mij is dit niet correct // M
        OGItemsStreetArt.clear();
        OGItemsStreetArt.addAll(streetArts);
        */
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString();

                if (input.isEmpty()) {
                    itemsCbArt = OGItemsCbArt;
                } else {
                    itemsCbArt = OGItemsCbArt;
                    ArrayList<CbArt> tempList = new ArrayList<>();
                    for (CbArt element : itemsCbArt) {
                        if (element.getCharacters().toLowerCase().contains(input.toLowerCase())) {
                            tempList.add(element);
                        } else {
                            if (element.getAuthors().toLowerCase().contains(input.toLowerCase())) {
                                tempList.add(element);
                            }
                            itemsCbArt = tempList;
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


