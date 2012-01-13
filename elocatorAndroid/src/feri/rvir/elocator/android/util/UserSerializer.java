package feri.rvir.elocator.android.util;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import feri.rvir.elocator.rest.resource.user.User;

import android.content.Context;

public class UserSerializer {
	
	private static final String FILENAME="user.data";
	
	public static void serialize(Context context, User user) {
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(user);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static User unserialize(Context context) {
		User user=null;
		try {
			FileInputStream fis=context.openFileInput(FILENAME);
			ObjectInputStream ois=new ObjectInputStream(fis);
			user=(User)ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return user;
	}

}
