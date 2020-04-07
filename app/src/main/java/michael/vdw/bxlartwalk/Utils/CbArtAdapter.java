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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Room.CbArtDao;

public class CbArtAdapter extends RecyclerView.Adapter<CbArtAdapter.ArtViewHolder> implements Filterable {

    class ArtViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvArtist;
        final ImageView ivPhoto, ivArtListFavorite;
        public CardView artCard;

        private View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //welke card(rij)?
                Log.d("detailTest", "watIkMaarWil");
                int position = getAdapterPosition();
                CbArt cbToPass = itemsCbArt.get(position);
                StreetArt saToPass = itemsStreetArt.get(position);
                Bundle data = new Bundle();
                data.putSerializable("passedCbArt", cbToPass);
                data.putSerializable("passedStreetArt", saToPass);
                //navigatie starten
                Navigation.findNavController(view).navigate(R.id.artList_to_detail, data);
            }
        };

        //default constructor(zonder parameter)
        ArtViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_artListCart_titleOfTheArt);
            tvArtist = itemView.findViewById(R.id.tv_artListCard_autor);
//            tvYear = itemView.findViewById(R.id.tv_detail_yearOfTheArt);
            ivPhoto = itemView.findViewById(R.id.iv_artListCard_photo);
            ivArtListFavorite = itemView.findViewById(R.id.iv_artListCard_favorite);
            artCard = itemView.findViewById(R.id.cardArt);
            artCard.setOnClickListener(detailListener);
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

        if (itemsCbArt.size() > 0 && position < itemsCbArt.size()) {

            final CbArt currentCbArt = itemsCbArt.get(position);
            View.OnClickListener addCbToFavorites = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ckicke", currentCbArt.getCharacters());
                    Log.d("ckicke", "favorite: " + currentCbArt.isFavorite());
                    currentCbArt.setFavorite(true);
                    Log.d("ckicke", "favorite: " + currentCbArt.isFavorite());
                    // in database opslaan als favorite

                }
            };
//            final View.OnClickListener detailListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //welke card(rij)?
//                    Log.d("detailTest", "watIkMaarWil");
////                    int position = getAdapterPosition();
//                    Bundle data = new Bundle();
//                    data.putSerializable("passedCbArt", currentCbArt);
//                    //navigatie starten
//                    Navigation.findNavController(view).navigate(R.id.artList_to_detail, data);
//                }
//            };

            if (currentCbArt.getCharacters() == "") {
                holder.tvTitle.setText("Unknown");
            } else {
                holder.tvTitle.setText(currentCbArt.getCharacters());
            }

            if (currentCbArt.getAuthors() == "") {
                holder.tvArtist.setText("Unknown");
            } else {
                holder.tvArtist.setText(currentCbArt.getAuthors());
            }

            if (currentCbArt.getPhotoid() != "Unknown") {
                Picasso.get().load("https://opendata.brussel.be/explore/dataset/striproute0/files/" + currentCbArt.getPhotoid() + "/download").into(holder.ivPhoto);
            }

            holder.ivArtListFavorite.setOnClickListener(addCbToFavorites);
        }

        StreetArt currentStreetArt;
        if (itemsCbArt.size() > 0) {
            if (itemsStreetArt.size() > 0 && position >= itemsCbArt.size()) {
                currentStreetArt = itemsStreetArt.get(position - itemsCbArt.size());

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

                if (currentStreetArt.getPhotoid() != "Unkwown") {
                    Picasso.get().load("https://opendata.brussel.be/explore/dataset/street-art/files/" + currentStreetArt.getPhotoid() + "/download").into(holder.ivPhoto);
                }
            }
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
                itemsCbArt = OGItemsCbArt;


                if (input.isEmpty()) {
                    itemsCbArt = OGItemsCbArt;
                } else {
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


