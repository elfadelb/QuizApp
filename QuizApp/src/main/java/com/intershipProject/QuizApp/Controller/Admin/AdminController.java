package com.intershipProject.QuizApp.Controller.Admin;

import com.intershipProject.QuizApp.Model.*;
import com.intershipProject.QuizApp.Repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AnnouncementRepo announcementRepo;
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private FAQRepo faqRepo;
    @Autowired
    private ContactUsRepo contactUsRepo;

    // ---DASHBOARD---

    @GetMapping(value = "/admin-dashboard")
    public ModelAndView dashboard(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/dashboard");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        // Total Announcement
        long totalAnnouncement = announcementRepo.count();
        modelAndView.addObject("totalAnnouncement", totalAnnouncement);

        // Total Question
        long totalQuestion = questionRepo.count();
        modelAndView.addObject("totalQuestion", totalQuestion);


        // Find All Course
        List<Course> courses = courseRepo.findAll();
        modelAndView.addObject("courses", courses);
        // Total Course
        long totalCourse = courseRepo.count();
        modelAndView.addObject("totalCourse", totalCourse);

        // Announcement URL
        String announcementListUrl = "/admin-announcement-list";
        modelAndView.addObject("announcementListUrl", announcementListUrl);

        //Last 5 Announcement add by created date
        List<Announcement> latestAnnouncements = announcementRepo.findTop5ByOrderByCreatedDateDesc();
        modelAndView.addObject("latestAnnouncements", latestAnnouncements);

        //Last 5 Course Add by id
        List<Course> latestCourses = courseRepo.findTop5ByOrderByIdDesc();
        modelAndView.addObject("latestCourses", latestCourses);


        return modelAndView;
    }

    // ---/DASHBOARD/---


    // ---QUESTİON---

    @GetMapping("/admin-create-question")
    public ModelAndView create_question(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/create-question");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }

        return modelAndView;
    }

    @GetMapping("/admin-update-question/{id}")
    public ModelAndView update_question(HttpServletRequest request , @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/update-question");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);
        Question question = questionRepo.findById(id).get();

        modelAndView.addObject("user", loggedInUser);
        modelAndView.addObject("question" , question);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }

        return modelAndView;
    }
    @GetMapping("/admin-show-question")
    public ModelAndView showQuestion(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/show-question");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);
        List<Question> questions = questionRepo.findAll();

        modelAndView.addObject("user", loggedInUser);
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }
    @GetMapping("/admin-show-course-question/{id}")
    public ModelAndView showQuestionById(HttpServletRequest request ,@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/show-question");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);
        List<Question> questions = questionRepo.findAllByCourseId(id);

        modelAndView.addObject("user", loggedInUser);
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    // ---/QUESTION/---


    // ---COURSE---

    @GetMapping("/admin-create-course")
    public ModelAndView create_course(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/create-course");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }

        return modelAndView;
    }

    @GetMapping("/admin-update-course/{id}")
    public ModelAndView update_course(HttpServletRequest request , @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/update-course");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);
        Course course = courseRepo.findById(id).get();

        modelAndView.addObject("user", loggedInUser);
        modelAndView.addObject("course" , course);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }

        return modelAndView;
    }
    @GetMapping("/admin-show-course")
    public ModelAndView showCourse(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/show-course");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);
        List<Course> courses = courseRepo.findAll();

        modelAndView.addObject("user", loggedInUser);
        modelAndView.addObject("courses", courses);

        return modelAndView;
    }

    // ---/COURSE/---


    // ---ANNOUNCEMENT---

    @GetMapping(value = "/admin-create-announcement")
    public ModelAndView createAnnouncementPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/create-announcement");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }
        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }
        return modelAndView;
    }

    @GetMapping(value = "/admin-announcement-list")
    public ModelAndView announcementList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/announcement");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        List<Announcement> announcements = announcementRepo.findAll();
        modelAndView.addObject("announcements", announcements);

        return modelAndView;
    }

    @PostMapping(value = "/admin-announcement-delete/{id}")
    public ModelAndView deleteAnnouncement(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-announcement-list");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);


        Announcement announcement = announcementRepo.findById(id).orElse(null);
        if (announcement != null) {
            announcementRepo.delete(announcement);
            String message = "Announcement deleted successfully.";
            request.getSession().setAttribute("message", message);
        } else {
            String errorMessage = "Announcement not found.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        }

        return modelAndView;
    }




    @GetMapping("/admin-update-announcement/{id}")
    public ModelAndView updateAnnouncement(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/update-announcement");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        Announcement announcement = announcementRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid announcement Id: " + id));

        modelAndView.addObject("announcement", announcement);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }
        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }

        return modelAndView;
    }

    @PostMapping("/update-announcement/{id}")
    public ModelAndView updateAnnouncement(@PathVariable("id") Long id ,
                                           @ModelAttribute Announcement announcement,
                                           HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-announcement-list");

        announcement.setId(id);
        Announcement updatedAnnouncement = announcementRepo.save(announcement);

        if (updatedAnnouncement == null) {
            String errorMessage = "Failed to update the announcement. Please try again.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        } else {
            String message = "Announcement updated successfully.";
            request.getSession().setAttribute("message", message);
        }

        return modelAndView;
    }


    // ---/ANNOUNCEMENT/---



    // ---  / FAQ / ---




    @GetMapping(value = "/admin-create-faq")
    public ModelAndView createFAQ(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/create-faq");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }
        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }
        return modelAndView;
    }

    @GetMapping(value = "/admin-faq-list")
    public ModelAndView listFAQ(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/faq");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        List<FAQ> faqs = faqRepo.findAll();

        modelAndView.addObject("faqs", faqs);

        return modelAndView;
    }

    @PostMapping(value = "/admin-faq-delete/{id}")
    public ModelAndView deleteFAQ(@PathVariable Long id,
                                HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-faq-list");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);


        FAQ faq = faqRepo.findById(id).orElse(null);
        if (faq != null) {
            faqRepo.delete(faq);
            String message = "FAQ deleted successfully.";
            request.getSession().setAttribute("message", message);
        } else {
            String errorMessage = "FAQ not found.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        }

        return modelAndView;
    }


    @GetMapping("/admin-update-faq/{id}")
    public ModelAndView updateFAQ(@PathVariable("id") Long id,
                                HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/update-faq");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        FAQ faq = faqRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid faq " +
                        "Id: " + id));

        modelAndView.addObject("faq", faq);

        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        if (errorMessage != null) {
            modelAndView.addObject("errorMessage", errorMessage);
            request.getSession().removeAttribute("errorMessage");
        }
        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }

        return modelAndView;
    }

    @PostMapping("/update-faq/{id}")
    public ModelAndView updateAnnouncement(@PathVariable("id") Long id ,
                                           @ModelAttribute FAQ faq,
                                           HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-faq-list");

        faq.setId(id);
        FAQ updateFAQ = faqRepo.save(faq);

        if (updateFAQ == null) {
            String errorMessage = "Failed to update the faq. Please try again.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        } else {
            String message = "FAQ updated successfully.";
            request.getSession().setAttribute("message", message);
        }

        return modelAndView;
    }



    // --- / end- FAQ / ----


    // ---  Contact - Us  ---

    @GetMapping(value = "/admin-contactUs-list")
    public ModelAndView listContactUs(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view/html/admin/show-message");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        User loggedInUser = userRepo.findBySessionId(sessionId);

        modelAndView.addObject("user", loggedInUser);

        List<ContactUs> contactUsList = contactUsRepo.findAll();

        modelAndView.addObject("contactUsList", contactUsList);

        return modelAndView;
    }




    // --- / Contact - Us ---






}
