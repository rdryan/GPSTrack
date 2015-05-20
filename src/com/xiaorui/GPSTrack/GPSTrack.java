package com.xiaorui.GPSTrack;

import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;


public class GPSTrack extends Activity {
	/** Called when the activity is first created. */	
	private  FileWriter fw;
	private  LocationManager myManager;   	
	private  String provider = LocationManager.GPS_PROVIDER;
	
	private final LocationListener locationListener=new LocationListener(){

		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub		
			Log.d("MyLog","onLocationChanged (a)");
			
			Location loc = myManager.getLastKnownLocation(provider);
    		LocationTextUpdate(loc);
		}

		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub			
		}

		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub			
		}

		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub	
			Log.d("MyLog","onStatusChanged (d)");
		}};
		
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d("MyLog","onCreate(1)");
        
        
        
       	//final LocationManager myManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
       	myManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
       	//myManager.setTestProviderEnabled(provider, true);
       	
        // 判断GPS是否正常启动  
        if (!myManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
            Toast.makeText(this, "请开启GPS...", Toast.LENGTH_SHORT).show();  
            // 返回开启GPS导航设置界面  
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
            startActivityForResult(intent, 0);  
            return;  
        }  
       	
    	//Double latPoint = myManager.getCurrentLocation("gps").getLatitude();
    	//Double lngPoint = myManager.getCurrentLocation("gps").getLongitude();
    	
    	// 查找到服务信息        
    	//Criteria criteria = new Criteria();        
    	//criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度        
    	//criteria.setAltitudeRequired(false);        
    	//criteria.setBearingRequired(false);        
    	//criteria.setCostAllowed(true);        
    	//criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗        
    	//final String provider = myManager.getBestProvider(criteria, true); // 获取GPS信息        
    	//Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置        
   
        myManager.requestLocationUpdates(provider, 3000, 10, locationListener);  	
    	
        final Button gpsButton = (Button) findViewById(R.id.gpsButton);
        gpsButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v){
            	final Location loc = myManager.getLastKnownLocation(provider);
            	Log.d("MyLog","onButton created (2)");
        		LocationTextUpdate(loc);
        	}});
    }
    
    public void LocationTextUpdate(Location loc){ // throws IOException{
    	TextView latText = (TextView) findViewById(R.id.latText);
    	TextView lngText = (TextView) findViewById(R.id.lngText);
    	TextView altText = (TextView) findViewById(R.id.altText);
    	    	
   		Double latPoint;
		Double lngPoint;
		Double altPoint;
		
    	if (loc != null)
    	{	
    		latPoint = loc.getLatitude();
    		lngPoint = loc.getLongitude();
    		altPoint = loc.getAltitude();
        	latText.setText(latPoint.toString());
        	lngText.setText(lngPoint.toString());
        	altText.setText(altPoint.toString());
        	//String str=String.format("lat:%e,lng:%e\n", latPoint,lngPoint);
        	
        	//try {
			//	fw.write("haha\n");
			//} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}  
			
    	}
    	else
    	{
    		//LocationListener locationListener;
			//myManager.requestLocationUpdates(provider, 3000, 10, locationListener);
    		//latPoint = 0.0;
    		//lngPoint = 0.0;
    		latText.setText("Not Ready yet!");
    		lngText.setText("Not Ready yet!");
    		altText.setText("Not Ready yet!");
    	}
      	
    }
       
}