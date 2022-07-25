package com.cubrid.quarterlycalculate.controller;

import com.cubrid.quarterlycalculate.service.TotalQuarterWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/total")
@RequiredArgsConstructor
@Controller
public class TotalQuarterController {

    private final TotalQuarterWebService totalQuarterWebService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("quarterWorkTimeList", totalQuarterWebService.findAll());
        return "total-dashboard-form";
    }

    @GetMapping("/{name}")
    public String find(Model model, @PathVariable String name) {
        model.addAttribute("quarterWorkTimeList", totalQuarterWebService.find(name));
        return "total-dashboard-form";
    }
}
