package sk.nuit.blanche.interfaces;

import android.os.Bundle;

public interface FragmentSwitcherInterface {

	public static final int FRAGMENT_MAIN  		  	= 0;
	
	public static final int FRAGMENT_ARTISTS 	  	= 1;
	public static final int FRAGMENT_FRIENDS  	  	= 2;
	public static final int FRAGMENT_PHOTO  	  	= 3;
	
	public static final int FRAGMENT_MAP  		  	= 4;
	public static final int FRAGMENT_NUIT  		  	= 5;
	public static final int FRAGMENT_KIDS  		  	= 6;
	
	public static final int FRAGMENT_MEDIA  	  	= 7;
	public static final int FRAGMENT_AUDIO  	  	= 8;
	public static final int FRAGMENT_DICTIONARY	  	= 9;
	
	public static final int FRAGMENT_PARTNERS  	  	= 10;
	public static final int FRAGMENT_FACEBOOK  	  	= 11;
	public static final int FRAGMENT_INSTAGRAM 	  	= 12;
	public static final int FRAGMENT_ARTIST_DETAIL	= 13;
//	public static final int FRAGMENT_ABOUT 		  	= 1;
	
	
	
	public void switchToFragment(int fragmentID, Bundle args);
	
	public void switchToFragmentAndClear(int fragmentID, Bundle args);
	
}
