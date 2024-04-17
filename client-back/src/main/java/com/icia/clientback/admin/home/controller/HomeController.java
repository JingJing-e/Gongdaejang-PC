package com.icia.clientback.admin.home.controller;

import com.icia.clientback.admin.home.service.HomeService;
import com.icia.clientback.admin.menu.controller.AdminMenuController;
import com.icia.clientback.admin.menu.dto.AdminMenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.OutputStream;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;
    @Autowired
    AdminMenuController adminMenuController;

    @GetMapping("/")
    public String adminHome (Model model) {
        List<AdminMenuDto> adminMenuList = adminMenuController.getAdminMenu();
        model.addAttribute("menu", adminMenuList);
        return "adminHome";
    }

    @PostMapping("/login/shutdown/{turnoff}")
    public ResponseEntity<?> shutdown(@PathVariable String turnoff) {
        Runtime runtime = Runtime.getRuntime();
        try{
            Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
            OutputStream os = process.getOutputStream();
            if(turnoff.equals("shutdown")){
                os.write("shutdown -s -f -t 0 \n\r".getBytes());
            }else if(turnoff.equals("restart")){
                os.write("shutdown -r -f -t 0 \n\r".getBytes());
            }
            os.close();
            process.waitFor();
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("종료");
    }
}
