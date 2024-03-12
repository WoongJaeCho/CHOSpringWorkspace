package kr.board.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.board.entity.Board;
import kr.board.repository.BoardDAO;

@Controller
public class BoardController {
	
	@Autowired
	BoardDAO boardDAO;
	
	@ModelAttribute("cp")
	public String getContextPath(HttpServletRequest req) {
		return req.getContextPath();
	}
	
	@GetMapping("/")
	public String main() {
		return "/board/template";
	}
	
	@GetMapping("/boardList.do")
	public String boardList(Model model) {
		ArrayList<Board> list = boardDAO.getLists();
		model.addAttribute("list", list);
		return "/board/boardList";
	}
	
	@GetMapping("/boardContent.do")
	public String boardContent(int idx, Model model) {
		Board vo = boardDAO.boardContent(idx);
		
		model.addAttribute("vo", vo);
		return "/board/boardContent";
	}
	
	@GetMapping("/boardUpdateForm.do")
	public String boardUpdateForm(int idx, Model model) {
		Board vo = boardDAO.boardContent(idx);
		model.addAttribute("vo", vo);
		return "/board/boardUpdate";
	}
	
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board board, int idx, Model model) {
		int check = boardDAO.updateBoard(board);
		if(check == 0) {
			System.out.println("업데이트 실패");
		} else {
			System.out.println("업데이트 성공");
		}
		Board vo = boardDAO.boardContent(idx);
		model.addAttribute("vo", vo);
		return "board/boardContent";
	}
}
