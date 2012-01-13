package feri.rvir.elocator.android;

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
        
        Button signInButton=(Button)findViewById(R.id.main_buttonSignIn);
        signInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText usernameEditText=(EditText)findViewById(R.id.main_editTextUsername);
				EditText passwordEditText=(EditText)findViewById(R.id.main_editTextPassword);
				
				String username=usernameEditText.getText().toString();
				String password=passwordEditText.getText().toString();
				
				if(!(username.equals("")||password.equals(""))) {
					try {
						ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users/"+username+"/signin");
				        cr.setRequestEntityBuffering(true);
				        UserResource resource=cr.wrap(UserResource.class);
				        UserErrorMessage response=resource.accept(new User(username, password));
						if(response.isOk()) {
							Intent i=new Intent(thisActivity,TabMenuActivity.class);
							startActivity(i);
							ToastCentered.makeText(thisActivity, response.getMessage()).show();
						} else {
							usernameEditText.setText("");
							passwordEditText.setText("");
							ToastCentered.makeText(thisActivity, response.getMessage()).show();
						}
					} catch (Exception e) {
						ToastCentered.makeText(thisActivity, "Connection to server failed.").show();
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