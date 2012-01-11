package feri.rvir.elocator.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabMenuActivity extends TabActivity {
	
	private TabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		mTabHost=getTabHost();
		
		TabSpec spec;
		
		spec=mTabHost.newTabSpec("trackingOverview")
				.setIndicator("Overview",getResources().getDrawable(R.drawable.ic_tab_tracking_overview_icon))
				.setContent(new Intent(this,TrackingOverviewActivity.class));
		mTabHost.addTab(spec);
		
		spec=mTabHost.newTabSpec("trackingDetails")
				.setIndicator("Details",getResources().getDrawable(R.drawable.ic_tab_tracking_details_icon))
				.setContent(new Intent(this,TrackingDetailsActivity.class));
		mTabHost.addTab(spec);
		
	}
}
