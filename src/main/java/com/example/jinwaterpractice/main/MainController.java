package com.example.jinwaterpractice.main;

import com.example.jinwaterpractice.user.CurrentUser;
import com.example.jinwaterpractice.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/", "/main", "/home", "/index"})
    public String home(@CurrentUser User user, Model model){
        String page = (user == null)
                ? "redirect:/login"
                : "main";
        if(user!= null) {
            model.addAttribute("user", user);
        }
        return page;
    }
}
