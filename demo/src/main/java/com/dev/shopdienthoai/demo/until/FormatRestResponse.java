package com.dev.shopdienthoai.demo.until;

import com.dev.shopdienthoai.demo.domain.RestResponse;
import com.dev.shopdienthoai.demo.until.annotation.ApiMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse httpResponse =( (ServletServerHttpResponse) response).getServletResponse();
        int status = httpResponse.getStatus();
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatus(status);
        if(body instanceof String)
        {
            return body;
        }
        if(status>=400){
            return body;
        }else {
            restResponse.setData(body);
            ApiMessage message=returnType.getMethodAnnotation(ApiMessage.class);
            restResponse.setMessage(message!=null?message.value():"CALL API SUCCESS");
        }
        return restResponse;
    }
}
