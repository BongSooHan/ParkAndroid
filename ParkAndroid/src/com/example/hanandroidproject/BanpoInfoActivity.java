package com.example.hanandroidproject;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hanandroidproject.R;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class BanpoInfoActivity extends NMapActivity implements OnMapStateChangeListener, OnMapViewTouchEventListener{
private NMapView mMapView;
private static final String LOG_TAG = "NMAP";
private static final boolean DEBUG = false;
private static final String API_KEY="7bc06a315e8641dc1b290ea44c2e15da";
NMapController mMapController;
LinearLayout MapContainer;
private NMapViewerResourceProvider mMapViewerResourceProvider;
private NMapOverlayManager mOverlayManager;
ArrayList<FacilityDTO> list;

@Override
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_banpoinfo);
	final String requestURL="http://192.168.0.3:8090/HanOracle/facility_select.jsp";
// create map view
	
	Intent intent = getIntent();
	int p_number= intent.getIntExtra("p_number", 6);

mMapView = new NMapView(this);
MapContainer =(LinearLayout)findViewById(R.id.banpoinfomap);
mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
OnCalloutOverlayListener onCalloutOverlayListener = null;
mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
// set a registered API key for Open MapViewer Library
mMapView.setApiKey(API_KEY);

MapContainer.addView(mMapView);
// initialize map view
mMapView.setClickable(true);
mMapView.setOnMapStateChangeListener(this);
mMapView.setOnMapViewTouchEventListener(this);
mMapView.setBuiltInZoomControls(true, null);

mMapController=mMapView.getMapController();

Button infodeskBtn=(Button)findViewById(R.id.infodeskBtn);
Button bikeparkingBtn=(Button)findViewById(R.id.bikeparkingBtn);
Button bikerentalBtn=(Button)findViewById(R.id.bikerentalBtn);
Button waterBtn=(Button)findViewById(R.id.waterBtn);
Button policeBtn=(Button)findViewById(R.id.policeBtn);
	

infodeskBtn.setOnClickListener(new View.OnClickListener() {

		@Override
	public void onClick(View v) {
			InputStream is=Facilityselect.requestGet(requestURL,6,1);
			list = Facilityselect.getXML(requestURL, is);
			
		}
	});

bikeparkingBtn.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(),BanpoInfoActivity.class);
			intent.putExtra("p_number", 6);
			intent.putExtra("f_number", 2);
			startActivity(intent);
			finish();
		}
	});

bikerentalBtn.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getApplicationContext(),BanpoInfoActivity.class);
		intent.putExtra("p_number", 6);
		intent.putExtra("f_number", 3);
		startActivity(intent);
		finish();
	}
});

waterBtn.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getApplicationContext(),BanpoInfoActivity.class);
		intent.putExtra("p_number", 6);
		intent.putExtra("f_number", 4);
		startActivity(intent);
		finish();
	}
});

policeBtn.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getApplicationContext(),BanpoInfoActivity.class);
		intent.putExtra("p_number", 6);
		intent.putExtra("f_number", 5);
		startActivity(intent);
		finish();
	}
});

}



public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
    if (errorInfo == null) { // success
    	// Markers for POI item
    	int markerId = NMapPOIflagType.PIN;

    	// set POI data
    	NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
    	poiData.beginPOIdata(1);
    	poiData.addPOIitem(126.9946625, 37.5094917, "반포한강공원", markerId, 0);
    	poiData.endPOIdata();

    	// create POI data overlay
    	NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

    	// set event listener to the overlay
    	poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

    	// select an item
    	poiDataOverlay.selectPOIitem(0, true);

    	// show all POI data
    	poiDataOverlay.showAllPOIdata(0);
    } else { // fail
            Log.e("NMAP", "onMapInitHandler: error=" + errorInfo.toString());
    }
}    


/* POI data State Change Listener*/
private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

	@Override
	public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
		if (DEBUG) {
			Log.i(LOG_TAG, "onCalloutClick: title=" + item.getTitle());
		}

		// [[TEMP]] handle a click event of the callout
		Toast.makeText(BanpoInfoActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
		if (DEBUG) {
			if (item != null) {
				Log.i(LOG_TAG, "onFocusChanged: " + item.toString());
			} else {
				Log.i(LOG_TAG, "onFocusChanged: ");
			}
		}
	}
};




public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem, Rect itemBounds) {
    // set your callout overlay
    return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);
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


