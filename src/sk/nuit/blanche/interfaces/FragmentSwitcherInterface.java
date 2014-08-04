package sk.nuit.blanche.interfaces;

import android.os.Bundle;

public interface FragmentSwitcherInterface {

	public static final int FRAGMENT_MAIN  		  	= 0;
	public static final int FRAGMENT_ABOUT 		  	= 1;
	
	
	
	public void switchToFragment(int fragmentID, Bundle args);
	
	public void switchToFragmentAndClear(int fragmentID, Bundle args);
	
}
