package michael.vdw.bxlartwalk.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

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
    private ImageView photoIv;
    private CbArt selectedCbArt;
    private StreetArt selectedStreetArt;
    private FragmentActivity myContext;



    public DetailFragment() {
        // Required empty public constructor
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
        // photoIv = rootView.findViewById(R.id.iv_detail_photo);
        yearTv = rootView.findViewById(R.id.tv_detail_yearOfTheArt);
        authorTv = rootView.findViewById(R.id.tv_detail_authorOfTheArt);


        //get argument scherm gaat argumenten binnen trekken zie main_nav
        //Bundle data = getArguments();
        if (selectedCbArt != null) {
//            if (selectedCbArt.containsKey("passedCbArt")) {
//                CbArt cbArt = (CbArt) selectedCbArt.getSerializable("passedCbArt");
            titleTv.setText(selectedCbArt.getCharacters());
            yearTv.setText(selectedCbArt.getYear());
            authorTv.setText(selectedCbArt.getAuthors());
            //photoIv.setImageView(cbArt.getphotourl());

        }

        if (selectedStreetArt != null) {
            //StreetArt streetArt = (StreetArt) selectedStreetArt.getSerializable("passedStreetArt");
            titleTv.setText(selectedStreetArt.getWorkname());
            yearTv.setText(selectedStreetArt.getJaar());
            authorTv.setText(selectedStreetArt.getArtists());
            //photoIv.setImageView(streetArt.getphotourl());

        }

        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (selectedCbArt != null) {

            selectedCbArt.setAuthors(authorTv.getText().toString());
            selectedCbArt.setCharacters(titleTv.getText().toString());
            //selectedCbArt.setPhotoid();
            //selectedCbArt.setYear(yearTv.getText().charAt());

        } else {

            if (selectedStreetArt != null) {
                selectedStreetArt.setArtists(authorTv.getText().toString());
                selectedStreetArt.setArtists(titleTv.getText().toString());
                //selectedStreetArt.setPhotoid();
                //selectedStreetArt.setYear(yearTv.getText().charAt());


            }
            Navigation.findNavController(getView()).navigateUp();
        }

        return super.onOptionsItemSelected(item);
    }
}
