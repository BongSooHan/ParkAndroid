package com.example.hanandroidproject;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.hanandroidproject.R;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;

public class BanpoInfoActivity extends NMapActivity implements OnMapStateChangeListener, OnMapViewTouchEventListener{
private NMapView mMapView;
private static final String API_KEY="7bc06a315e8641dc1b290ea44c2e15da";
NMapController mMapController;
LinearLayout MapContainer;

@Override
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_banpoinfo);
// create map view
mMapView = new NMapView(this);
MapContainer =(LinearLayout)findViewById(R.id.banpoinfomap);

// set a registered API key for Open MapViewer Library
mMapView.setApiKey(API_KEY);

MapContainer.addView(mMapView);
// initialize map view
mMapView.setClickable(true);
mMapView.setOnMapStateChangeListener(this);
mMapView.setOnMapViewTouchEventListener(this);
mMapView.setBuiltInZoomControls(true, null);

mMapController=mMapView.getMapController();
}

public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
    if (errorInfo == null) { // success
            mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
    } else { // fail
            Log.e("NMAP", "onMapInitHandler: error=" + errorInfo.toString());
    }
}    
@Override
public void onLongPress(NMapView arg0, MotionEvent arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onLongPressCanceled(NMapView arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onScroll(NMapView arg0, MotionEvent arg1, MotionEvent arg2) {
	// TODO Auto-generated method stub
	
}

@Override
public void onSingleTapUp(NMapView arg0, MotionEvent arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTouchDown(NMapView arg0, MotionEvent arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTouchUp(NMapView arg0, MotionEvent arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub
	
}

@Override
public void onMapCenterChange(NMapView arg0, NGeoPoint arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onMapCenterChangeFine(NMapView arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onZoomLevelChange(NMapView arg0, int arg1) {
	// TODO Auto-generated method stub
	
}
}
