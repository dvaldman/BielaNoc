package sk.nuit.blanche.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import sk.nuit.blanche.R;
import sk.nuit.blanche.adapters.ArtistsAdapter;
import sk.nuit.blanche.core.DrawableManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import sk.nuit.blanche.model.Artist;
import sk.nuit.blanche.model.ArtistsListRow;
import sk.nuit.blanche.utils.Log;

public class ArtistDetailFragment extends BaseFragment implements OnClickListener{

	private static String title;
	private Artist artist;
	private DrawableManager drawableMng;
	
	public static final String ARTIST_OBJECT = "artist_id_key";
	
	public static BaseFragment newInstance(Bundle args){
		ArtistDetailFragment fragment = new ArtistDetailFragment();
    	fragment.setArguments(args);
        return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		artist = (Artist) getArguments().getSerializable(ARTIST_OBJECT);
		title = artist.getName();
		
		setHomeAsBack(title);
	
		drawableMng = getDrawableManager();
		
		rootView = inflater.inflate(R.layout.fragment_artist_detail, container, false);
		
		initContent();
		
		return rootView;
	}
	
	
	private void initContent(){
		drawableMng.addImageToItemLeft(artist.getImage(), artist.getId(), (ImageView) rootView.findViewById(R.id.image), null);
		((TextView) rootView.findViewById(R.id.name)).setText(artist.getWork());
		((TextView) rootView.findViewById(R.id.type)).setText(artist.getType());
		((TextView) rootView.findViewById(R.id.place)).setText(artist.getPlace());
		((TextView) rootView.findViewById(R.id.about_install)).setText(artist.getDescWork());
		((TextView) rootView.findViewById(R.id.about_artist)).setText(artist.getDescArtist());
		
		((ImageView) rootView.findViewById(R.id.button)).setOnClickListener(this);
	}
	
	private List<ArtistsListRow> prepareArtistsList(){
		List<ArtistsListRow> tmp = new ArrayList<ArtistsListRow>();
		List<Artist> arts = getContentHolder().getArtists();

		for(int i = 0; i < arts.size();i++){
			ArtistsListRow row = new ArtistsListRow();
			row.setLeftArtist(arts.get(i));
			if(i++ < arts.size())
				row.setRightArtist(arts.get(i));
			
			tmp.add(row);
		}
		

		return tmp;
	}

	@Override
	public void onClick(View v) {
		Log.i("biiiitch");
	}
}
