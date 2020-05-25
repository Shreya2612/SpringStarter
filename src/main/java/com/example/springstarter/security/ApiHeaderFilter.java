package com.example.springstarter.security;


import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiHeaderFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("x-auth-token"); //here x-auth token can be considered as key and token as it's value and that value will be String always.

        if (!isValidToken(token)) {
            ApiResponse resp = ApiResponse.failResponse(Constants.ErrorCodes.CODE_AUTH_FAIL, "Please send a valid token.");
            response.setContentType("application/json");    //Serializing into byte streams
            response.setCharacterEncoding("UTF-8");
            new GsonBuilder().create().toJson(resp,response.getWriter());  //Object(resp object)->Json String->writing into stream writer(getWriter)..Serializing
            return; //these 2 parameters means in first API response(resp) will be converted to json string and with getwriter() it will write string to stream writer.
        } //this is the same process searialization process which internally happens when response is sent to @ResponseBody.
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private boolean isValidToken(String token) {
        return token != null && !token.isEmpty() && Utility.AUTH_TOKEN.equals(token);

    }
}
