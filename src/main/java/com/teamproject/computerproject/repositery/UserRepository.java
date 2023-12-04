package com.teamproject.computerproject.repositery;

import com.teamproject.computerproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByMemberIdAndMemberPw(String memberId, String memberPw);
    boolean existsByMemberId(String memberId);
   User findByMemberPwAndMemberId(String memberPw, String memberId);



    User findByMemberId(String memberID);
}