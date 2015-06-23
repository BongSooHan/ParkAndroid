package com.example.hanandroidproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class Facilityselect {

	public static InputStream requestGet(String requestURL, int a, int b) {
	     
        try {
             int p_number=a;
             int f_number=b;
        //1.
            HttpClient client = new DefaultHttpClient();
             
            List<NameValuePair> dataList = new ArrayList<NameValuePair>();
            dataList.add(new BasicNameValuePair("p_number",""+p_number));
            dataList.add(new BasicNameValuePair("f_number",""+f_number));

            requestURL=requestURL+"?"+URLEncodedUtils.format(dataList, "UTF-8");
           
            HttpGet get = new HttpGet(requestURL);
             
            //2. 요청
			HttpResponse response = client.execute(get);
			
            //3. 결과 받기
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            
            return is;
             
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//end requestGet()
    
  	
    public static ArrayList<FacilityDTO> getXML(String requestURL,InputStream is) {
    	ArrayList<FacilityDTO> list = new ArrayList<FacilityDTO>();
    	Log.i("xxx", "getXML start!");
    	
    	URL text = null;
		try {
			text = new URL(requestURL);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
    		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    		XmlPullParser parser = factory.newPullParser();
			
			parser.setInput(text.openStream(),"UTF-8");
			
			int eventType = parser.getEventType();
			
			FacilityDTO dto = null;
			
			while( eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType) {
				
				case XmlPullParser.START_TAG:
					String startTag = parser.getName();
					Log.d("123","12312412341");
					if(startTag.equals("record")){Log.d("123","ddddddddddddd");	dto = new FacilityDTO(); }
					
					if(dto != null ) {
						if(startTag.equals("p_number")){ dto.setP_number(Integer.parseInt(parser.nextText())); }
						if(startTag.equals("f_number")){ dto.setF_number(Integer.parseInt(parser.nextText())); }
						if(startTag.equals("lon")){ dto.setLon(Double.parseDouble(parser.nextText())); }
						if(startTag.equals("lat")){ dto.setLat(Double.parseDouble(parser.nextText())); }
					} else { Log.i("xxx", "dto = null"); }
					break;
					
				case XmlPullParser.END_TAG:
					
					String endTag = parser.getName();
					if(endTag.equals("record")){ 

						list.add(dto);
					}
				}//end switch
				
				eventType = parser.next();
				
			}//end while
			
/*			 for( FacilityDTO xx : list){
		        	Log.i("xxx",xx.getP_number+" "+xx.getF_number+" "+xx.getLon()+" "+xx.getLat());
		        }*/
			 
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		}
    	return list;
    }
    
}
