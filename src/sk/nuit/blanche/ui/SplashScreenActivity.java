package sk.nuit.blanche.ui;

import org.json.JSONException;

import sk.nuit.blanche.R;
import sk.nuit.blanche.db.DBHelper;
import sk.nuit.blanche.db.Tables;
import sk.nuit.blanche.net.DownloadUtils.Callback;
import sk.nuit.blanche.service.DownloadService;
import sk.nuit.blanche.utils.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;


public class SplashScreenActivity extends BaseActivity {
	public static final int SPLASH_DELAY = 1000;
	private ProgressBar progressBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        
        try {
			new DownloadService().getArtists(this, callback);
		} catch (JSONException e) {
			Log.e(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private void startAppDelayed(){
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startApp();
			}
		}, SPLASH_DELAY);
	}
	
	private void startApp(){
		startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
		finish();
	}
	
	
	Callback callback = new Callback() {
		
		@Override
		public void onSuccess(Bundle data) {
			progressBar.setVisibility(View.INVISIBLE);
			startApp();
		}
		
		
		@Override
		public void onStarted() {
			progressBar.setVisibility(View.VISIBLE);
		}
		
		@Override
		public void onException() {
			Log.i("exception");
			progressBar.setVisibility(View.INVISIBLE);
			showErrorDialog();
		}
		
		@Override
		public void onError(int code, String message) {
			Log.e("ERROR UPDATING CONFIGURATION. CODE: "+code+"   MESSAGE: "+message);

			
			progressBar.setVisibility(View.INVISIBLE);
			boolean hasData = DBHelper.getInstance(SplashScreenActivity.this).hasTableData(Tables.Artists.TABLE_NAME);
			if(hasData)
				startApp();
			else
				showErrorDialog();
		}
	};
	
	
}
