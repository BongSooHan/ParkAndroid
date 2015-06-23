package com.example.hanandroidproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;

/** 
 * NMapFragment ?´?˜?Š¤?Š” NMapActivityë¥? ?ƒ?†?•˜ì§? ?•Šê³? NMapViewë§? ?‚¬?š©?•˜ê³ ì ?•˜?Š” ê²½ìš°?— NMapContextë¥? ?´?š©?•œ ?˜ˆ? œ?„.
 * NMapView ?‚¬?š©?‹œ ?•„?š”?•œ ì´ˆê¸°?™” ë°? ë¦¬ìŠ¤?„ˆ ?“±ë¡ì? NMapActivity ?‚¬?š©?‹œ?? ?™?¼?•¨.
 */
public class NMapFragment extends Fragment {

	private NMapContext mMapContext;
	
	/**
	 * Fragment?— ?¬?•¨?œ NMapView ê°ì²´ë¥? ë°˜í™˜?•¨
	 */
	private NMapView findMapView(View v) {

	    if (!(v instanceof ViewGroup)) {
	        return null;
	    }

	    ViewGroup vg = (ViewGroup)v;
	    if (vg instanceof NMapView) {
	        return (NMapView)vg;
	    }
	    
	    for (int i = 0; i < vg.getChildCount(); i++) {

	        View child = vg.getChildAt(i);
		    if (!(child instanceof ViewGroup)) {
		        continue;
		    }
		    
		    NMapView mapView = findMapView(child);
		    if (mapView != null) {
		    	return mapView;
		    }
	    }
	    return null;
	}

	/* Fragment ?¼?´?”„?‚¬?´?´?— ?”°?¼?„œ NMapContext?˜ ?•´?‹¹ APIë¥? ?˜¸ì¶œí•¨ */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    mMapContext =  new NMapContext(super.getActivity()); 
	    
	    mMapContext.onCreate();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		throw new IllegalArgumentException("onCreateView should be implemented in the subclass of NMapFragment.");
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    // Fragment?— ?¬?•¨?œ NMapView ê°ì²´ ì°¾ê¸°
	    NMapView mapView = findMapView(super.getView());
	    if (mapView == null) {
	    	throw new IllegalArgumentException("NMapFragment dose not have an instance of NMapView.");
	    }
	    
	    // NMapActivityë¥? ?ƒ?†?•˜ì§? ?•Š?Š” ê²½ìš°?—?Š” NMapView ê°ì²´ ?ƒ?„±?›„ ë°˜ë“œ?‹œ setupMapView()ë¥? ?˜¸ì¶œí•´?•¼?•¨.
	    mMapContext.setupMapView(mapView);
	}
	
	@Override
	public void onStart(){
	    super.onStart();
	    
	    mMapContext.onStart();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    mMapContext.onResume();
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    
	    mMapContext.onPause();
	}
	
	@Override
	public void onStop() {
		
		mMapContext.onStop();
		
	    super.onStop();
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		mMapContext.onDestroy();
		
	    super.onDestroy();
	}
}
