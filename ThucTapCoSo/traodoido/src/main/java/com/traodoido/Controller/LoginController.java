package com.traodoido.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.traodoido.DAO.UserDAO;
import com.traodoido.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@GetMapping("/dangnhap")
	public String getDangNhap(Model model, HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "dangnhapform";
		}
		return "redirect:/trangchu";
	}

	@GetMapping("/dangky")
	public String getDangKy(Model model, HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "dangkyform";
		}
		return "redirect:/trangchu";
	}
	
	@PostMapping("/dangnhap")
	public String postLogin(Model model, @RequestParam("username") String username,
	                        @RequestParam("password") String password,
	                        HttpSession session, RedirectAttributes redirect) {
	    // Kiểm tra thông tin đăng nhập
		UserDAO udao = new UserDAO();
	    if (udao.checkValidUser(username, password) != 0) {
	        // Lưu người dùng vào session
	    	User user = udao.selectUserByUsername(username);
	    	session.setAttribute("user", user);
//	    	System.out.println(user);
	        return "redirect:/trangchu";
	    } else {
	    	redirect.addFlashAttribute("thongBao", "Sai tài khoản hoặc mật khẩu!");
	        return "redirect:/dangnhap";
	    }
	}
	
	@PostMapping("/dangky")
	public String postDangKy(Model model, @RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirect) {
	    int success = 0;
	    try {
	        UserDAO udao = new UserDAO();
	        if(udao.selectUserByUsername(username) == null) {
	            success = udao.createUser(username, password); 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    if(success == 0) {
	    	redirect.addFlashAttribute("thongBao", "Đăng ký không thành công! Tài khoản bị trùng!");
	        return "redirect:/dangky"; // trả về trang đăng ký với thông báo lỗi
	    } else {
	    	redirect.addFlashAttribute("thongBao", "Đăng ký thành công!");
	        return "redirect:/dangnhap"; // điều hướng đến trang đăng nhập với thông báo thành công
	    }
	}
	
	@GetMapping("/dangxuat")
	public String getDangXuat(Model model, HttpSession session) {
		session.invalidate();
		return "redirect:/trangchu";
	}
}
