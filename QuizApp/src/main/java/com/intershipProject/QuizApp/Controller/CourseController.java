
package com.intershipProject.QuizApp.Controller;

import com.intershipProject.QuizApp.Model.Course;
import com.intershipProject.QuizApp.Repository.CourseRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@RestController
public class CourseController {
    @Autowired
    private CourseRepo courseRepo;

    @PostMapping(value = "/create-course", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView saveCourse(@ModelAttribute Course course, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-create-course");

        // Perform manual validation checks
        if (course.getName().isEmpty()) {
            String errorMessage = "Invalid course data. Please check your input.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        if (courseRepo.save(course) == null) {
            String errorMessage = "Failed to save the course. Please try again.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        } else {
            String message = "Course saved successfully.";
            request.getSession().setAttribute("message", message);
        }
        return modelAndView;
    }


    @PostMapping("/update-course")
    public ModelAndView updateCourse(@ModelAttribute Course course, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-update-course/" + course.getId());

        Optional<Course> optionalCourse = courseRepo.findById(course.getId());
        if (optionalCourse.isEmpty()) {
            String errorMessage = "Invalid course data. Please check your input.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        Course updatedCourse = optionalCourse.get();

        // Perform validation checks
        if (course.getName().isEmpty()) {
            String errorMessage = "Invalid course data. Please check your input.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        // Update the fields of the existing course object with the new values
        updatedCourse.setName(course.getName());

        if (courseRepo.save(updatedCourse) == null) {
            String errorMessage = "Failed to update the course. Please try again.";
            request.getSession().setAttribute("errorMessage", errorMessage);
        } else {
            String message = "Course Updated successfully.";
            request.getSession().setAttribute("message", message);
        }

        return modelAndView;
    }


    @GetMapping("/delete-course/{id}")
    public ModelAndView deleteCourse(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin-show-course");

        Optional<Course> optionalCourse = courseRepo.findById(id);
        if (optionalCourse.isEmpty()) {
            String errorMessage = "Invalid course ID. Please check the ID.";
            request.getSession().setAttribute("errorMessage", errorMessage);
            return modelAndView;
        }

        Course course = optionalCourse.get();
        courseRepo.delete(course);

        String message = "Course deleted successfully.";
        request.getSession().setAttribute("message", message);

        return modelAndView;
    }


}
