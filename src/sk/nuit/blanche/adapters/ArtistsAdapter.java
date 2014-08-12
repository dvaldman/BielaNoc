package sk.nuit.blanche.adapters;

import java.util.List;

import sk.nuit.blanche.R;
import sk.nuit.blanche.core.DrawableManager;
import sk.nuit.blanche.interfaces.FragmentSwitcherInterface;
import sk.nuit.blanche.model.Artist;
import sk.nuit.blanche.model.ArtistsListRow;
import sk.nuit.blanche.ui.MainActivity;
import sk.nuit.blanche.ui.fragment.ArtistDetailFragment;
import sk.nuit.blanche.utils.Log;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ArtistsAdapter extends BaseAdapter implements FragmentSwitcherInterface{
	
	private final static int LEFT = 0;
	private final static int RIGHT = 1;
	
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
	public View getView(final int position, View convertView, ViewGroup parent) {
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
		
		 
	     drawableManager.addImageToItemLeft(
	    		 artistsRow.get(position).getLeftArtist().getImage(),
	    		 artistsRow.get(position).getLeftArtist().getId(),
	    		 viewHolder.image1,viewHolder.progress1);
		
	     drawableManager.addImageToItemRight(
	    		 artistsRow.get(position).getRightArtist().getImage(),
	    		 artistsRow.get(position).getRightArtist().getId(),
	    		 viewHolder.image2,viewHolder.progress2);
	     
	     viewHolder.touch1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle args= new Bundle();
				args.putSerializable(ArtistDetailFragment.ARTIST_OBJECT, artistsRow.get(position).getLeftArtist());
				switchToFragment(FRAGMENT_ARTIST_DETAIL, args);
			}
		});
	     
	     viewHolder.touch2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle args= new Bundle();
					args.putSerializable(ArtistDetailFragment.ARTIST_OBJECT, artistsRow.get(position).getRightArtist());
					switchToFragment(FRAGMENT_ARTIST_DETAIL, args);
				}
			});

	     
		return convertView;
	}
	
	private class ViewHolder {
        ImageView image1, image2;
        TextView title1, title2;
        RelativeLayout touch1, touch2;
        ProgressBar progress1, progress2;
   }

	@Override
	public void switchToFragment(int fragmentID, Bundle args) {
		((MainActivity)context).switchFragment(fragmentID, false, args);
	}

	@Override
	public void switchToFragmentAndClear(int fragmentID, Bundle args) {
		// TODO Auto-generated method stub
		
	}
	
	

	

}
