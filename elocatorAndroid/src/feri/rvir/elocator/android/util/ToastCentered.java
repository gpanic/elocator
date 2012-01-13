package feri.rvir.elocator.android.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastCentered {
	
	public static Toast makeText(Context context, String message) {
		Toast t=Toast.makeText(context, message, Toast.LENGTH_SHORT);
		t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		return t;
	}

}
