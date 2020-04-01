package michael.vdw.bxlartwalk.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Room.CbArtDataBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    //nodig voor de tab
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    //Fragment to implement a MapView

    private View rootView;
    private MapView mapView;
    private GoogleMap mMap;
    private FragmentActivity fragmentActivity;
    private ArtViewModel artViewModel;

    private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            artViewModel.getCbRouteArt();
            artViewModel.getStreetArtRoute();
            mMap = googleMap;
            LatLng coordBrussel = new LatLng(50.858712, 4.347446);
            CameraUpdate moveToBrussel = CameraUpdateFactory.newLatLngZoom(coordBrussel, 10);
            mMap.animateCamera(moveToBrussel);
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            drawMarkers();

        }
    };

    private void drawMarkers() {

        for (CbArt cbArtMarker : artViewModel.getAllCbArtFromDataBase()) {
            Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(cbArtMarker.getLat(), cbArtMarker.getLng())));
            m.setTitle(cbArtMarker.getCharacters());
            m.setSnippet(cbArtMarker.getAuthors());
        }
        for (StreetArt streetArtMarker : artViewModel.getAllStreetArtFromDataBase()) {
            Marker s = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(streetArtMarker.getLat(), streetArtMarker.getLng()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            s.setSnippet(streetArtMarker.getArtists());
            s.setTitle(streetArtMarker.getWorkname());


        }

    }

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
        setHasOptionsMenu(true);

        artViewModel = new ViewModelProvider(getActivity()).get(ArtViewModel.class);
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