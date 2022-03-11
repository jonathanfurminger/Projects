package com.ebookfrenzy.mapdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ebookfrenzy.mapdemo.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 101;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         *
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;

            // Add a marker in Clovelly and move the camera
            LatLng clovelly = new LatLng(50.998128, -4.399118);
            mMap.addMarker(new MarkerOptions().position(clovelly).title("Marker in Clovelly"));

            LatLng fishermanscottage = new LatLng(50.99795648517228, -4.399230743006255);
            mMap.addMarker(new MarkerOptions().position(fishermanscottage).title("FisherMans Cottage in Clovelly"));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(clovelly));
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            // display user controls i.e., zoom in and out buttons, my location button etc
            UiSettings mapSettings;
            mapSettings = mMap.getUiSettings();
            mapSettings.setZoomControlsEnabled(true);
            mapSettings.setScrollGesturesEnabled(true);
            mapSettings.setTiltGesturesEnabled(true);
            mapSettings.setRotateGesturesEnabled(true);

            // zoom in on the map
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(clovelly)
                    .zoom(19)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // listener for Markers
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String markerName = marker.getTitle();
                    //Context context = getContext();

                    Log.i("mapDemo", "MapsFragment " + markerName);
                    //Toast.makeText(context, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                    Log.i("mapDemo", "MapsFragment, onMarkerClick has been called. Just about to call markerClicked.");
                    markerClicked(marker);
                    Log.i("mapDemo", "MapsFragment, onMarkerClick has been called.");
                    return false;
                }
            });

            // following code is to update location to current location, provided user has
            // given permission to do so. Commented out for now as need location to continue to show
            // Clovelly
//        if(mMap != null) {
//            int permission = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION);
//
//            if(permission == PackageManager.PERMISSION_GRANTED) {
//                mMap.setMyLocationEnabled(true);
//            } else {
//                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
//                        LOCATION_REQUEST_CODE);
//            }
//        }

        }


    /**
     * set up a listener that will allow the fragment to call the MainActivity when the marker
     * is clicked
     */
    GoogleMap.OnMarkerClickListener activityCallback;

    public interface OnMarkerClickListener {
        public void onMarkerClick(Marker marker);
    }

    @Override
    public void onAttach(Context context) {
        Log.i("mapDemo", "MapsFragment, onAttach has been called.");
        super.onAttach(context);
        Log.i("mapDemo", "MapsFragment, successfully called super.onAttach from onAttach");
        try {
            Log.i("mapDemo", "MapsFragment, about to set activityCallback in onAttach");
            activityCallback = (GoogleMap.OnMarkerClickListener) context;
            Log.i("mapDemo", "MapsFragment, successfully set activityCallback.");
        } catch (ClassCastException e) {
            Log.i("mapDemo", "MapsFragment, about to throw exception.");
            // throw new ClassCastException(context + " must implement OnMarkerClickListener");
        }
    }

    /**
     * this method is called when the marker is clicked by the user
     */
    public void markerClicked(Marker marker) {
        Log.i("mapDemo", "MapsFragment, markerClicked has been called.");

        activityCallback.onMarkerClick(marker);
    }

    /**
     * Method to add a marker to the map. Takes in a pointOfInterest object
     * @param pointOfInterest
     */
    public void addMarker(PointOfInterest pointOfInterest) {

            double latitude = pointOfInterest.getLatitude();
            double longitude = pointOfInterest.getLongitude();
            String placeTitle = pointOfInterest.getPlaceTitle();
            String placeDescription = pointOfInterest.getPlaceDescription();

            LatLng position = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions()
                    .position(position).title(placeTitle).snippet(placeDescription));
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }
}