package com.example.hanandroidproject;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hanandroidproject.R;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapItemizedOverlay;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class BanpoInfoActivity extends NMapActivity implements
		OnMapStateChangeListener, OnMapViewTouchEventListener {
	private NMapView mMapView;
	private NMapError mMapError;
	private static final String LOG_TAG = "NMAP";
	private static final boolean DEBUG = false;
	private static final String API_KEY = "7bc06a315e8641dc1b290ea44c2e15da";



	NMapController mMapController;
	LinearLayout MapContainer;
	private NMapViewerResourceProvider mMapViewerResourceProvider;
	private NMapOverlayManager mOverlayManager;
	ArrayList<FacilityDTO> list;
	BackgroundTask task,task1,task2,task3,task4;
	String requestURL=null;
	int a,b;
	int size = 0;
	String c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banpoinfo);
		// create map view

		Intent intent = getIntent();
		int p_number = intent.getIntExtra("p_number", 6);

		mMapView = new NMapView(this);
		MapContainer = (LinearLayout) findViewById(R.id.banpoinfomap);
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

		mMapController = mMapView.getMapController();

		Button infodeskBtn = (Button) findViewById(R.id.infodeskBtn);
		Button bikeparkingBtn = (Button) findViewById(R.id.bikeparkingBtn);
		Button bikerentalBtn = (Button) findViewById(R.id.bikerentalBtn);
		Button waterBtn = (Button) findViewById(R.id.waterBtn);
		Button policeBtn = (Button) findViewById(R.id.policeBtn);
		
		
		infodeskBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				a = 6;
				b = 1;
				c = "안내소";
				task = new BackgroundTask();
				task.execute();
				
			}
		});

		bikeparkingBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				a = 6;
				b = 2;
				c = "자전거 보관소";
				task1 = new BackgroundTask();
				task1.execute();
				onMapInitHandler(mMapView, mMapError);
			}
		});

		bikerentalBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				a = 6;
				b = 3;
				c = "자전거 대여소";
				task2 = new BackgroundTask();
				task2.execute();
				onMapInitHandler(mMapView, mMapError);
			}
		});

		waterBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				a = 6;
				b = 4;
				c = "식수대";
				task3 = new BackgroundTask();
				task3.execute();
				onMapInitHandler(mMapView, mMapError);
			}
		});

		policeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				a = 5;
				b = 5;
				c = "경찰서";
				task4 = new BackgroundTask();
				task4.execute();
				onMapInitHandler(mMapView, mMapError);
			}
		});

	}
	//////////////
	class BackgroundTask extends AsyncTask<String, String, ArrayList<FacilityDTO>>{
		
		protected ArrayList<FacilityDTO> doInBackground(String ... value){
			requestURL = "http://192.168.0.3:8090/HanOracle/test/facility_select.jsp";
			InputStream is = Facilityselect.requestGet(requestURL, a, b);
			requestURL = "http://192.168.0.3:8090/HanOracle/test/facility_select.jsp?p_number="+a+"&f_number="+b;
			Log.d("url",requestURL);
			list = Facilityselect.getXML(requestURL, is);
			Log.d("eee","index 0 :::"+list.get(0));
			Log.d("eee", "sdf:::"+list.size());
			size = list.size();
			return list;
		}
		
		 protected void onPostExecute(ArrayList<FacilityDTO> result) {
		       super.onPreExecute(); 
		       
		       onMapInitHandler(mMapView, mMapError);
		 }
		//protected void onPostExecute(ArrayList<FacilityDTO> list){	}
		
	}

	
	public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
		if (errorInfo == null) { // success
			// Markers for POI item
			int markerId = NMapPOIflagType.PIN;
			NMapPOIdata poiData = null;
			mapView.clearAnimation();
			// set POI data
			if (size==0) {
				Log.d("list","nulllll");
				poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
				poiData.beginPOIdata(1);
				poiData.addPOIitem(126.9946625, 37.5094917, "반포한강공원", markerId,0);
				poiData.endPOIdata();
			}


			if (size!=0) {
				Log.d("list","not nulllll");
				mOverlayManager.clearOverlays();
				poiData = new NMapPOIdata(list.size(),mMapViewerResourceProvider);
				poiData.beginPOIdata(list.size());
				for (int i = 0; i < list.size(); i++) {
					poiData.addPOIitem(list.get(i).getLon(), list.get(i).getLat(), c+i, markerId, 0);
				}
				poiData.endPOIdata();
			}
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

	/* POI data State Change Listener */
	private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

		@Override
		public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay,
				NMapPOIitem item) {
			if (DEBUG) {
				Log.i(LOG_TAG, "onCalloutClick: title=" + item.getTitle());
			}

			// [[TEMP]] handle a click event of the callout
			Toast.makeText(BanpoInfoActivity.this,"onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay,
				NMapPOIitem item) {
			if (DEBUG) {
				if (item != null) {
					Log.i(LOG_TAG, "onFocusChanged: " + item.toString());
				} else {
					Log.i(LOG_TAG, "onFocusChanged: ");
				}
			}
		}
	};
	

	public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay,
			NMapOverlayItem overlayItem, Rect itemBounds) {
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
