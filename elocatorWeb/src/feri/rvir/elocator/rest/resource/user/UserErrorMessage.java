package feri.rvir.elocator.rest.resource.user;

import java.io.Serializable;

public class UserErrorMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean ok;
	private String message;
	
	public UserErrorMessage() {
		
	}
	
	public UserErrorMessage(boolean ok, String message) {
		super();
		this.ok = ok;
		this.message = message;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

}
