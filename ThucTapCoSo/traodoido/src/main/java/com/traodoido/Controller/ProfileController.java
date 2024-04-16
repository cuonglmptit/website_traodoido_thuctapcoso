package com.traodoido.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.traodoido.DAO.ItemDAO;
import com.traodoido.DAO.UserDAO;
import com.traodoido.Model.Item;
import com.traodoido.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	@GetMapping("/account/profile")
	public String getProfile(Model model, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		try {
			if (user == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/dangnhap";
			}
			UserDAO udao = new UserDAO();
			user = udao.selectUserByID(user.getUid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("user", user);
		return "profile";
	}
	
	@PutMapping("/account/profile/update")
	public String updateProfile(Model model, User user, HttpSession session, RedirectAttributes redirect) {
		User sessionUser =  (User) session.getAttribute("user");
		System.out.println(user);
		try {
			sessionUser.setUname(user.getUname());
			sessionUser.setUaddress(user.getUaddress());
			sessionUser.setUbirth(user.getUbirth());
			UserDAO udao = new UserDAO();
			udao.updateUser(sessionUser);
			model.addAttribute("user", sessionUser);
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("thongBao","Cập nhật thất bại!");
			return "redirect:/account/profile";
		}
		redirect.addFlashAttribute("thongBao","Cập nhật thành công!");
		return "redirect:/account/profile";
	}
}
