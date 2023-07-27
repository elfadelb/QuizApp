package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.User;
import com.intershipProject.QuizApp.Repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class AuthController {
    @Autowired
    private UserRepo userRepo;
    @PersistenceContext
    private EntityManager entityManager;
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/auth/login");
        return modelAndView;
    }
    @PostMapping("/login-user")
    @Transactional

    public ModelAndView loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        RedirectView redirectView;
        ModelAndView modelAndView = new ModelAndView();
        User existingUser = userRepo.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
            String sessionId = UUID.randomUUID().toString();
            HttpSession session = request.getSession();
            session.setAttribute("sessionId", sessionId);

            existingUser.setSessionId(sessionId);
            userRepo.save(existingUser); // Update the session ID in the database


            if (existingUser.getRole() == 1) {
                redirectView = new RedirectView("user-dashboard", true);
            } else {
                redirectView = new RedirectView("admin-dashboard", true);
            }
            modelAndView.setView(redirectView);
            return modelAndView;
        }
        redirectView = new RedirectView("login", true);
        modelAndView.setView(redirectView);
        return modelAndView;
    }


//    @PostMapping("/login-user")
//    @Transactional
//    public ModelAndView loginUser(@RequestParam String username , @RequestParam String password , HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//        User existingUser = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
//                .setParameter("username", username)
//                .getSingleResult();
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
//            if (existingUser.getRole() == 1) {
//                String sessionId = UUID.randomUUID().toString();
//                HttpSession session = request.getSession();
//                session.setAttribute("sessionId", sessionId);
//                modelAndView.setViewName("view/html/user/dashboard");
//                return modelAndView;
//            } else if (existingUser.getRole() == 0) {
//                modelAndView.setViewName("view/html/admin/dashboard");
//                return modelAndView;
//            }
//        }
//        modelAndView.setViewName("view/html/auth/login");
//        return modelAndView;
//    }

    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/auth/register");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        RedirectView redirectView;
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        ModelAndView modelAndView = new ModelAndView();
        redirectView = new RedirectView("login", true);
        modelAndView.setView(redirectView);
        return modelAndView;
    }

    @RequestMapping("/register-user")
    @Transactional
    public ModelAndView registerUser(@RequestParam String firstName,
                                     @RequestParam String lastName,
                                     @RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String gender,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date bithDate,
                                     @RequestParam String email,
                                     @RequestParam(required = false) boolean agreeTerms,
                                     HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();


        User existingUser = userRepo.findByUsername(username);
        if(existingUser != null){
            modelAndView.addObject("message", "Username already exists. Please choose a different username.");
            modelAndView.setViewName("view/html/auth/register");
            return modelAndView;
        }

        // Create a new user object and set its properties
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setBithDate(bithDate);



        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setGender(gender);
        // Parse the birthDate string to a Date object (you might need to handle date formatting/parsing accordingly)
        // newUser.setBirthDate(...);
        newUser.setEmail(email);
        newUser.setRole(1); // Assuming 1 represents a regular user role

        modelAndView.addObject(newUser);

        // Save the new user to the database
        userRepo.save(newUser);

        // Redirect to the login page after successful registration
        RedirectView redirectView = new RedirectView("login", true);
        modelAndView.setView(redirectView);
        return modelAndView;
    }

}
