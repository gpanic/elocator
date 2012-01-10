package feri.rvir.elocator.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ElocatorAndroidActivity extends Activity {
	private boolean signedIn=false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(signedIn) {
        	findViewById(R.id.linearLayout2).setVisibility(View.INVISIBLE);
        }
        
        Button signInButton=(Button)findViewById(R.id.buttonSignIn);
        signInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
        
        TextView registerLink=(TextView)findViewById(R.id.textViewRegister);
        registerLink.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(v.getContext(),RegisterActivity.class);
				startActivity(i);
			}
		});
    }
    
}