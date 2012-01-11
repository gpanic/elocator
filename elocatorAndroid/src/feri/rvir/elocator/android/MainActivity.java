package feri.rvir.elocator.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private boolean signedIn=true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(signedIn) {
        	Intent i=new Intent(this,TabMenuActivity.class);
			startActivity(i);
        }
        
        Button signInButton=(Button)findViewById(R.id.main_buttonSignIn);
        signInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
        
        TextView registerLink=(TextView)findViewById(R.id.main_textViewRegister);
        registerLink.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(v.getContext(),RegistrationActivity.class);
				startActivity(i);
			}
		});
    }
    
}