package feri.rvir.elocator.android;

import feri.rvir.elocator.android.util.RegistrationTask;
import feri.rvir.elocator.android.util.ToastCentered;

import android.app.Activity;
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
						new RegistrationTask(thisActivity).execute(username,password);
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
