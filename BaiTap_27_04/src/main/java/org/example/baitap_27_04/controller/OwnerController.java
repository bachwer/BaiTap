package org.example.baitap_27_04.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.baitap_27_04.config.SessionConstants;
import org.example.baitap_27_04.dto.OwnerForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OwnerController {

    @GetMapping({"/", "/owner"})
    public String showOwnerForm(HttpSession session, Model model) {
        if (session.getAttribute(SessionConstants.OWNER_NAME) != null) {
            return "redirect:/todos";
        }
        if (!model.containsAttribute("ownerForm")) {
            model.addAttribute("ownerForm", new OwnerForm());
        }
        return "owner";
    }

    @PostMapping("/owner")
    public String saveOwner(
            @Valid @ModelAttribute("ownerForm") OwnerForm ownerForm,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "owner";
        }

        session.setAttribute(SessionConstants.OWNER_NAME, ownerForm.getOwnerName().trim());
        redirectAttributes.addFlashAttribute("successMessage", "owner.saved");
        return "redirect:/todos";
    }
}

