package com.feb.jdbc.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.feb.jdbc.entity.Member;

@Repository
public interface MemberDao {
	
	public int insertMember(HashMap<String,String> params);
	
	public Member selectMember(String memberId);

}
