package feri.rvir.elocator.android;

import java.io.File;
import java.io.FileNotFoundException;

import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.rest.resource.user.User;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {
	
	private PreferenceActivity thisActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thisActivity=this;
		
		StringBuilder sb=new StringBuilder();
		Serializer<User> serializer=new Serializer<User>();
		try {
			User user = serializer.unserialize(openFileInput(getString(R.string.user_data_store)));
			sb.append("Signed in as: ");
			sb.append(user.getUsername());
			sb.append(". ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sb.append("Click here to sign out.");
		
		addPreferencesFromResource(R.xml.preferences);
		
		Preference prefSignOut=(Preference)findPreference("prefSignOut");
		prefSignOut.setSummary(sb.toString());
		prefSignOut.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				File dir=getFilesDir();
				File file=new File(dir,getString(R.string.user_data_store));
				if(file.delete()) {
					ToastCentered.makeText(thisActivity, "Signed out.").show();
					Intent i=new Intent(thisActivity, MainActivity.class);
					startActivity(i);
					return true;
				} else {
					ToastCentered.makeText(thisActivity, "Sign out failed.").show();
					return false;
				}
			}
		});
		
		Preference prefSync=(Preference)findPreference("prefSync");
		prefSync.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				return false;
			}
		});
		
	}

}
