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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private int tempPWLength = 10;
    private final char[] charArr =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

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

        return memberRepository.isExistId(id);

    }

    public MemberType searchMemberType(MemberSearchCondition searchCondition) {
        return memberRepository.searchMemberType(searchCondition);
    }

    public Member findById(String memberId) {
        return memberRepository.findById(memberId).get();
    }


    public String findID(MemberSearchCondition searchCondition) {

        Member findMember = memberRepository.searchMember(searchCondition);

        if(findMember == null)
            return "";
        else
            return findMember.getId();



    }

    @Transactional
    public boolean updateTempPW(MemberSearchCondition searchCondition) {

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

    public void sendEmailFullID(MemberSearchCondition searchCondition) {

        Member findMember = memberRepository.searchMember(searchCondition);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(findMember.getEmail());
        simpleMailMessage.setSubject("[LMS 전체 아이디 확인 메일]");
        simpleMailMessage.setText("전체 아이디는 [ " + findMember.getId() +"  ] 입니다.");
        javaMailSender.send(simpleMailMessage);
    }

    @Transactional
    public void updateInfo(String id, Member member){

        Member findMember = memberRepository.findById(id).get();
        findMember.updateInfo(member);

    }




    private String createTempPW() {

        StringBuilder tempPassword = new StringBuilder();

        for (int tempI = 0; tempI < tempPWLength; tempI++) {

            int randomCharIndex = (int) (charArr.length * Math.random());

            tempPassword.append(charArr[randomCharIndex]);

        }

        return tempPassword.toString();
    }

}
