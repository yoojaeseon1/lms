package com.yoo.lms.service;

import com.yoo.lms.domain.Member;
import com.yoo.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Transactional
    public void join(Member member){

        memberRepository.save(member);

    }

    @Transactional
    public void updatePersonelInfo(Member member){

        Member findMember = memberRepository.findById(member.getId()).get();

        findMember.updateInfo(member);

    }





}
