package feri.rvir.elocator.android.util;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import feri.rvir.elocator.rest.resource.user.User;

import android.content.Context;
public class UserSerializer {
	
	private static final String FILENAME="user.data";
	
	public static void serialize(Context context, User user) {
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(user);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
