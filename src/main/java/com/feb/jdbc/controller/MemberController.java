package com.feb.jdbc.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.feb.jdbc.entity.Member;
import com.feb.jdbc.service.MemberService;
import com.feb.jdbc.util.Sha512Encoder;

@Controller
public class MemberController {

	

	@Autowired
	MemberService memberService;

	//처음에 진입하려고 만든 메서드.
	@GetMapping("/hello.do")
	public ModelAndView hello() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("resultMsg","helloOk");		
		return mv;
	}
	
	// 입력으로 firstName LastName email passwd를 받는다 
	// 나는 name = firstName +LastName, memberId,email = email 이렇게 보고 코딩을 할 것 이다	
	public ModelAndView join(HttpServletResponse response,@RequestParam HashMap< String, String> params) {
		ModelAndView mv = new ModelAndView();
		params.put("memberName", params.remove("firstName")+params.remove("lastName"));
		params.put("memberId", params.get("email"));
		
		System.out.println(params);
		mv.setViewName("login");
		int result = memberService.join(params);
		
		if(result ==1) {
		mv.addObject("resultMsg","joinOk");
		}else {
		mv.addObject("resultMsg","joinFail");
		}
		
		
		return mv;
	}
	
	///이메일 정보가 전달되어 오지만 일전에 join메서드를 보면 알 수 있듯이  mebmerId = email로 두고 구현한다  
	@PostMapping("/login.do")
	public ModelAndView login(HttpServletResponse response,@RequestParam  HashMap< String, String> params) {
		ModelAndView mv =  new ModelAndView();

		mv.setViewName("login");
		Member equ = memberService.findMember(params.get("memberId"));
		String equPas = equ.getPasswd();
		
		Sha512Encoder encoder = Sha512Encoder.getInstance();
		String passwd = encoder.getSecurePassword(params.get("passwd"));
		
		 
		
		
		if(passwd.equals(equPas)) {
			mv.addObject("resultMsg","loginOk");

		} else {
			mv.addObject("resultMsg","loginFail");
		}
		
		return mv;
	}
}
