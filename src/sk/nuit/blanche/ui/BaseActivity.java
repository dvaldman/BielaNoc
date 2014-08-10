package sk.nuit.blanche.ui;

import java.util.Locale;










import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import sk.nuit.blanche.R;
import sk.nuit.blanche.customview.CustomTextView;
import sk.nuit.blanche.customview.MyTypefaceSpan;
import sk.nuit.blanche.model.ContentHolder;
import sk.nuit.blanche.utils.Log;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class BaseActivity extends SherlockFragmentActivity {
	
	protected Context context = this;
	protected String language;
	public Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(com.actionbarsherlock.view.Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		Log.initialize(true);
		this.language = Locale.getDefault().getLanguage();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		this.menu = menu;
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void setHomeAsBack(String title) {
    	getSupportActionBar().show();
    	getSupportActionBar().setHomeButtonEnabled(true);
    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setDisplayShowCustomEnabled(true);
    	getActionBar().setDisplayShowTitleEnabled(false);

    	LayoutInflater inflator = LayoutInflater.from(this);
    	View v = inflator.inflate(R.layout.actionbar_title, null);

    	CustomTextView ctv = (CustomTextView) v.findViewById(R.id.title);
    	
    	
//    	SpannableString s = new SpannableString(title);
//        s.setSpan(new MyTypefaceSpan(this, "anca_medium.otf"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        
//        // Update the action bar title with the TypefaceSpan instance
//        ctv.setText(s);
    	ctv.setText(title);

    	getActionBar().setCustomView(v);
//       getActionBar().setTitle(s);
    	
    }
	
	public void hideActionBar(){
		getSupportActionBar().hide();
	}
	
	
	public void showErrorDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setTitle(R.string.connection_error_title);
//        alertDialog.setMessage(R.string.connection_error_message);
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	dialog.dismiss();
            	finish();
            }
        });
  
        alertDialog.show();
	}
	
	public String getLanguage(){
		return this.language;
	}
	
	
	
	public void hideKeyboard() {
	    if(getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	    }
	}
	
	public ContentHolder getContentHolder(){
		return ContentHolder.getInstance(this);
	}
	
}
