package com.costrategix.gbp.entity;


public class NewPassword {
		
	private String newPassword;
	
	private String confirmNewPassword;
	
	private String phonenumber;
	
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public NewPassword(String newPassword, String confirmNewPassword) {
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}

	public NewPassword(String newPassword, String confirmNewPassword, String phonenumber) {
		super();
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
		this.phonenumber = phonenumber;
	}



	public String getNewPassword() {
		return newPassword;
	}

	

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public NewPassword() {
		super();
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
	
}
