package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.service.DMakerService;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 컨트롤러 빈들만 올려서 테스트. 원하는거 지정도 가능
// 컨트롤러 어드바이스나 필터까지 올려줌
@WebMvcTest(DMakerController.class)
//@WebMvcTest
class DMakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception {

        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .memberId("memberId1")
                .build();
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .memberId("memberId2")
                .build();

        given(dMakerService.getAllEmployedDevelopers())
                .willReturn(Arrays.asList(juniorDeveloperDto, seniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                    jsonPath("$.[0].developerSkillType",
                            CoreMatchers.is(DeveloperSkillType.BACK_END.name()))
                )
                .andExpect(
                        jsonPath("$.[0].developerLevel",
                                CoreMatchers.is(DeveloperLevel.JUNIOR.name()))
                )
                .andExpect(
                        jsonPath("$.[1].developerSkillType",
                                CoreMatchers.is(DeveloperSkillType.FRONT_END.name()))
                )
                .andExpect(
                        jsonPath("$.[1].developerLevel",
                                CoreMatchers.is(DeveloperLevel.SENIOR.name()))
                );

    }

}