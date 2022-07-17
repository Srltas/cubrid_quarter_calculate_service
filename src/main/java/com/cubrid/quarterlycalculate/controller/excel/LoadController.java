package com.cubrid.quarterlycalculate.controller.excel;

import com.cubrid.quarterlycalculate.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/excel/upload")
@Controller
public class LoadController {

    private final LoadService loadService;

    @GetMapping
    public String uploadForm() {
        return "upload-form";
    }

    @PostMapping
    public String loadData(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            loadService.loadData(file);
        }

        return "redirect:/excel/upload";
    }
}
