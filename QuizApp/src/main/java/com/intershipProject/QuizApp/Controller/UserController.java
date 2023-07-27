package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.User;
import com.intershipProject.QuizApp.Repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserController {
    @Autowired
    private UserRepo userRepo;
//    @GetMapping(value = "/admin-dashboard")
//    public ModelAndView getPage(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("view/html/admin/dashboard");
//        return modelAndView;
//    }

    @PostMapping("/register-users")
    public String registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorBuilder = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorBuilder.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            return "Validation errors: " + errorBuilder.toString();
        }

        // Hash the password using BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepo.save(user);
        return "User registered successfully.";
    }

    @GetMapping("/change-password")
    public ModelAndView getChangePassword(){
        ModelAndView modelAndView = new ModelAndView("view/html/user/change-password");
        return modelAndView;
    }

    @PostMapping("/change-password")
    @Transactional
    public ModelAndView changePassword(@RequestParam String currentPassword,
                                       @RequestParam String newPassword,
                                       HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        // Session
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);
        // Matched current password with password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordMatches = passwordEncoder.matches(currentPassword, loggedInUser.getPassword());
        // if not matches, it give error message
        if(!passwordMatches){
            modelAndView.addObject("errorMessage", "Current password is incorrect.");
            modelAndView.setViewName("view/html/user/change-password");
            return modelAndView;
        }
        if(passwordMatches){
            String hashedNewPassword = passwordEncoder.encode(newPassword);
            loggedInUser.setPassword(hashedNewPassword);
            userRepo.save(loggedInUser);
        }
        RedirectView redirectView = new RedirectView("/login", true);
        modelAndView.setView(redirectView);
        return modelAndView;
    }

}
