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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import michael.vdw.bxlartwalk.Fragments.DetailFragment;
import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;

public class CbArtAdapter extends RecyclerView.Adapter<CbArtAdapter.ArtViewHolder> implements Filterable {

    class ArtViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvArtist;
        final ImageView ivPhoto, ivArtListFavorite;
        final CardView artCard;
        private int currentFavoritImmage;
        int[] favoritImages= {R.drawable.ic_action_favorit, R.drawable.ic_action_myfavorit};

        private View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //welke card(rij)?
                Log.d("detailTest", "watIkMaarWil");
                int position = getAdapterPosition();
                    CbArt cbToPass = OGItemsCbArt.get(position);
                StreetArt saToPass = OGItemsStreetArt.get(position);
                    Bundle data = new Bundle();
                    data.putSerializable("passedCbArt", cbToPass);
                 //   data.putSerializable("passedStreetArt", saToPass);





                //navigatie starten
               // Navigation.findNavController(view).navigate(R.id.artlist_to_detail, data);

                DetailFragment details = DetailFragment.newInstance(data);
                mContext.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, details)
                        .addToBackStack("BACK")
                        .commit();




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

    FragmentActivity mContext;
    private ArrayList<CbArt> itemsCbArt;
    private ArrayList<CbArt> OGItemsCbArt;
    private ArrayList<StreetArt> itemsStreetArt;
    private ArrayList<StreetArt> OGItemsStreetArt;
    private ArrayList<CbArt> cbFavorites;


    public CbArtAdapter(FragmentActivity fragmentActivity) {
        //CbArt
        itemsCbArt = new ArrayList<>();
        OGItemsCbArt = new ArrayList<>();
        //streetArt
        itemsStreetArt = new ArrayList<>();
        OGItemsStreetArt = new ArrayList<>();
        //favorites
        cbFavorites = new ArrayList<>();

        mContext = fragmentActivity;
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

        final ArtViewModel model = new ViewModelProvider(this.mContext).get(ArtViewModel.class);

        if (itemsCbArt.size() > 0 && position < itemsCbArt.size()) {

            final CbArt currentCbArt = itemsCbArt.get(position);

            // Add favorite on click on icon: change value of isFavorite field, then update the database
            View.OnClickListener addCbToFavorites = new View.OnClickListener() {
                //todo volgende 3 lijnen gebruiken om na klik image aan te passen (denkt talia ;))
//                    currentFavoritImmage++;
//                    currentFavoritImmage = currentFavoritImmage % favoritImages.length;
//                    ivArtListFavorite.setImageResource(favoritImages[currentFavoritImmage]);

//                }
                @Override
                public void onClick(View v) {
                    currentCbArt.setFavorite(1);
                    model.updateCbArtInDatabase(currentCbArt);
                    Toast.makeText(v.getContext(), "'" + currentCbArt.getCharacters() + "' was added to favorites.", Toast.LENGTH_LONG).show();

                }
            };

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

            holder.ivArtListFavorite.setOnClickListener(addCbToFavorites
            );

        }

        if (itemsStreetArt.size() > 0 && position >= itemsCbArt.size()) {

            final StreetArt currentStreetArt = itemsStreetArt.get(position - itemsCbArt.size());

            // Add favorite on click on icon: change value of isFavorite field, then update the database
            View.OnClickListener addStreetArtToFavorites = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentStreetArt.setFavorite(1);
                    model.updateStreetArtInDatabase(currentStreetArt);
                    Toast.makeText(v.getContext(), "'" + currentStreetArt.getWorkname() + " by " + currentStreetArt.getArtists() + "' was added to favorites.", Toast.LENGTH_LONG).show();
                }
            };

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

            if (currentStreetArt.getPhotoid() != "Unknown") {
                Picasso.get().load("https://opendata.brussel.be/explore/dataset/street-art/files/" + currentStreetArt.getPhotoid() + "/download").into(holder.ivPhoto);
            }

            holder.ivArtListFavorite.setOnClickListener(addStreetArtToFavorites);
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
    }

    public void addStreetItems(ArrayList<StreetArt> streetArts) {
        itemsStreetArt.clear();
        itemsStreetArt.addAll(streetArts);
        OGItemsStreetArt = streetArts;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString();
                itemsCbArt = OGItemsCbArt;
                itemsStreetArt = OGItemsStreetArt;


                    if(!input.isEmpty()){

                    ArrayList<CbArt> tempListCbArt = new ArrayList<>();
                    ArrayList<StreetArt> tempListStreetArt = new ArrayList<>();


                    for (CbArt elementCbArt : itemsCbArt) {
                        if (elementCbArt.getCharacters().toLowerCase().contains(input.toLowerCase())) {
                            tempListCbArt.add(elementCbArt);
                        } else {
                            if (elementCbArt.getAuthors().toLowerCase().contains(input.toLowerCase())) {
                                tempListCbArt.add(elementCbArt);
                            }
                        }
                        OGItemsCbArt = tempListCbArt;
                    }

                    for (StreetArt elementStreetArt :itemsStreetArt){
                            if (elementStreetArt.getWorkname().toLowerCase().contains(input.toLowerCase())) {
                                tempListStreetArt.add(elementStreetArt);
                            } else {
                                if (elementStreetArt.getArtists().toLowerCase().contains(input.toLowerCase())) {
                                    tempListStreetArt.add(elementStreetArt);
                                }
                            }
                            OGItemsStreetArt = tempListStreetArt;
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


