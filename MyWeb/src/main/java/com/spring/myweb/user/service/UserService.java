package com.spring.myweb.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.myweb.command.UserVO;
import com.spring.myweb.user.mapper.IUserMapper;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserMapper mapper;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public int idCheck(String id) {
		return mapper.idCheck(id);
	}

	@Override
	public void join(UserVO vo) {
		//회원 비밀번호를 암호화 인코딩
		
		vo.setUserPw(encoder.encode(vo.getUserPw()));
		
		mapper.join(vo);

	}

	@Override
	public UserVO login(String id, String pw) {
		//id 정보를 기반으로 회원의 정보를 조회
		UserVO vo = getInfo(id);
		if(vo != null) {
			String dbPw = vo.getUserPw();
			//날것의 비밀번호와 암호화된 비밀번호의 일치 여부를 알려주는 matches
			if(encoder.matches(pw, dbPw)) {
				return vo;
			}
		}
		return null;
	}

	@Override
	public UserVO getInfo(String id) {
		return mapper.getInfo(id);
	}

	@Override
	public void updateUser(UserVO vo) {
		// TODO Auto-generated method stub

	}

}
