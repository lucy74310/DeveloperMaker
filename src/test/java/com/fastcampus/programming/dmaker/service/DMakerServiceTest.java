package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fastcampus.programming.dmaker.type.DeveloperLevel.JUNIOR;
import static com.fastcampus.programming.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest -@Autowired랑 세트
@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;
//    @Autowired
    @InjectMocks
    private DMakerService dMakerService;

    private final Developer developer = Developer.builder()
            .developerLevel(DeveloperLevel.JUNIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(2)
            .memberId("memberId")
            .name("name")
            .age(20)
            .build();

    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(DeveloperLevel.JUNIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(2)
            .memberId("memberId")
            .name("name")
            .age(20)
            .build();


    @Test
    public void testSomething() {
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                    .developerLevel(DeveloperLevel.JUNIOR)
                    .developerSkillType(FRONT_END)
                    .experienceYears(2)
                    .name("name")
                    .age(12)
                    .build()));

        //when
        /*DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");*/

        //then
        /*assertEquals(SENIRO, developerDetail.getDeveloperLevel());*/

        /*dMakerService.createDeveloper(CreateDeveloper.Request.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .memberId("test")
                .name("name")
                .age(20)
                .build());
        List<DeveloperDto> allDevelopers = dMakerService.getAllDevelopers();
        System.out.println("=====================");
        System.out.println(allDevelopers);
        System.out.println("=====================");
        String result = "hello" + " world!";
        assertEquals("hello world!", result);*/
    }

    @Test
    void createDeveloperTest_success() {
        //given

        given(developerRepository.findByMemberId(anyString()))
            .willReturn(Optional.empty());

        /*save 하는것 캡처*/
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when
        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultCreateRequest);

        //then

        /* 검증
         1. validation이 제대로 동작하는지.
         2. save시 제대로 저장되는지.*/

        verify(developerRepository, times(1))
                .save(captor.capture());
        Developer savedDeveloper = captor.getValue();
        assertEquals(JUNIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(2, savedDeveloper.getExperienceYears());
        assertEquals("name", savedDeveloper.getName());
        assertEquals(20, savedDeveloper.getAge());
    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(developer));


        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(defaultCreateRequest));

        assertEquals(DMakerErrorCode.NO_DEVELOPER, dMakerException.getDMakerErrorCode());
    }
}