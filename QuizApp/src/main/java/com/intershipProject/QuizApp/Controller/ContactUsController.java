package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.ContactUs;
import com.intershipProject.QuizApp.Model.User;
import com.intershipProject.QuizApp.Repository.ContactUsRepo;
import com.intershipProject.QuizApp.Repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/contact-us")
public class ContactUsController {
    @Autowired
    private ContactUsRepo contactUsRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public ModelAndView showContactForm(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/user/contact-us");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView processContactUsForm(@RequestParam("subject") String subject,
                                             @RequestParam("message") String message,
                                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);


        ContactUs contactUs = new ContactUs();
        contactUs.setSubject(subject);
        contactUs.setMessage(message);
        contactUs.setUser(loggedInUser);
        contactUsRepo.save(contactUs);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/user/contact-us");
        modelAndView.addObject("successMessage", "Message sent successfully!");
        return modelAndView;
    }

}
