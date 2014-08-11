package sk.nuit.blanche.adapters;

import java.util.List;

import sk.nuit.blanche.R;
import sk.nuit.blanche.core.DrawableManager;
import sk.nuit.blanche.model.Artist;
import sk.nuit.blanche.model.ArtistsListRow;
import sk.nuit.blanche.utils.Log;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ArtistsAdapter extends BaseAdapter{
	
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	
	private Context context;
	List<ArtistsListRow> artistsRow;
	private DrawableManager drawableManager;
	
	public ArtistsAdapter(Context context, List<ArtistsListRow> artists, DrawableManager dman){
		this.context = context;
		this.artistsRow = artists;
		this.drawableManager = dman;
		Log.i("artist row size "+artists.size());
	}

	@Override
	public int getCount() {
		return this.artistsRow.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        	convertView = inflater.inflate(R.layout.list_row_artists_layout, parent, false);
        	viewHolder = new ViewHolder();
        	viewHolder.title1 = (TextView) convertView.findViewById(R.id.title1);
        	viewHolder.title2 = (TextView) convertView.findViewById(R.id.title2);
            
        	viewHolder.image1 = (ImageView) convertView.findViewById(R.id.artist1);
        	viewHolder.image2 = (ImageView) convertView.findViewById(R.id.artist2);
        	
        	viewHolder.progress1 = (ProgressBar) convertView.findViewById(R.id.progressbar1);
        	viewHolder.progress2 = (ProgressBar) convertView.findViewById(R.id.progressbar2);
        	
        	viewHolder.touch1 = (RelativeLayout) convertView.findViewById(R.id.left);
        	viewHolder.touch2 = (RelativeLayout) convertView.findViewById(R.id.right);
        	
        	convertView.setTag(viewHolder);
    	}else{
    		viewHolder = (ViewHolder) convertView.getTag();
    	}

		viewHolder.title1.setText(artistsRow.get(position).getLeftArtist().getName());
		viewHolder.title2.setText(artistsRow.get(position).getRightArtist().getName());
		
//		viewHolder.image1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img1));
//		viewHolder.image2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img2));
		
		 
	     drawableManager.fetchScaledDrawableOnThread(
//	    		 artistsRow.get(position).getLeftArtist().getImage(),
	    		 "http://lorempixel.com/400/250/",
	    		 artistsRow.get(position).getLeftArtist().getId(),
	    		 viewHolder.image1,viewHolder.progress1, null);
	     
	     drawableManager.fetchScaledDrawableOnThread(
//	    		 artistsRow.get(position).getRightArtist().getImage(),
	    		 "http://lorempixel.com/400/250/",
	    		 artistsRow.get(position).getRightArtist().getId(),
	    		 viewHolder.image2,viewHolder.progress2, null);
		        		
		return convertView;
	}
	
	private class ViewHolder {
        ImageView image1, image2;
        TextView title1, title2;
        RelativeLayout touch1, touch2;
        ProgressBar progress1, progress2;
   }
	
	public int getItemViewType(int position) {
		return (position % 2 == 0) ? LEFT : RIGHT;
	}
	
	

}
