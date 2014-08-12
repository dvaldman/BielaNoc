package sk.nuit.blanche.ui.fragment;


import sk.nuit.blanche.core.DrawableManager;
import sk.nuit.blanche.interfaces.FragmentSwitcherInterface;
import sk.nuit.blanche.model.ContentHolder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import sk.nuit.blanche.ui.MainActivity;
import sk.nuit.blanche.ui.BaseActivity;



public class BaseFragment extends Fragment implements FragmentSwitcherInterface{
	
	public static final String BUNDLE_EVENT_OBJECT = "event_object";
	
	protected View rootView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
    
    protected void showErrorDialog(){
    	((BaseActivity)getActivity()).showErrorDialog();
    }
    
    
    protected void hideKeyboard(){
    	((BaseActivity)getActivity()).hideKeyboard();
    }
    
    public void switchToFragment(int fragmentID, Bundle args){
    	((MainActivity)getActivity()).switchFragment(fragmentID, false, args);
	}
	
	public void switchToFragmentAndClear(int fragmentID, Bundle args){
		((MainActivity)getActivity()).switchFragment(fragmentID, true, args);
	}
	
	public void setHomeAsBack(String title){
		((BaseActivity)getActivity()).setHomeAsBack(title);
	}
	
	public void setUpAsBack(String title){
		((BaseActivity)getActivity()).setHomeAsBack(title);
	}
	
	public void hideActionBar(){
		((BaseActivity)getActivity()).hideActionBar();
	}
	
	public ContentHolder getContentHolder(){
		return ((BaseActivity)getActivity()).getContentHolder();
	}
	
	public DrawableManager getDrawableManager(){
		return ((BaseActivity)getActivity()).getDrawavleManager();
	}
	
}
