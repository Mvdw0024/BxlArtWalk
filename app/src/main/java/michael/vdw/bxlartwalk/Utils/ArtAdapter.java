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
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import michael.vdw.bxlartwalk.Models.Art;
import michael.vdw.bxlartwalk.R;

//public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ArtViewHolder> implements Filterable {
//
//    class ArtViewHolder extends RecyclerView.ViewHolder {
//        final TextView tvTitle, tvArtist, tvYear;
//        final ImageView ivPhoto;
//
//        final View.OnClickListener detailListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //welke card(rij)?
//                int position = getAdapterPosition();
//
//                //TODO data in bundel om door te kunnen geven
//                Bundle data = new Bundle();
//                data.putSerializable("passedArt", items.get(position));
//
//                //navigatie starten
//
//                Navigation.findNavController(view).navigate(R.id.artList_to_detail, data);
//            }
//        };
//        //default constructor(zonder parameter)
//
//
//        public ArtViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tv_detail_titleOfTheArt);
//            tvArtist = itemView.findViewById(R.id.tv_detail_authorOfTheArt);
//            tvYear = itemView.findViewById(R.id.tv_detail_yearOfTheArt);
//            ivPhoto = itemView.findViewById(R.id.iv_detail_photo);
//        }
//    }
//
//    private JSONObject items;
//    private JSONObject OGItems;
//
//    public ArtAdapter() {
//        items = new JSONObject();
//        OGItems = new JSONObject();
//    }
//
//    @NonNull
//    @Override
//    public ArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View card = layoutInflater.inflate(R.layout.art_card, parent, false);
//
//        return new ArtViewHolder(card);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ArtViewHolder holder, int position) {
//        Art currentArt = items.get(position);
//        holder.tvTitle.setText(currentArt.getTitle());
//        // holder.ivPhoto.setImageIcon(currentArt.getImageUrl());
//        holder.tvArtist.setText(currentArt.getAuthor());
//        holder.tvYear.setText(currentArt.getYear());
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public void addItems(JSONObject arts) {
//        items.clear();
//        items.addAll(arts);
//        OGItems.clear();
//        OGItems.addAll(arts);
//    }
//    @Override
//    public Filter getFilter() {
//
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String input = constraint.toString();
//                if(input.isEmpty()) {
//                    items = OGItems;
//                }else{
//                    items = OGItems;
//                    JSONObject tempList = new JSONObject();
//                    for (Art element : items){
//                        if (element.getSetup().contains(input)) {
//                            tempList.add(element);
//                        }
//                    }
//                    items = tempList;
//                }
//
//                return null;
//            }
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                notifyDataSetChanged();
//            }
//        };
//    }
//}
//
//
