package com.traodoido.Controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.traodoido.DAO.ItemDAO;
import com.traodoido.DAO.MessageDAO;
import com.traodoido.DAO.TransactionDAO;
import com.traodoido.DAO.UserDAO;
import com.traodoido.Model.Item;
import com.traodoido.Model.Message;
import com.traodoido.Model.Transaction;
import com.traodoido.Model.User;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class TransController {
	@GetMapping("/yourtransaction")
	public String getYourTransaction(HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		return "choosetrans";
	}
	
	//Đến trang các đồ có thể đổi khi bấm nút trao đổi
	@GetMapping("/transaction/trade/{iid}")
	public String getTradeTrans(Model model, @PathVariable String iid, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		ItemDAO idao = new ItemDAO();
		Item item1 = idao.selectItemById(Integer.parseInt(iid));
		
		if(item1.getAcceptType() == 2) return "error";
		
		int minValItem1 = item1.getTradePrice() - item1.getTradeRange();
		int maxValItem1 = item1.getTradePrice() + item1.getTradeRange();
		
		List<Item> ableItems = new ArrayList<>();
		List<Item> itemsOfUser = idao.selectItemsOfUserByUID(user.getUid());
		for (Item item : itemsOfUser) {
			if(item.getAcceptType() == 1 || item.getAcceptType() == 3) {
				if(item.getTradePrice() >= minValItem1 && item.getTradePrice() <= maxValItem1 && item.getAvaiable() > 0) {
					ableItems.add(item);
				}	
			}
		}
		model.addAttribute("item1id", item1.getIid());
		model.addAttribute("items", ableItems);
		model.addAttribute("thaotac", "MÓN ĐỒ NGANG GIÁ CÓ THỂ ĐỔI:");
		return "tradeable";
	}
	//Đến trang tạo giao dịch trao đổi
	@GetMapping("/transaction/trade")
	public String getTradeTrans(Model model, HttpSession session, RedirectAttributes redirect,
			@RequestParam int item1id,
			@RequestParam int item2id
			) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
//		System.out.println("goi get trade: "+item1id+"<->"+item2id);
		ItemDAO itemDAO = new ItemDAO();
		Item item1 = itemDAO.selectItemById(item1id);
		Item item2 = itemDAO.selectItemById(item2id);
		
		if(item2.getUserID() != user.getUid()) return "error";
		if(item1.getAvaiable() <= 0 ) return "error";
		if(item2.getAvaiable() <= 0 ) return "error";
		
		UserDAO userDAO = new UserDAO();
		User user1 = userDAO.selectUserByID(item1.getUserID());
		User user2 = userDAO.selectUserByID(item2.getUserID());
		model.addAttribute("item1", item1);
		model.addAttribute("item2", item2);
		model.addAttribute("user1", user1);
		model.addAttribute("user2", user2);
		return "tradeitem";
	}
	//Tạo giao dịch loại trao đổi
	@PostMapping("/transaction/trade")
	public String postTradeTrans(Model model, HttpSession session, RedirectAttributes redirect,
			@RequestParam int iditem1,
			@RequestParam int iditem2,
			@RequestParam int tradeamount
			) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
//		System.out.println("goi post trade: "+item1id+"<->"+item2id);
		ItemDAO itemDAO = new ItemDAO();
		Item item1 = itemDAO.selectItemById(iditem1);
		Item item2 = itemDAO.selectItemById(iditem2);
		if(item2.getUserID() != user.getUid()) return "error";
		if(item1.getAvaiable() <= 0 ) return "error";
		if(item2.getAvaiable() <= 0 ) return "error";

		UserDAO userDAO = new UserDAO();
		TransactionDAO transactionDAO = new TransactionDAO();
		Transaction trans = new Transaction();
		
		trans.setAmount(tradeamount);
		trans.setFromuser(user.getUsername());
		trans.setTouser(userDAO.selectUserByID(item1.getUserID()).getUsername());
		trans.setItem1id(item1.getIid());
		trans.setItem2id(item2.getIid());
		trans.setStatus(3);
		trans.setTradetype(1);
		transactionDAO.addTrans(trans);
		return "redirect:/yoursentpendingtrans";
	}
	
	@GetMapping("/transaction/buy/{iid}")
	public String getBuyTrans(Model model, @PathVariable String iid, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		model.addAttribute("iid", iid);
		UserDAO uDAO = new UserDAO();
		ItemDAO idao = new ItemDAO();
		Item item = idao.selectItemById(Integer.parseInt(iid));
		User selluser = uDAO.selectUserByID(item.getUserID());
		User buyuser = (User) session.getAttribute("user");
		
		if(item.getAcceptType() == 1) return "error";
		if(item.getAvaiable() <= 0) return "error";
		if(buyuser.getUsername().equals(selluser.getUsername())) return "error";
		
		model.addAttribute("buyername", buyuser.getUname());
		model.addAttribute("buyerusername", buyuser.getUsername());
		model.addAttribute("buyeraddress", buyuser.getUaddress());
		
		model.addAttribute("authorname", selluser.getUname());
		model.addAttribute("author", selluser.getUsername());
		model.addAttribute("item", item);
		
		return "buyitem";
	}
	
	@GetMapping("/yourrecivedpendingtrans")
	public String getYourRecivedPendingTrans(Model model, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		TransactionDAO transDAO = new TransactionDAO();
		List<Transaction> recivedTranss = transDAO.selectTransByToUserAndStatus(user.getUsername(), 3);
		model.addAttribute("transs", recivedTranss);
		model.addAttribute("tieuDeThaoTac", "CÁC GIAO DỊCH NGƯỜI KHÁC GỬI ĐẾN BẠN TRONG TRẠNG THÁI ĐANG CHỜ:");
		return "transs";
	}
	
	@GetMapping("/yoursentpendingtrans")
	public String getYourSentPendingTrans(Model model, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		TransactionDAO transDAO = new TransactionDAO();
		List<Transaction> sentTranss = transDAO.selectTransByFromUserAndStatus(user.getUsername(), 3);
		model.addAttribute("transs", sentTranss);
		model.addAttribute("tieuDeThaoTac", "CÁC GIAO DỊCH BẠN ĐÃ GỬI TRONG TRẠNG THÁI ĐANG CHỜ:");
		return "transs";
	}
	@GetMapping("/yourexpiredtrans")
	public String getYourExpiredTrans(Model model, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		TransactionDAO transDAO = new TransactionDAO();
		List<Transaction> transs = new ArrayList<>();
		List<Transaction> failedsentTranss = transDAO.selectTransByFromUserAndStatus(user.getUsername(), 1);
		List<Transaction> failedrecivedTranss = transDAO.selectTransByToUserAndStatus(user.getUsername(), 1);
		List<Transaction> successSentTranss = transDAO.selectTransByFromUserAndStatus(user.getUsername(), 2);
		List<Transaction> successRecivedTranss = transDAO.selectTransByToUserAndStatus(user.getUsername(), 2);
		transs.addAll(failedsentTranss);
		transs.addAll(failedrecivedTranss);
		transs.addAll(successSentTranss);
		transs.addAll(successRecivedTranss);
		Collections.sort(transs, Comparator.reverseOrder());
		model.addAttribute("transs", transs);
		model.addAttribute("tieuDeThaoTac", "CÁC GIAO DỊCH ĐÃ KẾT THÚC:");
		return "transs";
	}
	//Tạo giao dịch mua
	@PostMapping("/transaction/buy/{iid}")
	public String postTrans(Model model, @PathVariable String iid, HttpSession session,
			RedirectAttributes redirect,
			@RequestParam int buyamount) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		model.addAttribute("iid", iid);
		UserDAO uDAO = new UserDAO();
		ItemDAO idao = new ItemDAO();
		Item item = idao.selectItemById(Integer.parseInt(iid));
		User selluser = uDAO.selectUserByID(item.getUserID());
		User buyuser = (User) session.getAttribute("user");
//		System.out.println("mua: "+ buyuser);
//		System.out.println("ban: "+ selluser);
		if(item.getAcceptType() == 1) return "error";
		if(item.getAvaiable() <= 0) return "error";
		if(buyuser.getUsername().equals(selluser.getUsername())) return "error";
		Transaction trans = new Transaction();
		trans.setFromuser(buyuser.getUsername());
		trans.setTouser(selluser.getUsername());
		trans.setAmount(buyamount);
		trans.setItem1id(Integer.valueOf(iid));
		trans.setStatus(3);//1 = failed, 2 = success, 3 = pending
		trans.setTradetype(2);//1 = trade, 2 = buy-sell
		TransactionDAO transDAO = new TransactionDAO();
		transDAO.addTrans(trans);
		return "redirect:/yoursentpendingtrans";
	}
	//Xem giao dịch đã được tạo
	@GetMapping("/transaction/{transid}")
	public String getPendingTrans(Model model, 
			@PathVariable String transid,
			HttpSession session, RedirectAttributes redirect) {
		System.out.println("get duoc trans");
		model.addAttribute("transid".concat(transid));
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		TransactionDAO transDAO = new TransactionDAO();
		Transaction trans =  transDAO.selectTransByTransID(Integer.valueOf(transid));
		if(trans == null) return "error";
		else if(!trans.getTouser().equals(user.getUsername()) && !trans.getFromuser().equals(user.getUsername())) {
			return "error";
		}
		UserDAO uDAO = new UserDAO();
		ItemDAO idao = new ItemDAO();
		//Chỉ là giao dịch nên chỉ cần quan tâm các giá trị xuất hiện trên giao dịch nên model ko cần add trans, chỉ cần người dùng nhận được confirm
		//là được
		//Nếu là giao dịch mua thì:		
		if(trans.getTradetype() == 2) {
			Item item = idao.selectItemById(trans.getItem1id());
			User selluser = uDAO.selectUserByID(item.getUserID());
			User buyuser = uDAO.selectUserByUsername(trans.getFromuser());
			
			model.addAttribute("item", item);
			model.addAttribute("buyername", buyuser.getUname());
			model.addAttribute("buyerusername", buyuser.getUsername());
			model.addAttribute("buyeraddress", buyuser.getUaddress());
			
			model.addAttribute("authorname", selluser.getUname());
			model.addAttribute("author", selluser.getUsername());
			model.addAttribute("soluong", trans.getAmount());
			model.addAttribute("gia", trans.getAmount()*item.getSellPrice());
			model.addAttribute("transcreated", trans.getCreated());

			if(trans.getStatus() == 1) {
				model.addAttribute("trangthai", "Thất bại");
				model.addAttribute("transstatus", 1);
			}else if(trans.getStatus() == 2) {
				model.addAttribute("trangthai", "Thành công");
				model.addAttribute("transstatus", 2);
			}else {
				model.addAttribute("trangthai", "Đang chờ");
				model.addAttribute("transstatus", 3);
			}
			MessageDAO messDao = new MessageDAO();
			List<Message> messages = messDao.selectMessageByTransID(Integer.valueOf(transid));
			model.addAttribute("messages", messages);
			return "buytrans";
		}
		//Nếu là giao dịch trade:
		Item item1 = idao.selectItemById(trans.getItem1id());
		Item item2 = idao.selectItemById(trans.getItem2id());
		
		User user1 = uDAO.selectUserByUsername(trans.getTouser());
		User user2 = uDAO.selectUserByUsername(trans.getFromuser());
		
		model.addAttribute("item1", item1);
		model.addAttribute("item2", item2);
		model.addAttribute("user1", user1);
		model.addAttribute("user2", user2);
		model.addAttribute("soluong", trans.getAmount());
		model.addAttribute("transcreated", trans.getCreated());
		if(trans.getStatus() == 1) {
			model.addAttribute("trangthai", "Thất bại");
			model.addAttribute("transstatus", 1);
		}else if(trans.getStatus() == 2) {
			model.addAttribute("trangthai", "Thành công");
			model.addAttribute("transstatus", 2);
		}else {
			model.addAttribute("trangthai", "Đang chờ");
			model.addAttribute("transstatus", 3);
		}
		
		MessageDAO messDao = new MessageDAO();
		List<Message> messages = messDao.selectMessageByTransID(Integer.valueOf(transid));
		model.addAttribute("messages", messages);
		return "tradetrans";
	}
	//nếu xác nhận giao dịch
	@PutMapping("/transaction/confirm/{transid}")
	public String putConfirmTrans(Model model, @PathVariable String transid, HttpSession session,
			RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		System.out.println("vao put trans: "+transid);
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		TransactionDAO transDAO = new TransactionDAO();
		Transaction trans =  transDAO.selectTransByTransID(Integer.valueOf(transid));
		if(trans == null) return "error";
		else if(!trans.getTouser().equals(user.getUsername())) {
			return "error";
		}
		
		ItemDAO itemDAO = new ItemDAO();
		
		if(trans.getTradetype() == 2) {//=2 la mua-ban
			Item itemChanged = itemDAO.selectItemById(trans.getItem1id());
			if(itemChanged.getAvaiable() - trans.getAmount() < 0) {
				trans.setStatus(1);
				transDAO.updateTransStatus(trans);
				redirect.addFlashAttribute("thongBao","Xác nhận thất bại!");
				return "error";
			}
			itemChanged.setAvaiable(itemChanged.getAvaiable() - trans.getAmount());
			itemDAO.updateItem(itemChanged);
			trans.setStatus(2);
			transDAO.updateTransStatus(trans);
		}
		
		if(trans.getTradetype() == 1) {//=1 la trade
			Item item1Changed = itemDAO.selectItemById(trans.getItem1id());
			if(item1Changed.getAvaiable() - trans.getAmount() < 0) {
				trans.setStatus(1);
				transDAO.updateTransStatus(trans);
				redirect.addFlashAttribute("thongBao","Xác nhận thất bại!");
				return "error";
			}
			
			
			Item item2Changed = itemDAO.selectItemById(trans.getItem2id());
			if(item2Changed.getAvaiable() - trans.getAmount() < 0) {
				trans.setStatus(1);
				transDAO.updateTransStatus(trans);
				redirect.addFlashAttribute("thongBao","Xác nhận thất bại!");
				return "error";
			}
			//cap nhat item 1
			item1Changed.setAvaiable(item1Changed.getAvaiable() - trans.getAmount());
			itemDAO.updateItem(item1Changed);
			//cap nhat item 2
			item2Changed.setAvaiable(item2Changed.getAvaiable() - trans.getAmount());
			itemDAO.updateItem(item2Changed);
			//cap nhat trans
			trans.setStatus(2);
			transDAO.updateTransStatus(trans);
		}
		redirect.addFlashAttribute("thongBao","Xác nhận thành công!");
		return "redirect:/transaction/"+transid;
	}
	//Nếu từ chối giao dịch
	@PutMapping("/transaction/decline/{transid}")
	public String putDeclineTrans(Model model, @PathVariable String transid, HttpSession session, 
			RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		System.out.println("vao put trans: "+transid);
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		TransactionDAO transDAO = new TransactionDAO();
		Transaction trans =  transDAO.selectTransByTransID(Integer.valueOf(transid));
		if(trans == null) return "error";
		else if(!trans.getTouser().equals(user.getUsername())) {
			return "error";
		}
		
		trans.setStatus(1);
		transDAO.updateTransStatus(trans);
		
		redirect.addFlashAttribute("thongBao","Từ chối thành công!");
		return "redirect:/transaction/"+transid;
	}
}
