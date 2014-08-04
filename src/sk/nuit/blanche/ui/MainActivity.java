package sk.nuit.blanche.ui;



import java.util.LinkedList;
import java.util.NoSuchElementException;

import sk.nuit.blanche.R;
import sk.nuit.blanche.interfaces.FragmentSwitcherInterface;
import sk.nuit.blanche.ui.fragment.BaseFragment;
import sk.nuit.blanche.ui.fragment.MainMenuFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends BaseActivity implements FragmentSwitcherInterface{
	
	
	public boolean isTablet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setContentView(R.layout.activity_main);
		switchToFragment(FRAGMENT_MAIN, null);
		
		isTablet = getResources().getBoolean(R.bool.tablet);
	}


	public void switchToFragment(int fragmentID, Bundle args){
		switchFragment(fragmentID, false, args);
	}
	
	public void switchToFragmentAndClear(int fragmentID, Bundle args){
		switchFragment(fragmentID, true, args);
	}
	
	public void switchFragment(int fragmentID, boolean clearBackStack, Bundle args){
		switch (fragmentID) {
		
		case FRAGMENT_ABOUT:
//			switchFragment(AboutFragment.newInstance(args), clearBackStack);
			break;
		case FRAGMENT_MAIN:
			switchFragment(MainMenuFragment.newInstance(), clearBackStack);
			break;
		
		}
		
	}
	
	private void switchFragment(BaseFragment fragment, boolean clearBackStack){
		
		
		FragmentTransaction transaction = getSupportFragmentManager()
	                .beginTransaction();
		try {
            Fragment top = fragmentBackStack.getLast();
                transaction.hide(top);
        } catch (NoSuchElementException e) {
            // no element in stack
        }
		
		if(clearBackStack) {
			for(Fragment f: fragmentBackStack) {
				 transaction.remove(f);
			}
			fragmentBackStack.clear();
			transaction.commit();
			transaction = getSupportFragmentManager()
				 .beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}
		transaction
			.add(R.id.content, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("bckstck").commit();
		fragmentBackStack.add(fragment);
		
	}

		
	@Override
	public void onBackPressed() {
		if(fragmentBackStack.size() == 1) {
			finish();
		}
		else
			showTopBackStackFragment();
	}
		
	LinkedList<BaseFragment> fragmentBackStack = new LinkedList<BaseFragment>();
	    
	/**
	 * Replaces fragment on top of fragment content view
	 * @param fragment fragment to show
	 * @param clearBackStack clear fragment back stack
	 */
	   
	public void showTopBackStackFragment() {
		hideKeyboard();
		try {
	        	
			// fragment on top - remove this fragment
			BaseFragment top = fragmentBackStack.getLast();
			fragmentBackStack.removeLast();
	             
			// second fragment, show this one
			BaseFragment toShow = fragmentBackStack.getLast();
	            
			getSupportFragmentManager()
				.beginTransaction()
				.remove(top)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
				.show(toShow)
				.commit();
	            
		} catch (NoSuchElementException e) {
			// no element in stack
		}
	}
	     
	public void clearFragmentBackStack() {
		fragmentBackStack.clear();
	}

}
