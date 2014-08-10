package sk.nuit.blanche.ui.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import sk.nuit.blanche.R;
import sk.nuit.blanche.core.GPSProvider;
import sk.nuit.blanche.customview.CustomTextView;
import sk.nuit.blanche.parser.DirectionsJSONParser;
import sk.nuit.blanche.utils.Log;
import sk.nuit.blanche.map.PlacesGroup;
import sk.nuit.blanche.model.Artist;

public class MapFragment extends BaseFragment{
	
	private static String title;
	
	public static BaseFragment newInstance(String title){
		MapFragment.title = title;
        MapFragment fragment = new MapFragment();
    	return fragment;
	}
	
	public static final LatLng CITY_CENTER = new LatLng(48.723135, 21.256793);
	public static final int DEFAULT_ZOOM = 13;
    public static boolean isSavedInstance = false;
    public static int lastSection = 1;
    private static View view;
    
    List<LatLng> polyz;
	GPSProvider gpsProvider;
	AlertDialog alert;
   
    GoogleMap map;
    private static Map<Marker, Artist> markerMap = new HashMap<Marker, Artist>();
    
   
    
    private static View infoWindowView;
    
    private static String lastAddress;
    private static GPSProvider gps;
     
    public MapFragment() {
    }
    
    @Override
    public void onPause() {	
    	super.onPause();
    	isSavedInstance = true;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	setHomeAsBack(title);
    	
//        if(gps == null)
//        	gps = ((sk.village.office.ui.BaseActivity)getActivity()).GPSProvider;
    	
    	if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        
    	try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {}
        
        new InitMap().execute();
        
        return view;
    }
    
    private class InitMap extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); 
				
		        try {
					MapsInitializer.initialize(MapFragment.this.getActivity());
				} catch (GooglePlayServicesNotAvailableException e) {}
				
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			setLastMapState();
		}
    	
    }
    
   
    
    private void setLastMapState(){
//    	if(map!=null && isSavedInstance == false) 
//    		lastSection = 1;
        setSelectedMarkers(true);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    }
    
	
    
    private void setUpMap(PlacesGroup markerGroup, List<Artist> places, boolean zoomMap){
        map.setInfoWindowAdapter(infoWindowAdapter);
        map.setOnInfoWindowClickListener(new InfoWindowClick());
        map.setOnMarkerClickListener(markerClick);
        map.setMyLocationEnabled(true);
        markerMap = markerGroup.addItemsToMap(map,places,getActivity());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(CITY_CENTER, DEFAULT_ZOOM);
        if(zoomMap)
        	map.animateCamera(cameraUpdate);
    }
    
    public final OnMarkerClickListener markerClick = new OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			
			lastAddress = null;
//			Projection projection = map.getProjection();
            LatLng latLng = marker.getPosition();
//            Point point = projection.toScreenLocation(latLng);
//            int offsetX = 0;//getActivity().getResources().getDimensionPixelSize(R.dimen.map_marker_offset_x);
//            int offsetY = 0;//getActivity().getResources().getDimensionPixelSize(R.dimen.map_marker_offset_y);
//            Point point2 = new Point(point.x+offsetX,point.y-offsetY);
//            LatLng point3 = projection.fromScreenLocation(point2);
//            CameraUpdate zoom1 = CameraUpdateFactory.newLatLng(point3);
//            map.animateCamera(zoom1);
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            marker.showInfoWindow();
			return true;
		}
	};
    
    public InfoWindowAdapter infoWindowAdapter = new InfoWindowAdapter() {
    	
    	
		@Override
		public View getInfoWindow(final Marker marker) {
	      try{  
			if (infoWindowView != null) {
	            ViewGroup parent = (ViewGroup) infoWindowView.getParent();
	            if (parent != null)
	                parent.removeView(infoWindowView);
	        }
	        infoWindowView = getLayoutInflater(getArguments()).inflate(R.layout.place_detail_layout, null);
	        Artist item = markerMap.get(marker);
			CustomTextView title = (CustomTextView) infoWindowView.findViewById(R.id.title);
			CustomTextView desc = (CustomTextView) infoWindowView.findViewById(R.id.description);

			desc.setText(item.getWork());
			title.setText(item.getName());
			
	      }catch(Exception e){Log.i("exception "+e.getMessage());}
	      
			return infoWindowView;
		}
		
		@Override
		public View getInfoContents(Marker marker) {
			return null;
		}
		
	};
	
	private class AddressCallback implements Callback{
	
		private Marker marker;
	
		public AddressCallback(Marker mark){
			this.marker = mark;
		}
	
		@Override
		public boolean handleMessage(Message msg) {
//			switch (msg.what) {
//			case Constants.MESSAGE_GET_ADDRESS:
//				marker.showInfoWindow();
//				lastAddress = (String) msg.obj;
//				return true;
//	
//			}
			return false;
		}
		
	}
	
		public class InfoWindowClick implements OnInfoWindowClickListener {

		  @Override
		  public void onInfoWindowClick(Marker marker) {
//		   marker.hideInfoWindow();
//		   
//		   
//		   gps.getLocation();
//		   LatLng fromPosition = gps.getLatLng();
//		   LatLng toPosition = marker.getPosition();
//		   map.clear();
//		   setSelectedMarkers(false);
//		   
//		   if(ConnectionChecker.isOnline(getActivity())){
//			   String url = Util.getDirectionsUrl(fromPosition, toPosition);
//			   DownloadTask downloadTask = new DownloadTask();
//			   downloadTask.execute(url);
//		   }
//		   else
//			   showErrorDialog();
		   
		  }
		  
		 }

	
	
	private void setSelectedMarkers(boolean zoomMap){
//		switch (lastSection) {
//		case 1:
			if(map!=null) setUpMap(new PlacesGroup(),getContentHolder().getArtists(),zoomMap);
//			break;
		
	}
	
	
	private class DownloadTask extends AsyncTask<String, Void, String>{			
		
		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {
				
			// For storing data from web service
			String data = "";
					
			try{
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			}catch(Exception e){
				
			}
			return data;		
		}
		
		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);			
			
			ParserTask parserTask = new ParserTask();
			
			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
				
		}		
	}
	
	/** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
    	
    	// Parsing the data in non-ui thread    	
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			
			JSONObject jObject;	
			List<List<HashMap<String, String>>> routes = null;			           
            
            try{
            	jObject = new JSONObject(jsonData[0]);
            	DirectionsJSONParser parser = new DirectionsJSONParser();
            	
            	// Starts parsing data
            	routes = parser.parse(jObject);    
            }catch(Exception e){
            	e.printStackTrace();
            }
            return routes;
		}
		
		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			
			
			// Traversing through all the routes
			if(result == null)
				showErrorDialog();
			else
				for(int i=0;i<result.size();i++){
					points = new ArrayList<LatLng>();
					lineOptions = new PolylineOptions();
					
					// Fetching i-th route
					List<HashMap<String, String>> path = result.get(i);
					
					// Fetching all the points in i-th route
					for(int j=0;j<path.size();j++){
						HashMap<String,String> point = path.get(j);					
						
						double lat = Double.parseDouble(point.get("lat"));
						double lng = Double.parseDouble(point.get("lng"));
						LatLng position = new LatLng(lat, lng);	
						
						points.add(position);						
					}
					
					// Adding all the points in the route to LineOptions
					lineOptions.addAll(points);
					lineOptions.width(5);
					lineOptions.color(Color.RED);	
					
				}
				
				// Drawing polyline in the Google Map for the i-th route
				map.addPolyline(lineOptions);							
			}			
    }   

    protected void showErrorDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//        alertDialog.setTitle(R.string.error);
//        alertDialog.setMessage(R.string.not_online);
//        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog,int which) {
//            	dialog.dismiss();
//            }
//        });
//        alertDialog.show();
	}
    
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url 
                urlConnection.connect();

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }
                
                data = sb.toString();

                br.close();

        }catch(Exception e){
                
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }
        return data;
     }

	
}
