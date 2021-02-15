package com.victolee.signuplogin.controller;

import com.victolee.signuplogin.domain.entity.MemberEntity;
import com.victolee.signuplogin.dto.MemberDto;
import com.victolee.signuplogin.exception.WrongPasswordConfirmException;
import com.victolee.signuplogin.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Member;
import java.text.DateFormat;

@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;

    // 메인 페이지
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "/signup";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(MemberDto memberDto, String password1) {

        if(!memberDto.getPassword().contentEquals(password1)){
            throw new WrongPasswordConfirmException("confirm your password again");
        }
        memberService.joinUser(memberDto);

        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo(Model model) {

        MemberEntity member;

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            member = memberService.findUserByUsername(user.getUsername());
            if(member == null){
                throw new Exception("?");
            }
        }
        catch(Exception e){
            return "redirect:/user/login";
        }

        model.addAttribute("user", member);
        return "/myinfo";
    }

    @GetMapping("/user/update-profile")
    public String updateProfile(Model model){

        MemberEntity member;

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            member = memberService.findUserByUsername(user.getUsername());
            if(member == null){
                throw new Exception("member not found");
            }
        }
        catch(Exception e){
            return "redirect:/user/login";
        }

        model.addAttribute("member", member);

        return "/update-profile";
    }

    @PostMapping("/user/update-profile/post")
    public String updateProfilePost(@ModelAttribute MemberEntity member, Model model){

        System.out.println("!!!!!!!" + member.getBio());
        MemberEntity me;

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            me = memberService.findUserByUsername(user.getUsername());
            if(me == null){
                throw new Exception("member not found");
            }
        }
        catch(Exception e){
            return "redirect:/user/login";
        }

        me.setBio(member.getBio());
        me.setGender(member.getGender());
        me.setBirthdate(member.getBirthdate());
        memberService.UpdateUser(me);

        return "redirect:/user/info";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }
}
