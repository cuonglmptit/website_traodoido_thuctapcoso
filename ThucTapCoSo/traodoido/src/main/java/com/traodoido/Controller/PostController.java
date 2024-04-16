package com.traodoido.Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.traodoido.DAO.CategoryDAO;
import com.traodoido.DAO.ItemDAO;
import com.traodoido.DAO.UserDAO;
import com.traodoido.Model.Category;
import com.traodoido.Model.Item;
import com.traodoido.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
	@GetMapping("/yourposts")
	public String getTrangChu(Model model, HttpSession session, RedirectAttributes redirect) {
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
			return "redirect:/dangnhap";
		}
		ItemDAO idao = new ItemDAO();
		List<Item> items = idao.selectItemsOfUserByUID(user.getUid());
		model.addAttribute("items", items);
		return "posts";
	}
	
	@GetMapping("/post/{iid}")
	public String getPostBai(Model model, @PathVariable String iid, HttpSession session, RedirectAttributes redirect) {
		model.addAttribute("iid", iid);
		System.out.println("GOI post/:" + iid + "");
		Item item = new Item();
		CategoryDAO catDAO =  new CategoryDAO();
		List<Category> categories = catDAO.selectAllCategories();
		try {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/dangnhap";
			}
			if (iid != null) {
				if(Integer.valueOf(iid) >= 0) {
					ItemDAO idao = new ItemDAO();
					item = idao.selectItemByIdAndUser(Integer.valueOf(iid), user.getUid());
					System.out.println(user.getUid()+":"+ item.getUserID());
					if(user.getUid() != item.getUserID()) return "error";	
//		            System.out.println(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("item", item);
		model.addAttribute("categories", categories);
		return "post";
	}

	@PostMapping("/post/save/{iid}")
	public String upoadBaiPost(Model model, Item item, @PathVariable String iid, HttpSession session, RedirectAttributes redirect,
			@RequestParam("fimg1") MultipartFile img1, @RequestParam("fimg2") MultipartFile img2,
			@RequestParam("fimg3") MultipartFile img3) {
		User user = (User) session.getAttribute("user");
		System.out.println("post: "+item);
		if(Integer.valueOf(iid) > 0) {
//			System.out.println(user.getUid()+":"+ item.getUserID());
			if(user.getUid() != item.getUserID()) return "error";
		}
		try {
			//Gán người dùng đăng bài (item) này
			item.setUserID(user.getUid());
			//Xử lý lưu các ảnh và gán đường dẫn
			List<MultipartFile> dsImg = new ArrayList<>();
			dsImg.add(img1); dsImg.add(img2); dsImg.add(img3);
			for (int i = 0; i < dsImg.size(); i++) {
				MultipartFile img = dsImg.get(i);
				if (!img.isEmpty() && img != null) {
					String uid = user.getUid()+"_";
					String originalFilename = img.getOriginalFilename();
					String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
					String newFilename = uid + System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
					Path path = Paths.get("src/main/resources/static/uploads/");
		            InputStream inputStream = img.getInputStream();
		            Files.copy(inputStream, path.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
		            System.out.println(newFilename);
		            inputStream.close();
		            item.setImgByNumber(i+1, "/uploads/"+newFilename);
				}
			}
			//Xử lý gán ngày tạo
			Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
			item.setCreateddate(currentDate);
//			System.out.println(item);
			ItemDAO idao = new ItemDAO();
			idao.addItem(item);
			redirect.addFlashAttribute("thongBao", "Đăng bài thành công!");
		} catch (Exception e) {
			redirect.addFlashAttribute("thongBao", "Đăng bài thất bại!");
			e.printStackTrace();
		}
		return "redirect:/yourposts";
	}
	
	@PutMapping("/post/save/{iid}")
	public String suaBaiPost(Model model, Item item, @PathVariable String iid, HttpSession session, RedirectAttributes redirect,
			@RequestParam("fimg1") MultipartFile img1, @RequestParam("fimg2") MultipartFile img2,
			@RequestParam("fimg3") MultipartFile img3) {
		User user = (User) session.getAttribute("user");
		System.out.println("put: "+item);
		if(Integer.valueOf(iid) > 0) {
			System.out.println(user.getUid()+":"+ item.getUserID());
			if(user.getUid() != item.getIid()) {
				System.out.println(user.getUid()+":"+ item.getUserID());
				if(user.getUid() != item.getUserID()) return "error";
			}
		}
		try {
			//Xử lý lưu các ảnh và gán đường dẫn
			List<MultipartFile> dsImg = new ArrayList<>();
			dsImg.add(img1); dsImg.add(img2); dsImg.add(img3);
			for (int i = 0; i < dsImg.size(); i++) {
				MultipartFile img = dsImg.get(i);
				if (!img.isEmpty() && img != null) {
					String uid = user.getUid()+"_";
					String originalFilename = img.getOriginalFilename();
					String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
					String newFilename = uid + System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
					Path path = Paths.get("src/main/resources/static/uploads/");
		            InputStream inputStream = img.getInputStream();
		            Files.copy(inputStream, path.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
		            System.out.println(newFilename);
		            inputStream.close();
		            item.setImgByNumber(i+1, "/uploads/"+newFilename);
				}
			}
			//Xử lý gán ngày tạo
			Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
			item.setCreateddate(currentDate);
//			System.out.println(item);
			ItemDAO idao = new ItemDAO();
			idao.updateItem(item);
			redirect.addFlashAttribute("thongBao", "Sửa bài thành công!");
		} catch (Exception e) {
			redirect.addFlashAttribute("thongBao", "Sửa bài thất bại!");
			e.printStackTrace();
		}
		return "redirect:/post/{iid}";
	}
	
	@DeleteMapping("/post/delete/{iid}")
	public String xoaBaiPost(Model model, Item item, @PathVariable String iid, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		System.out.println("delete: "+item);
		if(Integer.valueOf(iid) > 0) {
			System.out.println(user.getUid()+":"+ item.getUserID());
			if(user.getUid() != item.getIid()) {
				System.out.println(user.getUid()+":"+ item.getUserID());
				if(user.getUid() != item.getUserID()) return "error";
			}
		}
		try {
			ItemDAO idao = new ItemDAO();
			idao.deleteItem(item.getIid());
			redirect.addFlashAttribute("thongBao", "Xóa bài thành công!");
		} catch (Exception e) {
			redirect.addFlashAttribute("thongBao", "Xóa bài thất bại!");
			e.printStackTrace();
			return "error";	
		}
		return "redirect:/yourposts";
	}
}
