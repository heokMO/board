package com.example.board.util;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginCheckResolver implements HandlerMethodArgumentResolver {
    private final LoginChecker loginChecker;

    public LoginCheckResolver(LoginChecker loginChecker) {
        this.loginChecker = loginChecker;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(IsLogin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object nativeRequest = webRequest.getNativeRequest();
        if(nativeRequest instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest = (HttpServletRequest) nativeRequest;

            return loginChecker.isLogin(httpServletRequest);
        }
        return false;
    }
}
