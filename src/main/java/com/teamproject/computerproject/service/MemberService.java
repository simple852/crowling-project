package com.teamproject.computerproject.service;


import com.teamproject.computerproject.domain.User;
import com.teamproject.computerproject.dto.UserDto;
import com.teamproject.computerproject.repositery.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public boolean login(UserDto userDto){
        return userRepository.existsByMemberIdAndMemberPw(userDto.getMemberId(), userDto.getMemberPw());
    }

    public boolean joinMember(UserDto userDto) {
      if(!checkId(userDto.getMemberId())) {
          userRepository.save(modelMapper.map(userDto, User.class));
          return true;
      }
      return false;
    }


    public boolean checkId(String memberId){
        return  userRepository.existsByMemberId(memberId);
    }


}
