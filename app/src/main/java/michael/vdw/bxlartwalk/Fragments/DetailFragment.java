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
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Utils.CbArtAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private TextView titleTv, authorTv, yearTv, addressTv, favButtonTv;
    private ImageView photoIv;
    private CbArt selectedCbArt;
    private StreetArt selectedStreetArt;
    private FragmentActivity myContext;
    private TabLayout tabLayout;
    private CbArtAdapter adapter;
    private View.OnClickListener pinToMapListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Bundle data = new Bundle();
//           // MapFragment pinToMap = MapFragment.newInstance(data);
//            myContext.getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container_fragment, pinToMap)
//                    .addToBackStack("BACK")
//                    .commit();
            Toast.makeText(myContext, "PIN TO MAP", Toast.LENGTH_SHORT).show();
        }
    };


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
//        addressTv = rootView.findViewById(R.id.tv_detail_adresOfTheArt);
//        favButtonTv = rootView.findViewById(R.id.detail_tv_favorite);
        authorTv = rootView.findViewById(R.id.tv_detail_authorOfTheArt);
        ImageView pinToMap = rootView.findViewById(R.id.iv_detail_map);
        pinToMap.setOnClickListener(pinToMapListener);


        //get argument scherm gaat argumenten binnen trekken zie main_nav
        Bundle data = getArguments();
        if (selectedCbArt != null) {
//            if (selectedCbArt.containsKey("passedCbArt")) {
//                CbArt cbArt = (CbArt) selectedCbArt.getSerializable("passedCbArt");
            titleTv.setText(selectedCbArt.getCharacters());
            yearTv.setText("" + selectedCbArt.getYear());
//            favButtonTv.setText(R.string.favButtonText);
            authorTv.setText(selectedCbArt.getAuthors());
            Picasso.get().load("https://opendata.brussel.be/explore/dataset/striproute0/files/" + selectedCbArt.getPhotoid() + "/download").into(photoIv);
        }

        if (selectedStreetArt != null) {
            //StreetArt streetArt = (StreetArt) selectedStreetArt.getSerializable("passedStreetArt");
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
