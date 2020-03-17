package michael.vdw.bxlartwalk.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import michael.vdw.bxlartwalk.Models.Art;
import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    //Fragment to implement a MapView

    private View rootView;
    private MapView mapView;
    private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng coordBrussel = new LatLng(50.858712, 4.347446);
            CameraUpdate moveToBrussel = CameraUpdateFactory.newLatLngZoom(coordBrussel, 16);
            mMap.animateCamera(moveToBrussel);
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        }
    };
    private GoogleMap mMap;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(onMapReady);
        ArtViewModel artViewModel = new ViewModelProvider(getActivity()).get(ArtViewModel.class);
        /* TODO: aanpassen voor ArtRoute
        //voorbeeld uit demo:

        iv = rootView.findViewById(R.id.iv_icon);
        tv = rootView.findViewById(R.id.tv_joke);

        artViewModel.threadExecutor(getActivity(), new Observer<Art>() {
        /*    @Override
            public void onChanged(RandomJoke randomJoke) {
                Picasso.get().load(randomJoke.getImageUrl()).into(iv);
                tv.setText(randomJoke.getJokeText());
         */
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}