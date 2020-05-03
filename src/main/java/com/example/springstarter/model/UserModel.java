package com.example.springstarter.model;

public class UserModel{
	private String password;
	private String username;
	private String firstName;
	private String lastName;
	private String contact;
	private Long id;
    private String mail;

	public UserModel() {
	}

	public UserModel(String username) {
	    this(username, "");
	}

	public UserModel(String username, String password) {
		this.password = password;
		this.username = username;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"UserModel{" + 
			"password = '" + password + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
