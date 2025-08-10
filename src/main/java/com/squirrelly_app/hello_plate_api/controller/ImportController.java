package com.squirrelly_app.hello_plate_api.controller;

import com.squirrelly_app.hello_plate_api.service.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping(value = "/menu")
    public ResponseEntity<Void> importMenu(@RequestParam String magic, @RequestParam String year, @RequestParam String week) {
        return importService.importMenu(magic, year, week);
    }

}
