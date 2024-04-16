package com.traodoido.Controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.traodoido.DAO.CategoryDAO;
import com.traodoido.DAO.ItemDAO;
import com.traodoido.DAO.UserDAO;
import com.traodoido.Model.Category;
import com.traodoido.Model.Item;
import com.traodoido.Model.User;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class TrangChuController {
	@GetMapping("/donganggia")
	public String getChooseTrade(Model model, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		ItemDAO idao = new ItemDAO();
		List<Item> itemsOfUser = idao.selectItemsOfUserByUID(user.getUid());
		List<Item> tradeItemsOfUser = new ArrayList<>();
		
		for (Item item : itemsOfUser) {
			if(item.getAcceptType() == 1 || item.getAcceptType() ==3) {
				tradeItemsOfUser.add(item);
			}
		}
		model.addAttribute("items", tradeItemsOfUser);
		model.addAttribute("thaotac", "CHỌN MÓN ĐỒ CỦA BẠN ĐỂ XEM CÁC MÓN ĐỒ NGANG GIÁ CÓ THỂ ĐỔI:");
		return "itemstofindtradeable";
	}
	@GetMapping("/donganggia/{iid}")
	public String getTradeItem(Model model, @PathVariable String iid, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		ItemDAO idao = new ItemDAO();
		List<Item> items = idao.selectAllItems();
		Item itemToFind = idao.selectItemById(Integer.valueOf(iid));
		List<Item> tradeAbleItems = new ArrayList<>();
						
		for (Item item : items) {
			if(item.getUserID() != user.getUid()) {
				if(item.getAvaiable() > 0) {
					if(item.getAcceptType() == 1 || item.getAcceptType() == 3) {
						int minVal = item.getTradePrice() - item.getTradeRange();
						int maxVal = item.getTradePrice() + item.getTradeRange();
						if(itemToFind.getTradePrice() >= minVal && itemToFind.getTradePrice() <= maxVal) {
							tradeAbleItems.add(item);
						}
					}
				}
			}
		}
		model.addAttribute("items", tradeAbleItems);
		model.addAttribute("thaotac", "TẤT CẢ MÓN ĐỒ NGANG GIÁ CÓ THỂ ĐỔI VỚI MÓN ĐỒ BẠN ĐÃ CHỌN: "+itemToFind.getTitle());
		return "index";
	}
	
	@GetMapping("/trangchu")
	public String getTrangChu(Model model) {
		ItemDAO idao = new ItemDAO();
		List<Item> items = idao.selectAllItems();
		model.addAttribute("items", items);
		model.addAttribute("thaotac", "TẤT CẢ MÓN ĐỒ TRÊN TRANG");
		return "index";
	}
	@GetMapping("/category/{catid}")
	public String getCategory(Model model, @PathVariable String catid) {
		ItemDAO idao = new ItemDAO();
		List<Item> items = idao.selectAllItemsByCategory(Integer.valueOf(catid));
		model.addAttribute("items", items);
		CategoryDAO categoryDAO = new CategoryDAO();
		Category category = categoryDAO.selectCategoryById(Integer.valueOf(catid));
		model.addAttribute("thaotac", "CÁC MÓN ĐỒ THỂ LOẠI: "+category.getCatname());
		return "index";
	}
	@GetMapping("/item/{iid}")
	public String getItem(Model model, @PathVariable String iid) {
		model.addAttribute("iid", iid);
		UserDAO uDAO = new UserDAO();
		ItemDAO idao = new ItemDAO();
		Item item = idao.selectItemById(Integer.parseInt(iid));
		User user = uDAO.selectUserByID(item.getUserID());
		model.addAttribute("authorname", user.getUname());
		model.addAttribute("author", user.getUsername());
		model.addAttribute("item", item);
		return "item";
	}
	
	@GetMapping("/search")
	public String getSearch(Model model, @RequestParam String searchkeyword) {
		ItemDAO idao = new ItemDAO();
		List<Item> items = idao.selectItemsByKeyword(searchkeyword);
		model.addAttribute("items", items);
		model.addAttribute("thaotac", "TÌM KIẾM VỚI TỪ KHÓA: "+searchkeyword);
		return "index";
	}
	
	@GetMapping("/test")
	public String asdasdD() {
		return "testupload";
	}
	
}
