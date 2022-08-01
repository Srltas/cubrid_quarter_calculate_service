package com.cubrid.quarterlycalculate.controller;

import com.cubrid.quarterlycalculate.request.TotalDataDto;
import com.cubrid.quarterlycalculate.service.TotalQuarterWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String findName(Model model, TotalDataDto totalDataDto) {
        model.addAttribute("quarterWorkTimeList",
                totalQuarterWebService.find(totalDataDto));
        return "total-dashboard-form";
    }

    @GetMapping("/{name}/{year}")
    public String findNameYear(Model model, TotalDataDto totalDataDto) {
        model.addAttribute("quarterWorkTimeList",
                totalQuarterWebService.find(totalDataDto));
        return "total-dashboard-form";
    }

    @GetMapping("/{name}/{year}/{quarter}")
    public String findNameYearQuarter(Model model, TotalDataDto totalDataDto) {
        model.addAttribute("quarterWorkTimeList",
                totalQuarterWebService.find(totalDataDto));
        return "total-dashboard-form";
    }
}
