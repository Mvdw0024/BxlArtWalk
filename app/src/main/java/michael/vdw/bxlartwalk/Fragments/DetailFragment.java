package michael.vdw.bxlartwalk.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private TextView titleTv, authorTv, yearTv;
    private ImageView photoIv, favButtonIv;
    private CbArt selectedCbArt;
    private StreetArt selectedStreetArt;
    private FragmentActivity myContext;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(Bundle data) {
        DetailFragment df = new DetailFragment();
        df.setArguments(data);
        return df;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        selectedCbArt = (getArguments() != null) ? (CbArt) getArguments().getSerializable("passedCbArt") : null;
        selectedStreetArt = (getArguments() != null) ? (StreetArt) getArguments().getSerializable("passedStreetArt") : null;

        titleTv = rootView.findViewById(R.id.tv_detail_titleOfTheArt);
        photoIv = rootView.findViewById(R.id.iv_detail_photo);
        yearTv = rootView.findViewById(R.id.tv_detail_yearOfTheArt);
        favButtonIv = rootView.findViewById(R.id.iv_detail_favorite);
        authorTv = rootView.findViewById(R.id.tv_detail_authorOfTheArt);

        final ArtViewModel model = new ViewModelProvider(this).get(ArtViewModel.class);
        View.OnClickListener addArtToFavorites = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedCbArt != null) {
                    selectedCbArt.setFavorite(1);
                    model.updateCbArtInDatabase(selectedCbArt);
                    favButtonIv.setImageResource(R.drawable.ic_favorite_active);
                    Toast.makeText(v.getContext(), "'" + selectedCbArt.getCharacters() + "' was added to favorites.", Toast.LENGTH_LONG).show();
                }

                if (selectedStreetArt != null) {
                    selectedStreetArt.setFavorite(1);
                    model.updateStreetArtInDatabase(selectedStreetArt);
                    favButtonIv.setImageResource(R.drawable.ic_favorite_active);
                    Toast.makeText(v.getContext(), "'" + selectedStreetArt.getWorkname() + " by " + selectedStreetArt.getArtists() + "' was added to favorites.", Toast.LENGTH_LONG).show();
                }

            }
        };
        favButtonIv.setOnClickListener(addArtToFavorites);


        //get argument scherm gaat argumenten binnen trekken zie main_nav
        Bundle data = getArguments();
        if (selectedCbArt != null) {
            titleTv.setText(selectedCbArt.getCharacters());
            yearTv.setText(""+selectedCbArt.getYear());
            authorTv.setText(selectedCbArt.getAuthors());
            Picasso.get().load("https://opendata.brussel.be/explore/dataset/striproute0/files/" + selectedCbArt.getPhotoid() + "/download").into(photoIv);

            if(model.findCbById(selectedCbArt.getId()).isFavorite() == 1) {
                favButtonIv.setImageResource(R.drawable.ic_favorite_active);
            }
        }

        if (selectedStreetArt != null) {
            titleTv.setText(selectedStreetArt.getWorkname());
            yearTv.setText("" + selectedStreetArt.getJaar());
            authorTv.setText(selectedStreetArt.getArtists());
            Picasso.get().load("https://opendata.brussel.be/explore/dataset/striproute0/files/" + selectedStreetArt.getPhotoid() + "/download").into(photoIv);
        }

        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (selectedCbArt != null) {
            selectedCbArt.setAuthors(authorTv.getText().toString());
            selectedCbArt.setCharacters(titleTv.getText().toString());
        } else {
            if (selectedStreetArt != null) {
                selectedStreetArt.setArtists(authorTv.getText().toString());
                selectedStreetArt.setArtists(titleTv.getText().toString());
            }
            Navigation.findNavController(getView()).navigateUp();
        }
        return super.onOptionsItemSelected(item);
    }


}
