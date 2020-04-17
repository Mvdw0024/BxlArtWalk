package michael.vdw.bxlartwalk.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Utils.CbArtAdapter;
import michael.vdw.bxlartwalk.Utils.Directionhelpers.FetchURL;
import michael.vdw.bxlartwalk.Utils.Directionhelpers.TaskLoadedCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements TaskLoadedCallback {

    private static final int PERMISSION_ID = 1320;
    private final LatLng coordBrussel = new LatLng(50.858712, 4.347446);
    private final LatLng coordMechelen = new LatLng(51.0258761, 4.4775362);

    private Location userLoc;
    private LatLng userLocGeo;
    private TextView cblegend;
    private TextView salegend;
    private Bundle dataFromDetail;

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
    private Polyline currentPolyLine;
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
            if (userLoc != null) {
                userLocGeo = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
                drawPolyTest();
                //drawPolylineUserLocToMarker();
                findNearestMarker();
            }


        }
    };

    private void findNearestMarker() {
        //nearestMarker to UserLocation
        Location locBxL = new Location("BXL");
        Location geoCoordCb = new Location("COMIC BOOK");
        Location geoCoordSa = new Location("STREET ART");
        locBxL.setLatitude(coordBrussel.latitude);
        locBxL.setLongitude(coordBrussel.longitude);
        //for loop to get every coord
        for (CbArt cbMarker : artViewModel.getAllCbArtFromDataBase()) {
            {
                LatLng cbPos = new LatLng(cbMarker.getLat(), cbMarker.getLng());
                geoCoordCb.setLongitude(cbPos.longitude);
                geoCoordCb.setLatitude(cbPos.latitude);
                Log.d("CBPOS", "findNearestMarker:" + geoCoordCb);
            }

            for (StreetArt saMarker : artViewModel.getAllStreetArtFromDataBase()) {
                LatLng saPost = new LatLng(saMarker.getLat(), saMarker.getLng());
                geoCoordSa.setLatitude(saPost.latitude);
                geoCoordSa.setLongitude(saPost.longitude);
                Log.d("SAPOS", "findNearestMarker: " + geoCoordSa);
            }
            //when user gets in vicinity of less than (distanceinM) m from marker, text of legend should change or Toast should appear.
            //TODO : get info to/from UserLoc;
            Log.d("USERLOCNEARESTMARKER", "" + userLoc);
            if (userLoc != null) {
                float distanceToCb = userLoc.distanceTo(geoCoordCb);
                float distanceToSa = userLoc.distanceTo(geoCoordSa);
                float distanceinM = 100;
                if (distanceToCb < distanceinM) {
                    // cblegend.setText("Cb TEST");
                    Toast.makeText(fragmentActivity, "Distance to :" + cbMarker.getCharacters() + ":" + distanceToCb, Toast.LENGTH_LONG).show();

                }
                if (distanceToSa < distanceinM) {
                    //salegend.setText("Sa TEST");saMarker.getWorkname()
                    Toast.makeText(fragmentActivity, "Distance to " + ":" + distanceToSa, Toast.LENGTH_LONG).show();

                }

            }
        }

    }


    private void drawPolyTest() {
        mMap.addPolyline(new PolylineOptions()
                .color(0xff990000)
                .width(5)
                //TODO: get info for UserLocation :
                .add(userLocGeo)
                // .add(new LatLng(mMap.getMyLocation().getLongitude(), mMap.getMyLocation().getAltitude()))
                .add(coordBrussel));
    }


    private void drawPolylineUserLocToMarker() {
// not functional at the moment, getting ClassCastException.
//TODO: get info for UserLocation :
        MarkerOptions place1 = new MarkerOptions().position(coordBrussel);
        MarkerOptions place2 = new MarkerOptions().position(coordMechelen);
        String urldirections = getUrl(place1.getPosition(), place2.getPosition(), "walking");
        new FetchURL(getContext()).execute(urldirections, "walking");


    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
//Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        //destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //Mode
        String mode = "mode=" + directionMode;
        //Building the parameters to the webservice
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        //output format
        String output = "json";
        //Building the url to the webservice
        String url = "https://maps.googleapis.com/maps/directions" + output + "?" + parameters + "&key=" + getString(R.string.google_api_key_M);
        return url;
    }


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
                Toast.makeText(getActivity(), "Comic Book Route", Toast.LENGTH_SHORT).show();
            }
            if (sa != null) {
                data.putSerializable("passedStreetArt", sa);
                DetailFragment details = DetailFragment.newInstance(data);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_fragment, details)
                        .addToBackStack("BACK")
                        .commit();
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
        Bundle dataFromDetail = getArguments();
        mapView = rootView.findViewById(R.id.mapView);
        cblegend = rootView.findViewById(R.id.leg_tv_cb);
        salegend = rootView.findViewById(R.id.leg_tv_sa);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(onMapReady);
        setHasOptionsMenu(true);
        artViewModel = new ViewModelProvider(getActivity()).get(ArtViewModel.class);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragmentActivity);


        return rootView;
    }

    private void drawUserMarker() {
//        mMap.setMyLocationEnabled(true); << geeft NullPointerException

        if (checkPermissions()) {
            final Task<Location> locationTask = fusedLocationClient.getLastLocation();
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();

                        userLoc.setLongitude(location.getLongitude());
                        userLoc.setLatitude(location.getLatitude());

                        userLocGeo = new LatLng(latitude, longitude);
                        Log.d("USERLOCATION", "onSuccess: " + userLocGeo);

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
                    //mMap.setMyLocationEnabled(true);
                }
                break;


        }
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyLine != null)
            currentPolyLine.remove();
        currentPolyLine = mMap.addPolyline((PolylineOptions) values[0]);
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
        mapView.onStart();
    }
}