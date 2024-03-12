package com.mysql.basic.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.basic.entity.Member;
import com.mysql.basic.repository.MemberDAO;

@Controller
public class MemberController {
	
	@Autowired
	MemberDAO memberDAO;
	
	@ModelAttribute("cp")
	public String getContextPath(HttpServletRequest req) {
		return req.getContextPath();
	}
	
	// get,post, put, delete 모든 값들이 허용가능하다
	@RequestMapping(value="/member/userMenu", method = RequestMethod.GET)
	public String UserMenu() {
		return "/member/userMenu";
	}
	
	@GetMapping("/member/list")
	public String list(Model model) {
		ArrayList<Member> memberList = memberDAO.getMemberList();
		
		model.addAttribute("memberList", memberList);
		return "/member/list";
	}
	
	@GetMapping("/member/joinForm")
	public String joinForm() {
		return "/member/joinForm";
	}
	
	@PostMapping("/member/joinPro")
	public String joinPro(@ModelAttribute Member member) {
		System.out.println("Member = "+member);
		memberDAO.joinMember(member);
		return "redirect:/member/list";
	}
	
	@GetMapping("/member/loginForm")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@PostMapping("/member/loginPro")
	public String loginPro(@ModelAttribute Member member, Model model, HttpSession session) {
		int check = memberDAO.checkMember(member);
		if(check==1) session.setAttribute("log", member.getId());
		model.addAttribute("check", check);
		model.addAttribute("id", member.getId());
		return "/member/loginPro";
	}
	
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/member/index";
	}
	
	@GetMapping("/member/modifyForm")
	public String modifyForm(Model model, HttpSession session) {
//		if(session.getAttribute("log") == null) {
//			return "/";
//		}
		if(session.getAttribute("log") != null) {
			Member member = memberDAO.getOneMember((String)session.getAttribute("log"));
			model.addAttribute("member", member);
		}
		
		return "/member/modifyForm";
	}
	
	@PostMapping("/member/modifyPro")
	public String modifyPro(Member member, HttpSession session) {
		if(session.getAttribute("log") == null) {
			return "/member/index";
		}
		
		System.out.println("modify Member ="+member);
		member.setId((String)session.getAttribute("log"));
		int check = memberDAO.updateMember(member);
		if(check == 0) {
			System.out.println("업데이트 실패"); 
		} else {
			System.out.println("업데이트 성공");
		}
		return "/member/list";
	}
	
	@GetMapping("/member/deleteFrom")
	public String deleteForm(HttpSession session, Model model) {
		if(session.getAttribute("log") == null) {
			return "redirect:/";
		}
		String id = (String)session.getAttribute("log");
		model.addAttribute("id", id);
		return "/member/deleteForm";
	}
	
	@PostMapping("/member/deletePro")
	public String deletePro(HttpSession session, String pw) {
		String id = (String)session.getAttribute("log");
		Member member = memberDAO.getOneMember(id);
		if(member.getPw().equals(pw)) {
			int check = memberDAO.deleteMember(member.getId());
			if(check == 0) {
				System.out.println("삭제 실패"); 
			} else {
				System.out.println("삭제 성공"); 
				session.invalidate();
				return "redirect:/";
			}
		}
		return "/member/deleteForm";
	}
}
