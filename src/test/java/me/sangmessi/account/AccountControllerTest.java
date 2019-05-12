package me.sangmessi.account;

import me.sangmessi.common.BaseControllerTest;
import me.sangmessi.common.MethodDescription;
import org.junit.Test;
import org.springframework.http.HttpHeaders;


import java.util.Collection;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends BaseControllerTest {

    @Test
    @MethodDescription("전체 사용자 조회를 한다.")
    public void getUsers() throws Exception {
        mockMvc.perform(get("/account"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("userName").exists())
            .andExpect(jsonPath("mobileNumber").exists())
            .andExpect(jsonPath("email").exists())
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.profile").exists())
            .andDo(document("get-accounts",
                    links(
                            linkWithRel("self").description("Link to self"),
                            linkWithRel("get-account").description("Link to retrieve one user by id"),
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
                            fieldWithPath("userName").type(Collection.class).description("Name of account")


                    )


                    ));
    }

}