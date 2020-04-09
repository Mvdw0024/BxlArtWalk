package michael.vdw.bxlartwalk.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Utils.CbArtAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private static final int PERMISSION_ID = 132023;
    private final LatLng coordBrussel = new LatLng(50.858712, 4.347446);
    private View view;

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
    private CbArtAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;

    private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
//            artViewModel.getCbRouteArt();
//            artViewModel.getStreetArtRoute();
            mMap = googleMap;
            CameraUpdate moveToBrussel = CameraUpdateFactory.newLatLngZoom(coordBrussel, 12);
            mMap.animateCamera(moveToBrussel);
            mMap.setOnInfoWindowClickListener(infoWindowClickListener);
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            drawUserMarker();
            drawMarkers();

        }
    };
    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Bundle data = new Bundle();
            /*int position = getAdapterPosition();*/
            CbArt cb = null;
            StreetArt sa = null;

            if (marker.getTag() instanceof CbArt)
                cb = (CbArt) marker.getTag();
            else
                sa = (StreetArt) marker.getTag();

            if (cb != null) {
                data.putSerializable("passedCbArt", cb);
                Navigation.findNavController(mapView).navigate(R.id.action_mapFragment_to_detailFragment, data);
            } else {
                Toast.makeText(getActivity(), "Comic Book Route", Toast.LENGTH_SHORT).show();
            }
            if (sa != null) {//
                data.putSerializable("passedStreetArt", sa);
                Navigation.findNavController(mapView).navigate(R.id.action_mapFragment_to_detailFragment, data);
            } else {
                Toast.makeText(getActivity(), "Street Art", Toast.LENGTH_SHORT).show();
            }


        }
    };

    private void drawMarkers() {

        for (CbArt cbArtMarker : artViewModel.getAllCbArtFromDataBase()) {
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(cbArtMarker.getLat(), cbArtMarker.getLng())));
            m.setTitle(cbArtMarker.getCharacters());
            m.setSnippet(cbArtMarker.getAuthors());
            m.setTag(cbArtMarker);
        }

        for (StreetArt streetArtMarker : artViewModel.getAllStreetArtFromDataBase()) {
            Marker s = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(streetArtMarker.getLat(), streetArtMarker.getLng()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            s.setTitle(streetArtMarker.getWorkname());
            s.setSnippet(streetArtMarker.getArtists());
            s.setTag(streetArtMarker);
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragmentActivity);

        return rootView;
    }

    private void drawUserMarker() {
        //TODO: moet aangevuld worden; M
/*        if fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Location userLoc = location;
                            LatLng userLocGeo = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
                            MarkerOptions m = new MarkerOptions().position(userLocGeo).title("You are here");
                            mMap.addMarker(m);

                        } else {
                            Toast.makeText(getActivity(), "User Location Unknown", Toast.LENGTH_LONG).show();
                        }*/
        Toast.makeText(getActivity(), "User Location Unknown", Toast.LENGTH_LONG).show();

    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentActivity = (FragmentActivity) context;
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