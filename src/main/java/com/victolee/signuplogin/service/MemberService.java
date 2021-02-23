package com.victolee.signuplogin.service;

import com.victolee.signuplogin.domain.Role;
import com.victolee.signuplogin.domain.entity.MemberEntity;
import com.victolee.signuplogin.domain.repository.MemberRepository;
import com.victolee.signuplogin.dto.MemberDto;
import com.victolee.signuplogin.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        Optional<MemberEntity> members = memberRepository.findByEmail(memberDto.getEmail());
        if(!members.isEmpty()){
            throw new UserAlreadyExistsException("user already exists");
        }

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<MemberEntity> userEntityWrapper = memberRepository.findByEmail(userEmail);
        MemberEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }

    public MemberEntity findUserById(Long id) throws UsernameNotFoundException{
        Optional<MemberEntity> userEntityWrapper = memberRepository.findById(id);
        return userEntityWrapper.get();
    }

    public MemberEntity findUserByUsername(String username) throws UsernameNotFoundException{
        Optional<MemberEntity> userEntityWrapper = memberRepository.findByEmail(username);
        return userEntityWrapper.get();
    }

    public Long UpdateUser(MemberEntity memberEntity){
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }

    @Transactional
    public Long UpdateUser(Long id, String bio, String gender, Date birthdate) {
        MemberEntity member = memberRepository.findById(id).orElseThrow(UserAlreadyExistsException::new);
        member.setBio(bio);
        member.setGender(gender);
        member.setBirthdate(birthdate);
        return member.getId();
    }
}
