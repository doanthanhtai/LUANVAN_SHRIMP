package com.pondmanage.controller;

import com.pondmanage.dto.AccountDTO;
import com.pondmanage.dto.PasswordDTO;
import com.pondmanage.model.Account;
import com.pondmanage.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public String loginPage(){
        return "/accounts/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("account", new AccountDTO());
        return "/accounts/register";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid AccountDTO accountDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("exception","Email đã tôn tại!");
            return "redirect:/register";
        }
        try {
            accountService.register(accountDTO);
            return "redirect:/login";
        } catch (Exception e){
            model.addAttribute("exception","Email đã tôn tại!");
            return "redirect:/register";
        }
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model){
        model.addAttribute("passwordDTO", new PasswordDTO());
        return "/accounts/changePassword";
    }

    @PostMapping("/changePassword")
    public String handleChangePassword(@Valid PasswordDTO passwordDTO, BindingResult result){
        if (result.hasErrors()) return "redirect:/changePassword";
        accountService.changePassword(passwordDTO);
        return "redirect:/logout";
    }

    @GetMapping("/reset-password")
    public String handleResetPassword(@Valid PasswordDTO passwordDTO, BindingResult result){
        return "accounts/resetPassword";
    }

    @GetMapping("/confirmDeleteAccount")
    public String confirmDeleteAccount(Model model){
        model.addAttribute("account",accountService.getCurrentAccount());
        return "/accounts/confirmDeleteAccount";
    }
    @PostMapping("/deleteAccount")
    public String handleDeleteAccount(@Valid AccountDTO accountDTO, BindingResult result){
        if (result.hasErrors()) return "redirect:/confirmDeleteAccount";
        boolean resultDelete = accountService.deleteAccount(accountDTO);
        if (resultDelete){
            return "redirect:/login";
        }else {
            return "redirect:/confirmDeleteAccount";
        }

    }
}
