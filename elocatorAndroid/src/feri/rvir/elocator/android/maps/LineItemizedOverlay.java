package feri.rvir.elocator.android.maps;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

import feri.rvir.elocator.android.R;

public class LineItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private Context mContext;
	private ArrayList<OverlayItem> mOverlays=new ArrayList<OverlayItem>();
	
	public LineItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public LineItemizedOverlay(Drawable defaultMarker, Context mContext) {
		super(boundCenterBottom(defaultMarker));
		this.mContext = mContext;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		ArrayList<GeoPoint> geoPoints=new ArrayList<GeoPoint>();
		for(OverlayItem oi:mOverlays) {
			geoPoints.add(oi.getPoint());
		}
		if(geoPoints.size()>=2) {
			Projection projection=mapView.getProjection();
			
			Paint paint=new Paint();
			paint.setDither(true);
			paint.setColor(Color.rgb(0, 61, 92));
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeWidth(5);
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			
			ArrayList<Point> points=new ArrayList<Point>();
			
			for(GeoPoint gp:geoPoints) {
				Point p=new Point();
				projection.toPixels(gp, p);
				points.add(p);
			}
			
			Path path=new Path();
			path.moveTo(points.get(0).x, points.get(0).y);
			for(int i=1;i<points.size();i++) {
				Point p=points.get(i);
				path.lineTo(p.x, p.y);
			}
			canvas.drawPath(path, paint);
		}
		super.draw(canvas, mapView, false);
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
	public boolean onTap(int index) {
		OverlayItem item=mOverlays.get(index);
		Builder dialog=new Builder(mContext);
		dialog.setTitle("User: "+item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.setIcon(R.drawable.ic_dialog);
		dialog.show();
		return true;
	}
	
	public ArrayList<OverlayItem> getOverlayItems() {
		return mOverlays;
	}
 
}
