package com.victolee.signuplogin.service;

import com.victolee.signuplogin.domain.entity.MemberEntity;
import com.victolee.signuplogin.dto.MemberDto;
import com.victolee.signuplogin.exception.UserAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @Transactional
    @Rollback(false)
    public void 어드민추가 () {
        MemberDto member = new MemberDto(0L, "admin@example.com", "admin");
        Long memberId = memberService.joinUser(member);
    }

    @Test
    @Transactional
    public void 새로운회원추가 (){
        MemberDto member = new MemberDto(0L, "test@example.com", "test");
        Long memberId = memberService.joinUser(member);
        MemberEntity memberEntity = memberService.findUserById(memberId);
        Assert.assertEquals(memberId, memberEntity.getId());
        Assert.assertEquals(member.getEmail(), memberEntity.getEmail());
        Assert.assertEquals(member.getPassword(), memberEntity.getPassword());
    }

    @Test(expected = UserAlreadyExistsException.class)
    @Transactional
    public void 중복회원추가 (){
        MemberDto member1 = new MemberDto(0L, "test1@example.com", "test1");
        Long member1Id = memberService.joinUser(member1);
        MemberDto member2 = new MemberDto(0L, "test1@example.com", "test1");
        Long member2Id = memberService.joinUser(member2);

        Assert.fail("completed with no exception");
    }
}