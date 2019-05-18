package me.sangmessi.account;

import me.sangmessi.common.BaseControllerTest;
import me.sangmessi.common.MethodDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;


import java.util.Collection;
import java.util.Random;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends BaseControllerTest {

    @Autowired
    AccountRepository repository;

    @Test
    @MethodDescription("전체 사용자 조회를 한다.")
    public void getUsers() throws Exception {

        IntStream.range(30, 60).forEach(this::generateAccount);

        mockMvc.perform(get("/accounts")
                .param("page", "1")
                .param("size", "10")
            )
            .andDo(print())
            .andExpect(status().isOk())
//            .andExpect(jsonPath("userName").exists())
//            .andExpect(jsonPath("mobileNumber").exists())
//            .andExpect(jsonPath("email").exists())
//            .andExpect(jsonPath("_links.self").exists())
//            .andExpect(jsonPath("_links.profile").exists())
//            .andDo(document("get-accounts",
//                    links(
//                            linkWithRel("self").description("Link to self"),
//                            linkWithRel("get-account").description("Link to retrieve one user by id"),
//                            linkWithRel("profile").description("Link to profile")
//                    ),
//                    requestHeaders(
//                            headerWithName(HttpHeaders.ACCEPT).description("Accept header")
//                    ),
//                    responseHeaders(
//                            headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
//                    ),
//                    responseFields(
//                            fieldWithPath("userName").type(String.class).description("Name of account"),
//                            fieldWithPath("email").type(String.class).description("Name of account"),
//                            fieldWithPath("mobileNumber").type(String.class).description("Name of account"),
//                            fieldWithPath("userName").type(Collection.class).description("Name of account"),
//                            fieldWithPath("_links.self.href").description("Link to self"),
//                            fieldWithPath("_links.get-account.href").description("Link to retrieve one user"),
//                            fieldWithPath("_links.profile.href").description("Link to profile")
//                    )
//            ))

        ;
    }

    @Test
    @MethodDescription("ID 를 조회조건으로 사용자 한명을 조회를 한다.")
    public void getUser() throws Exception {
        // GIVEN

        // WHEN & THEN
        mockMvc.perform(get("/accounts/{id}", 1)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").exists())
                .andExpect(jsonPath("mobileNumber").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-account",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("update-account").description("Link to modify information of this user"),
                                linkWithRel("profile").description("Link to profile")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("userName").type(String.class).description("Name of account"),
                                fieldWithPath("email").type(String.class).description("Name of account"),
                                fieldWithPath("mobileNumber").type(String.class).description("Name of account"),
                                fieldWithPath("stores").type(Collection.class).description("Name of account"),
                                fieldWithPath("_links.self.href").description("Link to self"),
                                fieldWithPath("_links.update-account.href").description("Link to retrieve one user"),
                                fieldWithPath("_links.profile.href").description("Link to profile")
                        )
                ));
    }


    private Account generateAccount(int index) {
        Account account = Account.builder()
                .userName("테스트_"+index)
                .email("test"+index+"@test.com")
                .mobileNumber("1234567890")
                .build();

        return this.repository.save(account);
    }

}