package com.yoo.lms.service;

import com.yoo.lms.domain.Member;
import com.yoo.lms.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final EntityManager em;

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
