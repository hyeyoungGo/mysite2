package com.bit2017.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bit2017.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(
			MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest WebRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		if(supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED;
		}
		// @AuthUser 붙어있고 타입이 UserVo
		HttpServletRequest request = (HttpServletRequest) WebRequest.getNativeRequest();
		HttpSession session = request.getSession(false);
		if(session == null) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		return session.getAttribute("authUser");
	}
	

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 달려있지 않음
		if(authUser == null) {
			return false;
		}
		
		//파라미터타입이 UserVo인지 확인
		if(parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		return true;
	}

}
