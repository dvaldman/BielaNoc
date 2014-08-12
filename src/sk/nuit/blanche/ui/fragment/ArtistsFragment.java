package sk.nuit.blanche.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import sk.nuit.blanche.R;
import sk.nuit.blanche.adapters.ArtistsAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import sk.nuit.blanche.model.Artist;
import sk.nuit.blanche.model.ArtistsListRow;

public class ArtistsFragment extends BaseFragment{

	private static String title;
	private ListView listView;
	
	public static BaseFragment newInstance(String title){
		ArtistsFragment.title = title;
    	ArtistsFragment fragment = new ArtistsFragment();
        return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHomeAsBack(title);
	
		rootView = inflater.inflate(R.layout.fragment_artists, container, false);
		
		initList();
		
		return rootView;
	}
	
	
	private void initList(){
		listView = (ListView) rootView.findViewById(R.id.artists_list);
		listView.setAdapter(new ArtistsAdapter(getActivity(), prepareArtistsList(),getDrawableManager()));
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
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden)
			setHomeAsBack(title);
	}
}
