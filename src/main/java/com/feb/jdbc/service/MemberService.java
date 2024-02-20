package com.feb.jdbc.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feb.jdbc.dao.MemberDao;
import com.feb.jdbc.entity.Member;
import com.feb.jdbc.util.Sha512Encoder;

@Service
public class MemberService {
	
	@Autowired
	MemberDao memberDao;
	
	
	public int join(HashMap<String,String> params) {
	
		Sha512Encoder sha = Sha512Encoder.getInstance();

		String passwd = sha.getSecurePassword(params.get("passwd"));
		params.put("passwd", passwd);
		int result= memberDao.insertMember(params);
		System.out.println(params);
		
		return result;
	}
	

	
	public Member findMember(String memberId) {
		
		Member member = memberDao.selectMember(memberId);
		
		return member;
	}

}
