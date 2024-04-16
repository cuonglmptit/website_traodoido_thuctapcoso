package com.traodoido.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.traodoido.DAO.MessageDAO;
import com.traodoido.Model.Message;
import com.traodoido.Model.User;

import jakarta.servlet.http.HttpSession;
@Controller
public class MessageController {
	@PostMapping("/message/{transid}")
	public String postMessage(Model model, @PathVariable String transid, HttpSession session, @RequestParam String messagefromuser,
			@RequestParam String tinNhan) {
		System.out.println("post message:");
		User user = (User) session.getAttribute("user");
		if(!messagefromuser.equals(user.getUsername())) {
			System.out.println(messagefromuser+": "+user.getUsername());
			return "error";
		}
		MessageDAO messageDAO = new MessageDAO();
		Message message = new Message();
		message.setFrom(user.getUsername());
		message.setTrans(Integer.valueOf(transid));
		message.setContent(tinNhan);
//		System.out.println(message);
		messageDAO.addMessage(message);
		return "redirect:/transaction/"+transid;
	}
}
