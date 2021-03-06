package com.example.springstarter.model.response;

import com.example.springstarter.util.Constants;

import java.util.Collections;
import java.util.List;

public class ApiResponse {
    private List<Object> data = Collections.EMPTY_LIST;
   // private List<Object> successContacts = Collections.EMPTY_LIST;
   // private List<Object> failContacts = Collections.EMPTY_LIST;
    private String message;
    private String status;
    private int statusCode;

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<Object> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ApiResponse(List<Object> data, String message, String status, int statusCode) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }

   /* public ApiResponse(List<List<List<Object>>> data, String message, String status, int statusCode) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }*/

    public ApiResponse() {  //since we created the above constructor ,creating this default constructor was necessary.
    }

    @Override
    public String toString() {
        return
                "ApiResponse{" +
                        "data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        ",statusCode = '" + statusCode + '\'' +
                        "}";
    }
// This is generic fail response.
    public static ApiResponse failResponse(int errorCode, String msg) {
        return new ApiResponse(
                Collections.emptyList(),
                msg,
                Constants.MSG_STATUS_FAIL,
                errorCode
        );

    }
    // This is generic success response.
    public static ApiResponse successResponse(int errorCode, String msg, List<Object> data) {
        return new ApiResponse(
                data,
                msg,
                Constants.MSG_STATUS_SUC,
                errorCode
        );
    }
   //ApiResponse created for showing  successfully added contacts while calling Create contacts API(multiple contact addition)
 /*   public static ApiResponse successContacts(int errrorCode, String msg, List<Object> success, List<Object> fail){
        return  new ApiResponse(
                success,
                fail,
                msg,
                Constants.MSG_STATUS_SUC,
                errrorCode
        );
    }*/
}