package com.fastcampus.programming.dmaker.controller;


import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    /**
     * DMakerController(Bean)   DMakerService(Bean)   DeveloperRepository(Bean)
     * =============================== Spring Application ========================
     *
     * @RequiredArgsConstructor 와 final을 달면 자동으로 주입해준다.
     */
    private final DMakerService dMakerService;


    // GET /developers HTTP/1.1
    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {

        return dMakerService.getAllDevelopers();
    }

    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(
            @RequestBody CreateDeveloper.Request request
    ) {
        log.info("request : {}", request);


        return dMakerService.createDeveloper(request);
    }


}
