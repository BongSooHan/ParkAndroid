package com.example.hanandroidproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;

/** 
 * NMapFragment ?΄??€? NMapActivityλ₯? ???μ§? ?κ³? NMapViewλ§? ?¬?©?κ³ μ ?? κ²½μ°? NMapContextλ₯? ?΄?©? ?? ?.
 * NMapView ?¬?©? ??? μ΄κΈ°? λ°? λ¦¬μ€? ?±λ‘μ? NMapActivity ?¬?©??? ??Ό?¨.
 */
public class NMapFragment extends Fragment {

	private NMapContext mMapContext;
	
	/**
	 * Fragment? ?¬?¨? NMapView κ°μ²΄λ₯? λ°ν?¨
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

	/* Fragment ?Ό?΄??¬?΄?΄? ?°?Ό? NMapContext? ?΄?Ή APIλ₯? ?ΈμΆν¨ */
	
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
	    
	    // Fragment? ?¬?¨? NMapView κ°μ²΄ μ°ΎκΈ°
	    NMapView mapView = findMapView(super.getView());
	    if (mapView == null) {
	    	throw new IllegalArgumentException("NMapFragment dose not have an instance of NMapView.");
	    }
	    
	    // NMapActivityλ₯? ???μ§? ?? κ²½μ°?? NMapView κ°μ²΄ ??±? λ°λ? setupMapView()λ₯? ?ΈμΆν΄?Ό?¨.
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
