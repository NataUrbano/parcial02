package com.library.parcial02.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorTitle", "Página no encontrada");
                model.addAttribute("errorMessage", "Lo sentimos, la página que estás buscando no existe.");
                return "error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorTitle", "Acceso denegado");
                model.addAttribute("errorMessage", "No tienes permisos para acceder a esta página.");
                return "error/403";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorTitle", "Error del servidor");
                model.addAttribute("errorMessage", "Ha ocurrido un error en el servidor. Por favor, inténtalo de nuevo más tarde.");
                return "error/500";
            }
        }

        model.addAttribute("errorTitle", "Error");
        model.addAttribute("errorMessage", "Ha ocurrido un error inesperado.");
        return "error/general";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("errorTitle", "Acceso denegado");
        model.addAttribute("errorMessage", "No tienes permisos para acceder a esta página.");
        return "error/403";
    }
}
