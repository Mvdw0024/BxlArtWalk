package michael.vdw.bxlartwalk.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView titleTv = rootView.findViewById(R.id.tv_detail_titleOfTheArt);
       // ImageView photoIv = rootView.findViewById(R.id.iv_detail_photo);
        TextView yearTv = rootView.findViewById(R.id.tv_detail_yearOfTheArt);
        TextView authorTv =rootView.findViewById(R.id.tv_detail_authorOfTheArt);

        //get argument scherm gaat ergumenten binnen trekken zie main_nav
        Bundle data = getArguments();
        if (data != null) {
            if (data.containsKey("passedCbArt")) {
                CbArt cbArt = (CbArt) data.getSerializable("passedCbArt");
                titleTv.setText(cbArt.getCharacters());
                yearTv.setText(cbArt.getYear());
                authorTv.setText(cbArt.getAuthors());
                //photoIv.setImageView(cbArt.getphotourl());

            }

            if (data.containsKey("passedStreetArt")) {
                StreetArt streetArt = (StreetArt) data.getSerializable("passedStreetArt");
//                titleTv.setText(streetArt.getCharacters());
//                yearTv.setText(streetArt.getYear());
//                authorTv.setText(streetArt.getAuthors());
                //photoIv.setImageView(streetArt.getphotourl());

            }
        }
        return rootView;
    }
}
