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
	

	
	public boolean login(HashMap<String,String> params) {
		
		Member member = memberDao.selectMember(params.get("memberId"));
		
		String equPas = member.getPasswd();
		
		Sha512Encoder encoder = Sha512Encoder.getInstance();
		String passwd = encoder.getSecurePassword(params.get("passwd"));
		if(equPas.equals(passwd)) {
		return true;
		}else {
			return false;
		}
	}

}
