package feri.rvir.elocator.android;

import java.io.FileNotFoundException;

import feri.rvir.elocator.android.util.Crypto;
import feri.rvir.elocator.android.util.SignInTask;
import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.android.util.Serializer;
import feri.rvir.elocator.rest.resource.user.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private MainActivity thisActivity;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        thisActivity=this;
        
        if(isSignedIn()) {
        	Intent i=new Intent(thisActivity, TabMenuActivity.class);
        	startActivity(i);
        } else {
        	
	        Button signInButton=(Button)findViewById(R.id.main_buttonSignIn);
	        signInButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					EditText usernameEditText=(EditText)findViewById(R.id.main_editTextUsername);
					EditText passwordEditText=(EditText)findViewById(R.id.main_editTextPassword);
					
					String username=usernameEditText.getText().toString();
					String password=Crypto.hash(passwordEditText.getText().toString(), "SHA-1");
					System.out.println(password);
					
					if(!(username.equals("")||password.equals(Crypto.hash("", "SHA-1")))) {
						new SignInTask(thisActivity).execute(username, password);
					} else {
						ToastCentered.makeText(thisActivity, "Fill out all the fields.").show();
					}
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
    
    private boolean isSignedIn() {
    	Serializer<User> seralizer=new Serializer<User>();
    	try {
			User u=seralizer.unserialize(openFileInput(getString(R.string.user_data_store)));
			if(u!=null) {
				return true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    @Override
    public void onBackPressed() {
    	Intent i=new Intent(Intent.ACTION_MAIN);
    	i.addCategory(Intent.CATEGORY_HOME);
    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(i);
    }
    
}