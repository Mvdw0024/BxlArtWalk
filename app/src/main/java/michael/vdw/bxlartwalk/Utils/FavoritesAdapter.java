package michael.vdw.bxlartwalk.Utils;

import android.content.Context;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> implements Filterable {

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvArtist;
        final ImageView ivPhoto, ivDeleteFavorite;
        final CardView favoriteCard;
// todo OnClickListener detailistener
        FavoriteViewHolder(View favoriteView){
            super(favoriteView);
            tvTitle          = favoriteView.findViewById(R.id.tv_favoritListCard_titleOfArt);
            tvArtist         = favoriteView.findViewById(R.id.tv_favoritListCard_autor);
            ivPhoto          = favoriteView.findViewById(R.id.iv_favoritListCard_photo);
            ivDeleteFavorite = favoriteView.findViewById(R.id.iv_favoritListCard_delete);
            favoriteCard     = favoriteView.findViewById(R.id.favorite_card);
        }

    }
FragmentActivity mContext;
    private  ArrayList<CbArt>     cbFavorites;
    private  ArrayList<CbArt>     OGCbFavorites;
    private ArrayList<StreetArt> streetArtFavorites;
    private ArrayList<StreetArt> OGStreetArtFavorites;
    //private Context context;

    public FavoritesAdapter(FragmentActivity fragmentActivity){
        this.cbFavorites        = new ArrayList<>();
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
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString();

                cbFavorites = OGCbFavorites;
                streetArtFavorites = OGStreetArtFavorites;

                if(!input.isEmpty()){

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
                        cbFavorites = tempListCbArt;
                    }

                    for (StreetArt elementStreetArt :streetArtFavorites){
                        if (elementStreetArt.getWorkname().toLowerCase().contains(input.toLowerCase())) {
                            tempListStreetArt.add(elementStreetArt);
                        } else {
                            if (elementStreetArt.getArtists().toLowerCase().contains(input.toLowerCase())) {
                                tempListStreetArt.add(elementStreetArt);
                            }
                        }
                        streetArtFavorites = tempListStreetArt;
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
