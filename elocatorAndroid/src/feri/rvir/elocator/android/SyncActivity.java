package feri.rvir.elocator.android;

import java.io.File;
import java.io.FileNotFoundException;

import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.rest.resource.user.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class SyncActivity extends Activity {
	
	private SyncActivity thisActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sync);
		thisActivity=this;
		
		TextView signedInAsTextVeiw=(TextView)findViewById(R.id.sync_textViewSignedInAs);
		StringBuilder sb=new StringBuilder();
		sb.append(signedInAsTextVeiw.getText().toString());
		
		Serializer<User> serializer=new Serializer<User>();
		try {
			User u=serializer.unserialize(openFileInput(getString(R.string.user_data_store)));
			sb.append(u.getUsername());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		signedInAsTextVeiw.setText(sb.toString());
		
		Button signOutButton=(Button)findViewById(R.id.sync_button_signOut);
		signOutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File dir=getFilesDir();
				File file=new File(dir,getString(R.string.user_data_store));
				if(file.delete()) {
					ToastCentered.makeText(thisActivity, "Signed out.").show();
					Intent i=new Intent(thisActivity, MainActivity.class);
					startActivity(i);
				} else {
					ToastCentered.makeText(thisActivity, "Sign out failed.").show();
				}

			}
		});
	}

}
