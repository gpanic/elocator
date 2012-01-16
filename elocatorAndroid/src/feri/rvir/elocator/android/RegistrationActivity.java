package feri.rvir.elocator.android;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import feri.rvir.elocator.android.util.Crypto;
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

public class RegistrationActivity extends Activity {
	
	private RegistrationActivity thisActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		thisActivity=this;
		
		Button registerButton=(Button)findViewById(R.id.reg_buttonRegister);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText usernameEditText=(EditText)findViewById(R.id.reg_editTextUsername);
				EditText passwordEditText=(EditText)findViewById(R.id.reg_editTextPassword);
				EditText repeatEditText=(EditText)findViewById(R.id.reg_editTextPasswordRepeat);
				
				String username=usernameEditText.getText().toString();
				String password=passwordEditText.getText().toString();
				String repeat=repeatEditText.getText().toString();
				if(!(username.equals("")||
						password.equals("")||
						repeat.equals(""))) {
					if(password.equals(repeat)) {
						try {
							ClientResource cr=new ClientResource("http://10.0.2.2:8888/rest/users/"+username+"/register");
							cr.setRequestEntityBuffering(true);
							cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "registrator", Crypto.hash(getString(R.string.registrator_password), "SHA-1"));
							UserResource resource=cr.wrap(UserResource.class);
							UserErrorMessage response=resource.accept(new User(username,Crypto.hash(password, "SHA-1")));
							if(response.isOk()) {
								ToastCentered.makeText(thisActivity, response.getMessage()).show();
								Intent i=new Intent(v.getContext(), MainActivity.class);
								startActivity(i);
							} else {
								ToastCentered.makeText(thisActivity, response.getMessage()).show();
							}
						} catch (Exception e) {
							ToastCentered.makeText(thisActivity, "Connection to server failed.").show();
						}

					} else {
						ToastCentered.makeText(thisActivity, "Passwords do not match.").show();
					}
				} else {
					ToastCentered.makeText(thisActivity, "Fill out all the fields.").show();
				}
				

			}
		});
	}

}
