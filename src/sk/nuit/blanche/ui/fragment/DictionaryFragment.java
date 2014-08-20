package sk.nuit.blanche.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import sk.nuit.blanche.R;
import sk.nuit.blanche.adapters.ArtistsAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class DictionaryFragment extends BaseFragment{

	private static String title;
	
	public static BaseFragment newInstance(String title){
		DictionaryFragment.title = title;
    	DictionaryFragment fragment = new DictionaryFragment();
        return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHomeAsBack(title);
	
		rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
		
		initView();
		
		return rootView;
	}
	
	
	private void initView(){
		
		    
	        WebView webView = (WebView)rootView.findViewById(R.id.webview);
	        
//	        webView.getSettings().setPluginState(PluginState.ON);
//	        webView.getSettings().setJavaScriptEnabled(true);

	        webView.getSettings().setJavaScriptEnabled(true);
	        webView.setWebChromeClient(new WebChromeClient());
	        webView.setBackgroundColor(0x00000000);
	        webView.setScrollbarFadingEnabled(true);
	        webView.setVerticalScrollBarEnabled(false);
	          
	        webView.loadUrl("file:///android_asset/about/dictionary.htm");
	        
//	        if(android.os.Build.VERSION.SDK_INT <= 10)
//	        	webView.setBackgroundColor(0xFF828282);
	        

	}
	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden)
			setHomeAsBack(title);
	}
}
