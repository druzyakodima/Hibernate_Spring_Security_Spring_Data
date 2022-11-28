package com.example.lesson7_spring_data.controllers;

import com.example.lesson7_spring_data.entity.user_entity.UserRepr;
import com.example.lesson7_spring_data.exception.BadRequestException;
import com.example.lesson7_spring_data.exception.NotFoundException;
import com.example.lesson7_spring_data.service.role_service.RoleService;
import com.example.lesson7_spring_data.service.user_service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private RoleService roleService;


    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String findAll(Model model,
                          @ModelAttribute("userRepr") UserRepr userRepr,
                          @RequestParam("currentPage") Optional<Integer> currentPage,
                          @RequestParam("countElementOnPage") Optional<Integer> countElementOnPage) {

        log.info("List page request");

        Page<UserRepr> users = userService.pages(currentPage.orElse(1) - 1,
                countElementOnPage.orElse(10));

        model.addAttribute("users", users);

        return "user_templates/user";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @GetMapping("/add")
    public String save(Model model) {
        log.info("Add new user request");

        model.addAttribute("user", new UserRepr());
        model.addAttribute("roles", roleService.findAll());

        return "user_templates/user-add-form";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @GetMapping("/edit{id}")
    public String editPerson(@PathVariable("id") Long id, Model model) {

        log.info("Edit page for id {} requested", id);

        model.addAttribute("user", userService.findById(id).orElseThrow(NotFoundException::new));
        model.addAttribute("roles", roleService.findAll());

        return "user_templates/user-add-form";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("user") UserRepr user, BindingResult result, Model model) {
        log.info("Update endpoint request");

        model.addAttribute("roles", roleService.findAll());
        if (result.hasErrors()) {
            return "user_templates/user-add-form";
        }

        if (!user.getPassword().equals(user.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "user_templates/user-add-form";
        }

        log.info("Updating user with id {}", user.getId());
        userService.save(user);
        return "redirect:/user";
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @DeleteMapping("/delete{id}")
    public String delete(@PathVariable Long id) {
        log.info("User delete request");

        userService.delete(id);

        return "redirect:/user";
    }

    @GetMapping("/findById")
    public String findByUsername(@RequestParam("id") Long id, Model model) {
        UserRepr userRepr = userService.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute(userRepr);
        return "user_templates/find-by-id";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("user_templates/not-found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }

    @ExceptionHandler
    public ModelAndView badRequestException(BadRequestException ex) {
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.BAD_REQUEST);
        return mav;
    }

    @RequestMapping(value ="/access_denied")
    public String exception403() {
        return "access_denied";
    }
}
