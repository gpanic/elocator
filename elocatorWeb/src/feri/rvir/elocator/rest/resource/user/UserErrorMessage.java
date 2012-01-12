package feri.rvir.elocator.rest.resource.user;

import java.io.Serializable;

public class UserErrorMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean created;
	private String message;
	
	public UserErrorMessage() {
		
	}

	public UserErrorMessage(boolean created, String message) {
		this.created = created;
		this.message = message;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
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
