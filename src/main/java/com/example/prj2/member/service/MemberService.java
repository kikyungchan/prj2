package com.example.prj2.member.service;

import com.example.prj2.member.dto.MemberDto;
import com.example.prj2.member.dto.MemberForm;
import com.example.prj2.member.entity.Member;
import com.example.prj2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void add(MemberForm data) {
        Optional<Member> db = memberRepository.findById(data.getId());
        if (db.isEmpty()) {
            List<Member> byNickName = memberRepository.findByNickName(data.getNickName());
            if (byNickName.isEmpty()) {

                //새 엔티티 객체 생성해서
                Member member = new Member();
                //data에 있는거 옮겨닮고
                member.setId(data.getId());
                member.setPassword(data.getPassword());
                member.setNickName(data.getNickName());
                member.setInfo(data.getInfo());
                //저장
                memberRepository.save(member);
            } else {
                throw new DuplicateKeyException(data.getId() + "는 이미 존재하는 닉네임입니다.");
            }

        } else {
            throw new DuplicateKeyException(data.getId() + "는 이미 존재하는 아이디입니다.");
        }
    }

    public List<Member> list() {
        List<Member> all = memberRepository.findAll();
        return all;
    }

    public MemberDto get(String id) {
        Member member = memberRepository.findById(id).get();
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setNickName(member.getNickName());
        dto.setInfo(member.getInfo());
        dto.setCreatedAt(member.getCreatedAt());
        return dto;

    }

    public boolean update(MemberForm data) {
        //조회
        Member member = memberRepository.findById(data.getId()).get();
        String dbPw = member.getPassword();
        String formPw = data.getPassword();
        if (dbPw.equals(formPw)) {
            //변경
            member.setNickName(data.getNickName());
            member.setInfo(data.getInfo());
            //저장
            memberRepository.save(member);

            return true;
        } else {
            return false;
        }
    }

    public boolean updatePassword(String id, String oldPassword, String newPassword) {
        Member db = memberRepository.findById(id).get();
        String dbPw = db.getPassword();
        if (dbPw.equals(oldPassword)) {
            db.setPassword(newPassword);
            memberRepository.save(db);

            return true;
        } else {
            return false;
        }
    }
}
