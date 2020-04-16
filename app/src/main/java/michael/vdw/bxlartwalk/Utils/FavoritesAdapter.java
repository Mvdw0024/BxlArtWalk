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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import michael.vdw.bxlartwalk.Fragments.DetailFragment;
import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>  implements Filterable {

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private FragmentActivity activity;
        final TextView tvTitle, tvArtist;
        final ImageView ivPhoto, ivDeleteFavorite;
        final CardView favoriteCard;

        FavoriteViewHolder(View favoriteView) {
            super(favoriteView);
            tvTitle = favoriteView.findViewById(R.id.tv_favoritListCard_titleOfArt);
            tvArtist = favoriteView.findViewById(R.id.tv_favoritListCard_autor);
            ivPhoto = favoriteView.findViewById(R.id.iv_favoritListCard_photo);
            ivDeleteFavorite = favoriteView.findViewById(R.id.iv_favoritListCard_delete);
            favoriteCard = favoriteView.findViewById(R.id.favorite_card);
        }

    }

    FragmentActivity mContext;
    private ArrayList<CbArt> cbFavorites;
    private List<CbArt> OGCbFavorites;
    private ArrayList<StreetArt> streetArtFavorites;
    private List<StreetArt> OGStreetArtFavorites;

    public FavoritesAdapter(FragmentActivity fragmentActivity) {
        this.cbFavorites = new ArrayList<>();
        this.OGCbFavorites = new ArrayList<>();
        this.streetArtFavorites = new ArrayList<>();
        this.OGStreetArtFavorites = new ArrayList<>();

        mContext = fragmentActivity;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.favorit_card, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        final ArtViewModel model = new ViewModelProvider(this.mContext).get(ArtViewModel.class);

        // CbArt Favorites
        if (cbFavorites.size() > 0 && position < cbFavorites.size()) {

            final CbArt currentFavoriteCbArt = cbFavorites.get(position);

            // Navigate to Detail
            View.OnClickListener cbDetailListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putSerializable("passedCbArt", currentFavoriteCbArt);

                    DetailFragment details = DetailFragment.newInstance(data);
                    mContext.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_fragment, details)
                            .addToBackStack("BACK")
                            .commit();
                }
            };
            holder.favoriteCard.setOnClickListener(cbDetailListener);

            View.OnClickListener deleteCbFromFavorites = new View.OnClickListener(){
                // Remove favorite
                @Override
                public void onClick(View v) {
                    currentFavoriteCbArt.setFavorite(0);
                    model.updateCbArtInDatabase(currentFavoriteCbArt);
                    Toast.makeText(v.getContext(), "'" + currentFavoriteCbArt.getCharacters() + "' was removed from favorites.", Toast.LENGTH_SHORT).show();
                }
            };
            holder.ivDeleteFavorite.setOnClickListener(deleteCbFromFavorites);

            String cbTitle =
                    (currentFavoriteCbArt.getCharacters() != "")
                            ? currentFavoriteCbArt.getCharacters()
                            : "Unknown";
            String cbAuthor =
                    (currentFavoriteCbArt.getAuthors() != "")
                            ? currentFavoriteCbArt.getAuthors()
                            : "Unknown";

            holder.tvTitle.setText(cbTitle);
            holder.tvArtist.setText(cbAuthor);

            if (currentFavoriteCbArt.getPhotoid() != "Unknown") {
                Picasso.get().load("https://opendata.brussel.be/explore/dataset/striproute0/files/" + currentFavoriteCbArt.getPhotoid() + "/download").into(holder.ivPhoto);
            }


        }

        // StreetArt Favorites
        if (streetArtFavorites.size() > 0 && position >= cbFavorites.size()) {

            final StreetArt currentFavoriteStreetArt = streetArtFavorites.get(position - cbFavorites.size());

            // Navigate to Detail
            View.OnClickListener streetArtDetailListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putSerializable("passedStreetArt", currentFavoriteStreetArt);

                    DetailFragment details = DetailFragment.newInstance(data);
                    mContext.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_fragment, details)
                            .addToBackStack("BACK")
                            .commit();
                }
            };
            holder.favoriteCard.setOnClickListener(streetArtDetailListener);

            View.OnClickListener deleteCbFromFavorites = new View.OnClickListener(){
                // Remove favorite
                @Override
                public void onClick(View v) {

                    currentFavoriteStreetArt.setFavorite(0);
                    model.updateStreetArtInDatabase(currentFavoriteStreetArt);
                    Toast.makeText(v.getContext(), "'" + currentFavoriteStreetArt.getWorkname() + " by " + currentFavoriteStreetArt.getArtists() + "' was removed from favorites.", Toast.LENGTH_SHORT).show();
                }
            };
            holder.ivDeleteFavorite.setOnClickListener(deleteCbFromFavorites);

            String streetArtTitle =
                    (currentFavoriteStreetArt.getWorkname() != "")
                            ? currentFavoriteStreetArt.getWorkname()
                            : "Unknown";
            String streetArtAuthor =
                    (currentFavoriteStreetArt.getArtists() != "")
                            ? currentFavoriteStreetArt.getArtists()
                            : "Unknown";

            holder.tvTitle.setText(streetArtTitle);
            holder.tvArtist.setText(streetArtAuthor);

            if (currentFavoriteStreetArt.getPhotoid() != "Unknown") {
                Picasso.get().load("https://opendata.brussel.be/explore/dataset/striproute0/files/" + currentFavoriteStreetArt.getPhotoid() + "/download").into(holder.ivPhoto);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cbFavorites.size() + streetArtFavorites.size();
    }

    public void addCbFavorites(List<CbArt> cbFavoritesToAdd) {

        if (cbFavoritesToAdd != null) {

            cbFavorites.clear();
            cbFavorites.addAll(cbFavoritesToAdd);
            OGCbFavorites = cbFavoritesToAdd;
        }

    }

    public void addStreetArtFavorites(List<StreetArt> streetArtFavoritesToAdd) {
        if (streetArtFavoritesToAdd != null) {
            streetArtFavorites.clear();
            streetArtFavorites.addAll(streetArtFavoritesToAdd);
            OGStreetArtFavorites = streetArtFavoritesToAdd;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString();

                cbFavorites = (ArrayList<CbArt>) OGCbFavorites;
                streetArtFavorites = (ArrayList<StreetArt>) OGStreetArtFavorites;

                if (!input.isEmpty()) {

                    ArrayList<CbArt> tempListCbArt = new ArrayList<>();
                    ArrayList<StreetArt> tempListStreetArt = new ArrayList<>();


                    for (CbArt elementCbArt : cbFavorites) {
                        if (elementCbArt.getCharacters().toLowerCase().contains(input.toLowerCase())) {
                            tempListCbArt.add(elementCbArt);
                        } else {
                            if (elementCbArt.getAuthors().toLowerCase().contains(input.toLowerCase())) {
                                tempListCbArt.add(elementCbArt);
                            }
                        }
                        OGCbFavorites = tempListCbArt;
                    }

                    for (StreetArt elementStreetArt : streetArtFavorites) {
                        if (elementStreetArt.getWorkname().toLowerCase().contains(input.toLowerCase())) {
                            tempListStreetArt.add(elementStreetArt);
                        } else {
                            if (elementStreetArt.getArtists().toLowerCase().contains(input.toLowerCase())) {
                                tempListStreetArt.add(elementStreetArt);
                            }
                        }
                        OGStreetArtFavorites = tempListStreetArt;
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
