package com.yoo.lms.repository;

import com.yoo.lms.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {



}
