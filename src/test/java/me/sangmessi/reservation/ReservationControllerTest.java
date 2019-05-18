package me.sangmessi.reservation;

import me.sangmessi.common.BaseControllerTest;
import me.sangmessi.common.MethodDescription;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerTest extends BaseControllerTest {

    @Test
    @MethodDescription("ID 를 조회조건으로 사용자 한명을 조회를 한다.")
    public void getUser() throws Exception {
        // GIVEN

        // WHEN & THEN
        mockMvc.perform(get("/reservations/{id}", 1)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").exists())
                .andExpect(jsonPath("mobileNumber").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-reservation",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("update-reservation").description("Link to modify information of this user"),
                                linkWithRel("profile").description("Link to profile")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Long.class).description("Identification of account"),
                                fieldWithPath("userName").type(String.class).description("Name of account"),
                                fieldWithPath("email").type(String.class).description("Name of account"),
                                fieldWithPath("mobileNumber").type(String.class).description("Name of account"),
                                fieldWithPath("stores").type(Collection.class).description("Name of account"),
                                fieldWithPath("_links.self.href").description("Link to self"),
                                fieldWithPath("_links.update-reservation.href").description("Link to retrieve one user"),
                                fieldWithPath("_links.profile.href").description("Link to profile")
                        )
                ));
    }

}