package sk.nuit.blanche.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.nuit.blanche.R;
import sk.nuit.blanche.model.Artist;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlacesGroup {

	public Map<Marker, Artist> addItemsToMap(GoogleMap map,List<Artist> places,Context context) {
		map.clear();
		Map<Marker, Artist> markerMap = new HashMap<Marker, Artist>();
		Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.marker);
        for (Artist item : places){
        	if(item.getGps()!=null) {
        		MarkerOptions markerOptions = new MarkerOptions()
        		.position(item.getGps())
        		.title(item.getName())
        		.icon(BitmapDescriptorFactory.fromBitmap(icon))
        		.snippet(item.getDesc());
        		
        		Marker marker = map.addMarker(markerOptions);
        		markerMap.put(marker, item);
        	}
        }
        
        return markerMap;
	}
	
}
