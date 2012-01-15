package feri.rvir.elocator.android.maps;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import feri.rvir.elocator.android.R;

public class TrackingItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private Context mContext;
	private ArrayList<OverlayItem> mOverlays=new ArrayList<OverlayItem>();

	public TrackingItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public TrackingItemizedOverlay(Drawable defaultMarker, Context mContext) {
		super(boundCenterBottom(defaultMarker));
		this.mContext = mContext;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	@Override
	protected boolean onTap(int index) {
		OverlayItem item=mOverlays.get(index);
		Builder dialog=new Builder(mContext);
		dialog.setTitle("User: "+item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.setIcon(R.drawable.ic_dialog);
		dialog.show();
		return true;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false);
	}
	
	public ArrayList<OverlayItem> getOverlayItems() {
		return mOverlays;
	}
}
