package sk.nuit.blanche.ui;

import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;


public class BaseActivity extends FragmentActivity {
	
	protected Context context = this;
	protected String language;
	public Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.language = Locale.getDefault().getLanguage();
		
	}
	
	public void refresh(String params) {
		
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
}
