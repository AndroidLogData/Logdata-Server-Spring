package com.logdata.backend;

import com.logdata.backend.model.LogVO;
import com.logdata.backend.model.UserVO;
import com.logdata.backend.repository.LogDataRepository;
import com.logdata.backend.repository.UserDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogDataControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogDataRepository logDataRepository;
    @MockBean
    private UserDataRepository userDataRepository;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<LogVO> logVOJacksonTester;

    @org.junit.Test
    public void logDataListTest() throws Exception {
        UserVO user = new UserVO("user", "user");
        when(userDataRepository.findByUserID("user")).thenReturn(user);
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/logdatalist")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
        ).andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
        assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
    }

    @org.junit.Test
    public void logDataUserDataNullTest() {
        try {
            MockHttpServletResponse response = mvc.perform(get("/logdata").header("secretKey", "apiKey"))
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
            assertThat(response.getRedirectedUrl()).isEqualTo("http://localhost/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataPageGetTest() {
        try {
            MockHttpServletResponse response = mvc.perform(get("/logdata").with(user("user").password("user").roles("USER")))
                    .andDo(print()).andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataPostButNotAPIKeyTest() {
        try {
            MockHttpServletResponse response = mvc.perform(post("/logdata").with(user("user").password("user").roles("USER")))
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.getErrorMessage()).isEqualTo("Missing request header 'secretKey' for method parameter of type String");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataPostTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    post("/logdata")
                            .header("secretKey", "apiKey")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
                            .contentType("application/json;charset=utf-8")
                            .content("{\"packageName\" : \"android3\", \"message\" : \"MainActivity onCreate\", \"tag\" : \"[MainActivity::onCreate]\", \"time\" : 846516546863, \"level\" : \"v\", \"memoryInfo\" : { \"memoryPercentage\" : 31.041206627151695, \"dalvikPss\" : 2660, \"lowMemory\" : false, \"nativePss\" : 1889, \"totalPss\" : 17418, \"availMemory\" : 1093652480, \"threshold\" : 150994944, \"totalMemory\" : 1585950720, \"otherPss\" : 22869}}")
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"result\":\"Log Data Transfer Success\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataLevelFilterTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/logdatalevelfilter/query?")
                            .param("packagename", "android")
                            .param("level", "i")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataTagFilterTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/logdatatagfilter/query?")
                            .param("packagename", "android")
                            .param("tag", "[MainActivity::onCreate]")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void logDataPackageNameFilterTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/logdatapackagenamefilter/query?")
                            .param("packagename", "android")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tagListTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/tagdatalist")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void packageNameListTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/packagenamedatalist")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"logData\":[]}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPackageNameViewTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/packagenamefilter/query?")
                            .param("packagename", "android")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
            )
                    .andDo(print()).andExpect(view().name("index"))
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logDataPackageNameViewFailedTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/packagenamefilter/query?")
                            .param("packagename", "android")
//                            .with(
//                                    user("user")
//                                            .password("user")
//                                            .roles("USER")
//                            )
            )
                    .andDo(print()).andExpect(view().name("login"))
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}