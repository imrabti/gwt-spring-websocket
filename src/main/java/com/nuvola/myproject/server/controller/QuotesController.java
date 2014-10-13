package com.nuvola.myproject.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuvola.myproject.server.service.QuotesService;
import static com.nuvola.myproject.shared.ResourcePaths.QUOTES;

@RestController
@RequestMapping(value = QUOTES, produces = MediaType.APPLICATION_JSON_VALUE)
public class QuotesController {
    private final QuotesService quotesService;

    @Autowired
    QuotesController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }
}
