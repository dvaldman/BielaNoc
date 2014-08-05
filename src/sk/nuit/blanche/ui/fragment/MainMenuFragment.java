package sk.nuit.blanche.ui.fragment;

import sk.nuit.blanche.R;
import sk.nuit.blanche.customview.GifMovieView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainMenuFragment extends BaseFragment{
	
	public static BaseFragment newInstance(){
    	MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_main, container, false);
		
//		GifMovieView logo = new GifMovieView(getActivity(), getActivity().getResources().openRawResource(R.drawable.bn_logo));
//		
//		container.addView(logo);
		
		return rootView;
	}

}
