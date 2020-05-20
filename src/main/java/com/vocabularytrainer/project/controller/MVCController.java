package com.vocabularytrainer.project.controller;

import com.vocabularytrainer.project.CSVParser.CSVWriter;
import com.vocabularytrainer.project.db.VocabularyEntries;
import com.vocabularytrainer.project.db.VocabularyRepository; // Repository Interface

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Controller
public class MVCController {

    /* Setup access Database Repository Interface */
    VocabularyRepository vocabularyRepository;
    public MVCController(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    /* Welcome Page */
    @GetMapping("/")
    public String root() {
        return "index";
    }

    /* User Interface: Dashboard */
    @GetMapping("/user")
    public String userIndex(Model model) {

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/index";
    }

    /* User StudyInterface in German*/
    @GetMapping("/user/studyInterfaceGerman")
    public String userStudyInterfaceGerman(Model model) {

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/study_interface_german";
    }

    /* User StudyInterface in English */
    @GetMapping("/user/studyInterfaceEnglish")
    public String userStudyInterfaceEnglish(Model model) {

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/study_interface_english";
    }


    /* General StudyInterface where user selects language */
    @GetMapping("/user/studyInterface")
    public String userStudyInterface(Model model) {

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("current_user", userDetails.getUsername());

        return "user/study_interface";
    }


    /* Show the All vocabularies in edit_vocab.html*/
    @GetMapping("user/editvoc")
    public String getUserEditVocabulary(Model model) {

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // get the Query to show current vocabulary entries of user x
        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/edit_vocab";
    }

    /* Edit specific vocabulary entry based on ID */
    @GetMapping("user/editvoc/edit/{id}")
    public String showEditProductPage(@PathVariable(name = "id") int id, Model model) {

        model.addAttribute("submitted", false);

        VocabularyEntries vocabularyEntries = this.vocabularyRepository.getEntryBasedOnId(id);
        //System.out.println(vocabularyEntries.getId());

        model.addAttribute("editvoc", vocabularyEntries);

        return "user/edit_vocab_entry";
    }

    /* Submit edited vocabulary entry based on ID */
    @PostMapping("user/editvoc/edit/{id}")
    public String submitEditedVocabularyEntry(@PathVariable(name = "id") int id, VocabularyEntries vocabularyEntries, Model model)
    {
        model.addAttribute("submitted", true);

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        vocabularyEntries.setUser(userDetails.getUsername());

        // save result in our Repository Interface
        VocabularyEntries result = this.vocabularyRepository.save(vocabularyEntries);

        // Thymeleaf-variable for "post-form" - add it
        model.addAttribute("editvoc", result);

        return "user/edit_vocab_entry";
    }

    @GetMapping("/user/editvoc/delete/{id}")
    public String deleteVocabularyEntry(@PathVariable(name = "id") int id, Model model)
    {
        // tell the thymeleaf which user is logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        VocabularyEntries vocabularyEntries = this.vocabularyRepository.getEntryBasedOnId(id);

        //System.out.println(vocabularyEntries.getId());

        this.vocabularyRepository.delete(vocabularyEntries);

        //this.vocabularyRepository.deleteById();

        model.addAttribute("overview", this.vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername()));

        return "user/edit_vocab";
    }

    /* Show the Form and Let the user enter stuff */
    @GetMapping("/user/addvoc")
    // Model -> Thymeleaf model to access html "variables"
    public String getUserAddVocabulary(Model model) {


        VocabularyEntries vocabularyEntries = new VocabularyEntries();


        // Thymeleaf-variable for "get-form"
        model.addAttribute("addvoc", vocabularyEntries);

        return "user/addvoc_form";
    }


    /* Submit Data from Form using POST */
    @PostMapping("/user/addvoc")
    public String submitUserAddVocabulary(VocabularyEntries vocabularyEntries, Model model) {

        model.addAttribute("submitted", true);

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // set current user column
        vocabularyEntries.setUser(userDetails.getUsername());

        // save result in our Repository Interface
        VocabularyEntries result = this.vocabularyRepository.save(vocabularyEntries);

        // Thymeleaf-variable for "post-form" - add it
        model.addAttribute("addvoc", result);

        /// TODO: Fix redirecting
        return "user/addvoc_form";
    }

    /* Login Page */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /* Access Denied Page */
    @GetMapping("/request-denied")
    public String accessDenied() {
        return "access-denied-page";
    }

    @GetMapping("user/edit_tag/edit/{id}")
    public String showEditTagPage(@PathVariable(name = "id") int id, Model model) {

        model.addAttribute("submitted", false);

        VocabularyEntries vocabularyEntries = this.vocabularyRepository.getEntryBasedOnId(id);

        model.addAttribute("edit_tag", vocabularyEntries);

        return "user/edit_tag";
    }


    @PostMapping("user/edit_tag/edit/{id}")
    public String submitEditedTagEntry(@PathVariable(name = "id") int id, VocabularyEntries vocabularyEntries, Model model)
    {
        model.addAttribute("submitted", true);

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        vocabularyEntries.setUser(userDetails.getUsername());

        // save result in our Repository Interface
        VocabularyEntries result = this.vocabularyRepository.save(vocabularyEntries);

        // Thymeleaf-variable for "post-form" - add it
        model.addAttribute("edit_tag", result);

        return "user/edit_tag";
    }

    @GetMapping("user/edit_rating/edit/{id}")
    public String showEditRatingPage(@PathVariable(name = "id") int id, Model model) {

        model.addAttribute("submitted", false);

        VocabularyEntries vocabularyEntries = this.vocabularyRepository.getEntryBasedOnId(id);

        model.addAttribute("edit_rating", vocabularyEntries);

        return "user/edit_rating";
    }

    @PostMapping("user/edit_rating/edit/{id}")
    public String submitEditedRatingEntry(@PathVariable(name = "id") int id, VocabularyEntries vocabularyEntries, Model model)
    {
        model.addAttribute("submitted", true);

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        vocabularyEntries.setUser(userDetails.getUsername());

        // save result in our Repository Interface
        VocabularyEntries result = this.vocabularyRepository.save(vocabularyEntries);

        // Thymeleaf-variable for "post-form" - add it
        model.addAttribute("edit_rating", result);

        return "user/edit_rating";
    }

    //change language
    @GetMapping("/?lang=de")
    public String getGermanStartPage() {
        return "?lang=de";
    }
    @GetMapping("/?lang=en")
    public String getEnglishStartPage() {
        return "?lang=en";
    }
    @GetMapping("/?lang=fr")
    public String getFrenchStartPage() {
        return "?lang=fr";
    }

    @GetMapping("/user/?lang=de")
    public String getGermanPage() {
        return "user/?lang=de";
    }
    @GetMapping("/user/?lang=en")
    public String getEnglishPage() {
        return "user/?lang=en";
    }
    @GetMapping("/user/?lang=fr")
    public String getFrenchPage() {
        return "user/?lang=fr";
    }

    /* Export and Download vocabularies as CSV */
    @GetMapping("user/export_vocabularies")
    public ResponseEntity<?> export()
    {
        // With ResponseEntity<> we can manipulate the HTTP Response
        // It represents the whole HTTP response: status code, headers, and body

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String filepath = "src/main/resources/templates/user/csv-output/";
        String filename = filepath + "export_vocabularies_" + userDetails.getUsername() + ".csv";

        List<Integer> ids = Arrays.asList(); // empty list means it will output all ids

        // Generate the CSV File
        File file = CSVWriter.writeAllEntriesToCSV(this.vocabularyRepository, userDetails, filename, ids);

        if(file != null)
        {
            try {

                // Create a Response HTTPHeader
                // Represents HTTP request and response headers, mapping string header names to list of string values
                HttpHeaders headers = new HttpHeaders();

                InputStreamResource attachedFile = new InputStreamResource(new FileInputStream(file.getPath()));

                // .add: adds a header value
                // with Content-Disposition we can say, the HTTPHeader should be treated as Attachment that is downloaded+saved
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export_vocabularies.csv");

                // Create HTTP 200 OK Response with File
                return ResponseEntity.ok().headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(attachedFile);

            } catch (Exception ex) {


                // Return HTML Page: Message with exception
                String ret = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"> <title>FAILED CSV</title>" +
                        "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\">" +
                        "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>" +
                        "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js\"></script>" +
                        "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script>" +
                        "</head><body><div><div class=\"alert alert-danger\">" +
                        ex.getMessage() +
                        "</div><p align=\"center\"><a href=\"/user\">back</a></p></div></body></html>";

                // Return HTML Page: Exception Message if fails
                return new ResponseEntity<>(ret, HttpStatus.NO_CONTENT);
            }
        }
        else
        {
            // Return HTML Page: Message, that Export File couldn't create
            String ret = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"> <title>FAILED CSV</title>" +
                    "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\">" +
                    "<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"></script>" +
                    "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js\"></script>" +
                    "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script>" +
                    "</head><body><div><div class=\"alert alert-danger\">There was an Error while generating CSV. Please contact administrator." +
                    "</div><p align=\"center\"><a href=\"/user\">back</a></p></div></body></html>";

            return new ResponseEntity<>(ret, HttpStatus.NOT_FOUND);
        }
    }

    /* Importing CSV */
    /// TODO: GetMapping for import_vocab.html
    @GetMapping("user/import")
    public String showImportView()
    {
        return "user/import_vocab";
    }

    /// TODO: PostMapping for import_vocab.html - the Read-Process (import CSV to Database)
}