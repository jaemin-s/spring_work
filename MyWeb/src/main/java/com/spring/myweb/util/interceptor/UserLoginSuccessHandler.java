package com.spring.myweb.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

//인터셉터로 사용할 클래스는 HandlerInterceptor 인터페이스를 구현합니다.
@Slf4j
public class UserLoginSuccessHandler implements HandlerInterceptor{
	
	// preHandle은 컨트롤러로 요청이 들어가기 전 처리해야 할 로직을 작성.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandle");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	// postHandle은 컨트롤러를 나갈 때 공통 처리해야 할 내용을 작성
	// /userLogin이라는 요청이 마무리 된 후 viewResolver로 전달이 되기 전
	// 로그인 성공 / 실패 여부에 따라 처리할 로직을 작성할 예정입니다.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		log.info("로그인 인터셉터가 동작");
		log.info("요청 방식:" +request.getMethod());
		
		if(request.getMethod().equals("POST")) {
			ModelMap map = modelAndView.getModelMap();
			String id = (String) map.get("user"); //모델 내에 있는 user라는 이름의 데이터 꺼내기
//			log.info("인터셉터 내부에서 user 확인: "+ vo.toString());
			
			if(id != null) { //로그인 성공
				log.info("로그인 성공 로직 동작");
				HttpSession session = request.getSession();
				session.setAttribute("login", id);
				response.sendRedirect(request.getContextPath()+"/");
			} else { // 로그인 실패
				modelAndView.addObject("msg","loginFail");
			}
			
		}
	}

}
