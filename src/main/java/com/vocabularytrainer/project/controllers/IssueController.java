package com.vocabularytrainer.project.controllers;

import com.vocabularytrainer.project.db.IssueRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*; // Restcontroller, Requestmapping, RequestParameter, GetMapping ...

// entity IssueReport Data
import com.vocabularytrainer.project.entity.IssueReport;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ExampleController
 *
 * @author ASD-Morning-4
 *
 */

@Controller
public class IssueController {

    /* setup DataBase Repository Interface */

    IssueRepository issueRepository;
    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    /* Hello World Example: Writing Plain Text */

    /*
    @RequestMapping("/")
    @ResponseBody
    String get() {
        return "Hello World!";
    }
    */

    /* Example Loading Thymeleaf */

    // Load Our welcome page
    @GetMapping("/index")
    public String getIndex(Model model) {
        return "index/welcome";
    }

    // Example with a form
    @GetMapping("/issuereport") //
    public String getReport(Model model) {

        model.addAttribute("issuereport", new IssueReport());
        return "issues/issuereport_form";
    }

    @PostMapping("/issuereport") //
    public String submitReport(IssueReport issueReport, Model model) { // here we also want to handle the data from the form

        IssueReport result = this.issueRepository.save(issueReport);
        model.addAttribute("submitted", true); // Feedback for user upon retrieving the form data
                                                     // The template will show some kind of confirming msg
                                                     // will only show after form was submitted
        model.addAttribute("issuereport", result); // create another variable for our html frontent path

        // for preventing sending formular again and again:
        return "redirect:/issuereport"; // return "issues/issuereport_form";
    }

    @GetMapping("/issues")
    public String getIssues(Model model) {

        //model.addAttribute("issues", this.issueRepository.findAllButPrivate());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //System.out.println("User name: " + userDetails.getUsername());

        // pass as an argument, show table entries only with this email
        model.addAttribute("issues", this.issueRepository.findAllByEmail( ( userDetails.getUsername() ) ));

        return "issues/issuereport_list";
    }

    // test@mail.com
    // pass the user name as for the


    /* Example Login Page Handler */
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }
}
