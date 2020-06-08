package com.example.springstarter.model.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("firstName") // with the help of Serialized it will work on the name written in quotes irrespective of what is coming
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("mail")
	private String mail;

	@SerializedName("contact")
	private Long contact;

	@SerializedName("id")
	private Long id;

	@SerializedName("userName")
	private String userName;

	@SerializedName("contactId")
	private Long contactId;

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setMail(String mail){
		this.mail = mail;
	}

	public String getMail(){
		return mail;
	}

	public void setContact(Long contact){
		this.contact = contact;
	}

	public long getContact(){
		return contact;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"UserResponse{" + 
			"firstName = '" + firstName + '\'' + 
			",lastName = '" + lastName + '\'' + 
			",mail = '" + mail + '\'' + 
			",contact = '" + contact + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
}