package sk.nuit.blanche.ui.fragment;

import sk.nuit.blanche.R;
import sk.nuit.blanche.customview.GifMovieView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


public class MainMenuFragment extends BaseFragment implements OnClickListener{
	
	
	
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
		
		
		initButtons();
		
		return rootView;
	}
	
	private void initButtons(){
		rootView.findViewById(R.id.menu_btn1).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn2).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn3).setOnClickListener(this);
		
		rootView.findViewById(R.id.menu_btn4).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn5).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn6).setOnClickListener(this);
		
		rootView.findViewById(R.id.menu_btn7).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn8).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn9).setOnClickListener(this);
		
		rootView.findViewById(R.id.menu_btn10).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn11).setOnClickListener(this);
		rootView.findViewById(R.id.menu_btn12).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.menu_btn1:
			switchToFragment(FRAGMENT_ARTISTS, null);
			break;
		case R.id.menu_btn2:
			switchToFragment(FRAGMENT_FRIENDS, null);
			break;
		case R.id.menu_btn3:
			switchToFragment(FRAGMENT_PHOTO, null);
			break;
		case R.id.menu_btn4:
			switchToFragment(FRAGMENT_MAP, null);
			break;
		case R.id.menu_btn5:
			switchToFragment(FRAGMENT_NUIT, null);
			break;
		case R.id.menu_btn6:
			switchToFragment(FRAGMENT_KIDS, null);
			break;
		case R.id.menu_btn7:
			switchToFragment(FRAGMENT_MEDIA, null);
			break;
		case R.id.menu_btn8:
			switchToFragment(FRAGMENT_AUDIO, null);
			break;
		case R.id.menu_btn9:
			switchToFragment(FRAGMENT_DICTIONARY, null);
			break;
		case R.id.menu_btn10:
			switchToFragment(FRAGMENT_PARTNERS, null);
			break;
		case R.id.menu_btn11:
			switchToFragment(FRAGMENT_FACEBOOK, null);
			break;
		case R.id.menu_btn12:
			switchToFragment(FRAGMENT_INSTAGRAM, null);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		
		if(!hidden)hideActionBar();
	}

}
