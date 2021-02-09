package com.yoo.lms.service;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.repository.MemberRepository;
import com.yoo.lms.searchCondition.MemberSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional(readOnly = true)

public class MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager em;
    private final JavaMailSender javaMailSender;
    private int tempPWLength;

    @Autowired
    public MemberService(MemberRepository memberRepository, EntityManager em, JavaMailSender javaMailSender) {
        this.memberRepository = memberRepository;
        this.em = em;
        this.javaMailSender = javaMailSender;
        this.tempPWLength = 10;
    }

    @Transactional
    public void joinStduent(Student student){

        student.initMemberType(MemberType.STUDENT);

        memberRepository.save(student);

    }

    @Transactional
    public void joinTeacher(Teacher teacher){

        teacher.initMemberType(MemberType.TEACHER);

        memberRepository.save(teacher);

    }

    public boolean checkDuplicationID(String id) {

        MemberSearchCondition searchCondition = new MemberSearchCondition(id, null, null, null);

        Member findMember = memberRepository.searchMember(searchCondition);

        if(findMember == null)
            return false;
        else
            return true;

    }


    boolean login(String id, String password) {

        MemberSearchCondition searchCondition = new MemberSearchCondition(id, password, null, null);

        Member findMember = memberRepository.searchMember(searchCondition);

        if(findMember == null)
            return false;
        else
            return true;

    }

    public String findID(String name, String email) {

        MemberSearchCondition searchCondition = new MemberSearchCondition(null, null, name, email);

        Member findMember = memberRepository.searchMember(searchCondition);

        if(findMember == null)
            return "";
        else
            return findMember.getId();

    }

    @Transactional
    public boolean updateTempPW(String id, String name, String email) {
        MemberSearchCondition searchCondition = new MemberSearchCondition(id, null, name, email);

        Member findMember = memberRepository.searchMember(searchCondition);

        if(findMember == null)
            return false;
        else {

            
            
            // 임시비밀번호 생성
            String tempPassword = createTempPW();
            
            // 이메일로 전송

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(findMember.getEmail());
            simpleMailMessage.setSubject("[LMS 임시 비밀번호 발급 메일]");
            simpleMailMessage.setText("임시 비밀번호는 [ " + tempPassword +"  ] 입니다.");
            javaMailSender.send(simpleMailMessage);
            
            
            // 임시비밀번호로 수정

            findMember.initPassword(tempPassword);

            return true;
        }

    }


    @Transactional
    public void updatePersonalInfo(Member member){

        Member findMember = memberRepository.findById(member.getId()).get();

        findMember.updateInfo(member);

    }


    private String createTempPW() {

        char[] charArr
                = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };


        StringBuilder tempPassword = new StringBuilder();

        for (int tempI = 0; tempI < tempPWLength; tempI++) {

            int randomCharIndex = (int) (charArr.length * Math.random());

            tempPassword.append(charArr[randomCharIndex]);

        }

        return tempPassword.toString();
    }

}
