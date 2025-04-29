package com.library.parcial02.controller;

import com.library.parcial02.model.VehicleTypeEntity;
import com.library.parcial02.service.VehicleTypeService;
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
@RequestMapping("/vehicleTypes")
@PreAuthorize("hasAuthority('ADMIN')")

public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @GetMapping
    public String getAllVehicleTypes(Model model) {
        List<VehicleTypeEntity> vehicleTypes = vehicleTypeService.findAll();
        model.addAttribute("vehicleTypes", vehicleTypes);
        return "vehicle-types/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("vehicleType", new VehicleTypeEntity());
        return "vehicle-types/create";
    }

    @PostMapping("/create")
    public String createVehicleType(@Valid @ModelAttribute("vehicleType") VehicleTypeEntity vehicleType,
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "vehicle-types/create";
        }

        vehicleTypeService.save(vehicleType);
        redirectAttributes.addFlashAttribute("successMessage", "Vehicle type created successfully");
        return "redirect:/vehicleTypes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        VehicleTypeEntity vehicleType = vehicleTypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle type not found"));

        model.addAttribute("vehicleType", vehicleType);
        return "vehicle-types/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateVehicleType(@PathVariable Long id,
                                    @Valid @ModelAttribute("vehicleType") VehicleTypeEntity vehicleType,
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "vehicle-types/edit";
        }

        vehicleTypeService.save(vehicleType);
        redirectAttributes.addFlashAttribute("successMessage", "Vehicle type updated successfully");
        return "redirect:/vehicleTypes";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicleType(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vehicleTypeService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vehicle type deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete vehicle type. It is being used by parking records.");
        }

        return "redirect:/vehicleTypes";
    }
}
