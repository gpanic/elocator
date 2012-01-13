package feri.rvir.elocator.android;

import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;

import feri.rvir.elocator.android.util.ToastCentered;
import feri.rvir.elocator.rest.resource.user.User;
import feri.rvir.elocator.rest.resource.user.UserErrorMessage;
import feri.rvir.elocator.rest.resource.user.UserResource;

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
        ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users/jkjkj/signin");
        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "asf", "asdasd");
        UserResource user=cr.wrap(UserResource.class);
        try {
        	System.out.println(user.retrieve());
        } catch (RuntimeException e) {
        	System.out.println(cr.getStatus());
        }
       
        
        Button signInButton=(Button)findViewById(R.id.main_buttonSignIn);
        signInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText usernameEditText=(EditText)findViewById(R.id.main_editTextUsername);
				EditText passwordEditText=(EditText)findViewById(R.id.main_editTextPassword);
				
				String username=usernameEditText.getText().toString();
				String password=passwordEditText.getText().toString();
				
				if(!(username.equals("")||password.equals(""))) {
					ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users/"+username);
			        cr.setRequestEntityBuffering(true);
			        cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, username, password);
			        try {
			        	cr.get();
			        } catch (RuntimeException e) {
			        	if(!cr.getStatus().isSuccess()) {
			        		usernameEditText.setText("");
			        		passwordEditText.setText("");
			        		if(cr.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
			        			ToastCentered.makeText(thisActivity, "Credentials do not match.").show();
			        		} else {
			        			ToastCentered.makeText(thisActivity, "Sign in failed.").show();
			        		}
			        	}
			        }
			        if(cr.getStatus().isSuccess()) {
			        	ToastCentered.makeText(thisActivity, "Signed in as: "+username+".").show();
			        	Intent i=new Intent(thisActivity, TabMenuActivity.class);
			        	startActivity(i);
			        }
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