package com.example.springstarter.model.response;

import java.util.List;

public class CreateContactResponse{
	private List<DataItem> data;
	private String message;
	private String status;
	private int statusCode;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public CreateContactResponse(List<DataItem> data) {
		this.data = data;
	}

}