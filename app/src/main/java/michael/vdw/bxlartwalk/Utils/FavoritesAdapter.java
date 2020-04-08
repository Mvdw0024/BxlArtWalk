package michael.vdw.bxlartwalk.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvArtist;
        final ImageView ivPhoto, ivDeleteFavorite;
        final CardView favoriteCard;

        FavoriteViewHolder(View favoriteView){
            super(favoriteView);
            tvTitle          = favoriteView.findViewById(R.id.tv_favoritListCard_titleOfArt);
            tvArtist         = favoriteView.findViewById(R.id.tv_favoritListCard_autor);
            ivPhoto          = favoriteView.findViewById(R.id.iv_favoritListCard_photo);
            ivDeleteFavorite = favoriteView.findViewById(R.id.iv_favoritListCard_delete);
            favoriteCard     = favoriteView.findViewById(R.id.favorite_card);
        }

    }

    private final List<CbArt>     cbFavorites;
    private final List<StreetArt> streetArtFavorites;
    private Context context;

    public FavoritesAdapter(){
        this.cbFavorites        = new ArrayList<>();
        this.streetArtFavorites = new ArrayList<>();
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

//        if (cbFavorites.size() > 0 && position < cbFavorites.size()) {

            final CbArt currentFavoriteCbArt = cbFavorites.get(position);
            String title =
                (currentFavoriteCbArt.getCharacters() != "") ?
                currentFavoriteCbArt.getCharacters() :
                "Unknown";
            String author =
                (currentFavoriteCbArt.getAuthors() != "") ?
                currentFavoriteCbArt.getAuthors() :
                "Unknown";

            holder.tvTitle.setText(title);
            holder.tvArtist.setText(author);

            if(currentFavoriteCbArt.getPhotoid() != "Unknown") {
                Picasso.get().load("https://opendata.brussel.be/explore/dataset/striproute0/files/" + currentFavoriteCbArt.getPhotoid() + "/download").into(holder.ivPhoto);
            }

//        }

    }

    @Override
    public int getItemCount() {
        Log.d("checktest", "items in getItemCount from FavAdapter: " + cbFavorites.size());
        return cbFavorites.size() + streetArtFavorites.size();
    }

    public void addCbFavorites(List<CbArt> cbFavoritesToAdd) {

        if(cbFavoritesToAdd != null){
            cbFavorites.clear();
            cbFavorites.addAll(cbFavoritesToAdd);
        }

    }
}
