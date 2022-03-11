package com.ebookfrenzy.mapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnMarkerClickListener {

    // TODO when device rotation noticed, if now in landscape, change constraints to match, but if in portrait change back

    // TODO place markers on map using SQL database

    private PointOfInterest fishermansCottage = new PointOfInterest(50.99795648517228,
            -4.399230743006255, "Fisherman's Cottage", "Here is" +
            "some history about the cottage.");
    private PointOfInterest redLionHotel = new PointOfInterest(50.99907263622343,
            -4.397884284339087, "Red Lion Hotel", "The Red Lion" +
            " Hotel is an 18th Century 4-star Inn that stands on the quay alongside Clovelly’s " +
            "ancient harbour in North Devon.");

    private PointOfInterest[] pointsOfInterest = {fishermansCottage, redLionHotel};

//    void addPointsOfInterest() {
//        for(int i = 0; i < pointsOfInterest.length; i++) {
//
//            MapsFragment mapFragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
//
//            mapFragment.addMarker(pointsOfInterest[i]);
//
//        }
//    }

    /**
     * obtain a reference to the fragment_text instance and call the changeText() method on the object
     * @param marker
     */
    public void onMarkerClick(Marker marker) {
        Log.i("mapDemo", "MainActivity, onMarkerClick has been called");
        TextFragment textFragment = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.text_fragment);
        textFragment.changeText(marker.getTitle());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        addPointsOfInterest();

    }
}