package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.Announcement;
import com.intershipProject.QuizApp.Repository.AnnouncementRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class AnnouncementController {
    @Autowired
    private AnnouncementRepo announcementRepo;

    @GetMapping("/announcements")
    public List<Announcement> getAnnouncements() {
        return announcementRepo.findAll();
    }

    @PostMapping("/create-announcement")
    public ModelAndView createAnnouncement(@ModelAttribute Announcement announcement, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-create-announcement");

        if (announcementRepo.save(announcement) == null) {
            String errorMessage = "Failed to save the announcement. Please try again.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        } else {
            String message = "Announcement saved successfully.";
            request.getSession().setAttribute("message", message);
        }

        return modelAndView;
    }

//    @PutMapping("/update-announcement/{id}")
//    public ModelAndView updateAnnouncement(@RequestBody Announcement announcement, @PathVariable Long id, HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/admin-update-announcement");
//
//        Announcement existingAnnouncement = announcementRepo.findById(id).orElse(null);
//        if (existingAnnouncement != null) {
//            existingAnnouncement.setTitle(announcement.getTitle());
//            existingAnnouncement.setContent(announcement.getContent());
//            announcementRepo.save(existingAnnouncement);
//
//            String message = "Announcement updated successfully.";
//            request.getSession().setAttribute("message", message);
//        } else {
//            String errorMessage = "Announcement not found.";
//            request.getSession().setAttribute("errorMessage", errorMessage);
//        }
//
//        return modelAndView;
//    }




    @DeleteMapping(value = "/delete-announcement/{id}")
    public ModelAndView deleteAnnouncement(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-announcement-list");

        Announcement deletedAnnouncement = announcementRepo.findById(id).orElse(null);
        announcementRepo.delete(deletedAnnouncement);
        return modelAndView;
    }




}
