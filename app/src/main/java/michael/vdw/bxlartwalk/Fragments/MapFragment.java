package michael.vdw.bxlartwalk.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Utils.CbArtAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private static final int PERMISSION_ID = 1320;
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
            mMap = googleMap;
            CameraUpdate moveToBrussel = CameraUpdateFactory.newLatLngZoom(coordBrussel, 12);
            mMap.animateCamera(moveToBrussel);
            mMap.setOnInfoWindowClickListener(infoWindowClickListener);
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            drawMarkers();
            if (checkPermissions())
                drawUserMarker();


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
                DetailFragment details = DetailFragment.newInstance(data);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, details)
                        .addToBackStack("BACK")
                        .commit();
            } else {
                Toast.makeText(getActivity(), "Street Art", Toast.LENGTH_SHORT).show();
            }
            if (sa != null) {
                data.putSerializable("passedStreetArt", sa);
                DetailFragment details = DetailFragment.newInstance(data);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, details)
                        .addToBackStack("BACK")
                        .commit();
            } else {
                Toast.makeText(getActivity(), "Comic Book Route", Toast.LENGTH_SHORT).show();
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
//        mMap.setMyLocationEnabled(true); << geeft NullPointerException

        if (checkPermissions()) {
            Task<Location> locationTask = fusedLocationClient.getLastLocation();
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                  /*      LatLng userLocGeo = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions m = new MarkerOptions()
                                .position(userLocGeo)
                                .title("You are here")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        mMap.addMarker(m);*/
                        mMap.setMyLocationEnabled(true);
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                    }
                }
            });
        }

    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(fragmentActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(fragmentActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permissions, PERMISSION_ID);
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ID:
                if (requestCode == PERMISSION_ID && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    drawUserMarker();
                    mMap.setMyLocationEnabled(true);
                }
                break;


        }
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