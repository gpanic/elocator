package feri.rvir.elocator.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TrackingDetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv=new TextView(this);
		tv.setText("Tracking Details Tab");
		setContentView(tv);
	}

}
