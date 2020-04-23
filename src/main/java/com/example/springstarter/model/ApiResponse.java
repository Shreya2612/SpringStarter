package com.example.springstarter.model;

import java.util.List;

public class ApiResponse{
	private List<Object> data;
	private String message;
	private String status;
	private int statusCode;

	public void setData(List<Object> data){
		this.data = data;
	}

	public List<Object> getData(){
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

	@Override
 	public String toString(){
		return 
			"ApiResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}