package com.example.springstarter.model.response;

import java.util.List;

public class DataItem{
	private List<Long> success;
	private List<Long> failed;

	public void setSuccess(List<Long> success){
		this.success = success;
	}

	public List<Long> getSuccess() {
		return success;
	}

	public void setFailed(List<Long> failed){
		this.failed = failed;
	}

	public List<Long> getFailed(){
		return failed;
	}

	public DataItem(List<Long> success, List<Long> failed) {
		this.success = success;
		this.failed = failed;
	}
}
