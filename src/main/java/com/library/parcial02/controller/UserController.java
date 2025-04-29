package com.library.parcial02.controller;

import com.library.parcial02.model.UserEntity;
import com.library.parcial02.repository.RoleRepository;
import com.library.parcial02.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserEntity> users = userService.findAll();
        model.addAttribute("items", users);
        model.addAttribute("entityType", "user");
        model.addAttribute("entityName", "Usuario");
        model.addAttribute("title", "Gestión de Usuarios");
        model.addAttribute("createUrl", "/users/create");
        return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new UserEntity());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("entityType", "user");
        model.addAttribute("formMode", "create");
        model.addAttribute("title", "Crear Usuario");
        model.addAttribute("formAction", "/users/create");
        return "form";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("item") UserEntity user,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            model.addAttribute("entityType", "user");
            model.addAttribute("formMode", "create");
            model.addAttribute("title", "Crear Usuario");
            model.addAttribute("formAction", "/users/create");
            return "form";
        }

        userService.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Usuario creado con éxito");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserEntity user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("item", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("entityType", "user");
        model.addAttribute("formMode", "edit");
        model.addAttribute("title", "Editar Usuario");
        model.addAttribute("formAction", "/users/edit/" + id);
        return "form";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("item") UserEntity user,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            model.addAttribute("entityType", "user");
            model.addAttribute("formMode", "edit");
            model.addAttribute("title", "Editar Usuario");
            model.addAttribute("formAction", "/users/edit/" + id);
            return "form";
        }

        userService.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Usuario actualizado con éxito");
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario eliminado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se puede eliminar el usuario. Tiene registros asociados.");
        }

        return "redirect:/users";
    }
}
