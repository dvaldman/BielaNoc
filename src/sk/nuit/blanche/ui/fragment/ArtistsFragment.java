package sk.nuit.blanche.ui.fragment;

import sk.nuit.blanche.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArtistsFragment extends BaseFragment{

	private static String title;
	
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
		
		return rootView;
	}
}
