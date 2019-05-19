package me.sangmessi.account;

import me.sangmessi.common.BaseControllerTest;
import me.sangmessi.common.MethodDescription;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends BaseControllerTest {

    @Autowired
    AccountRepository repository;

    @Before
    public void makeDummyData() {
//        List<Account> tests = this.repository.findByUserNameContaining("테스트");
//        this.repository.deleteInBatch(tests);
//        IntStream.range(1, 100).forEach(this::generateAccount);
    }

    @Test
    @MethodDescription("전체 사용자 조회를 한다.")
    public void getUsers() throws Exception {

        mockMvc.perform(get("/accounts")
                .accept(MediaTypes.HAL_JSON)
                .param("page", "1")
                .param("size", "10")
                .param("sort", "userName,DESC")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("page").exists())
            .andExpect(jsonPath("_embedded.accountDTOList[0].userName").exists())
            .andExpect(jsonPath("_embedded.accountDTOList[0].email").exists())
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.profile").exists())
            .andExpect(jsonPath("_links.prev").exists())
            .andExpect(jsonPath("_links.next").exists())
            .andDo(document("get-accounts",
                    links(
                            linkWithRel("self").description("Link to self"),
                            linkWithRel("get-account").description("Link to retrieve one user by id"),
                            linkWithRel("profile").description("Link to profile"),
                            linkWithRel("first").description("Link to first page"),
                            linkWithRel("last").description("Link to last page"),
                            linkWithRel("prev").description("Link to previous page"),
                            linkWithRel("next").description("Link to profile")
                    ),
                    requestHeaders(
                            headerWithName(HttpHeaders.ACCEPT).description("Accept header")
                    ),
                    responseHeaders(
                            headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                    ),
                    relaxedResponseFields(
                            fieldWithPath("page.size").description("Size of page"),
                            fieldWithPath("page.totalElements").description("The number of total elements"),
                            fieldWithPath("page.totalPages").description("The number of total pages"),
                            fieldWithPath("page.number").description("The current page number"),
                            fieldWithPath("_embedded.accountDTOList").description("list of User"),
                            fieldWithPath("_links.self.href").description("Link to self"),
                            fieldWithPath("_links.profile.href").description("Link to list existing users"),
                            fieldWithPath("_links.get-account.href").description("Link to get an user"),
                            fieldWithPath("_links.first.href").description("Link to first page of users"),
                            fieldWithPath("_links.last.href").description("Link to last page of users"),
                            fieldWithPath("_links.prev.href").description("Link to previous page of users"),
                            fieldWithPath("_links.next.href").description("Link to next page of users")
                    )
            ))

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
                                fieldWithPath("id").type(Long.class).description("Identification of account"),
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

    @Test
    @MethodDescription("사용자를 생성한다.")
    public void createUser() throws Exception {
        Account account = Account.builder()
                .userName("createUser")
                .email("createUser@test.com")
                .mobileNumber("1234567890")
                .password("rlatkdap1!")
                .build();

        Optional<Account> createUser = this.repository.findByUserName("createUser");
        createUser.ifPresent(value -> this.repository.delete(value));

        mockMvc.perform(post("/accounts")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").exists())
                .andExpect(jsonPath("mobileNumber").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("create-account",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("profile").description("link to profile"),
                                linkWithRel("get-account").description("link to retrieve an user")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        ),
                        requestFields(
                                fieldWithPath("id").type(Long.class).description("Identification of User"),
                                fieldWithPath("email").type(String.class).description("Email of User"),
                                fieldWithPath("password").type(String.class).description("Password of User"),
                                fieldWithPath("userName").type(String.class).description("Name of User"),
                                fieldWithPath("mobileNumber").type(String.class).description("Mobile number of User"),
                                fieldWithPath("stores").type(Collections.class).description("Stores of user owned"),
                                fieldWithPath("reservations").type(Collections.class).description("Reservation for this user")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Long.class).description("Identification of User"),
                                fieldWithPath("email").type(String.class).description("Email of User"),
                                fieldWithPath("userName").type(String.class).description("Name of User"),
                                fieldWithPath("mobileNumber").type(String.class).description("Mobile number of User"),
                                fieldWithPath("stores").type(Collections.class).description("Stores of user owned"),
                                fieldWithPath("_links.self.href").description("Link to self"),
                                fieldWithPath("_links.get-account.href").description("Link to retrieve an user"),
                                fieldWithPath("_links.profile.href").description("Link to update existing event")
                        )

                ));
    }

    @Test
    @MethodDescription("사용자 정보를 수정한다. ")
    @Transactional
    public void updateUser() throws Exception{
        //When
        List<Account> testList = this.repository.findByUserNameContaining("테스트_");
        Account account = testList.get(testList.size() - 1);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setMobileNumber("010-9989-1913");

        mockMvc.perform(put("/accounts")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").exists())
                .andExpect(jsonPath("mobileNumber").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("update-account",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("profile").description("link to profile"),
                                linkWithRel("get-account").description("link to retrieve an user")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        ),
                        requestFields(
                                fieldWithPath("id").type(Long.class).description("Identification of User"),
                                fieldWithPath("email").type(String.class).description("Email of User"),
                                fieldWithPath("userName").type(String.class).description("Name of User"),
                                fieldWithPath("mobileNumber").type(String.class).description("Mobile number of User"),
                                fieldWithPath("stores").type(Collections.class).description("Stores of user owned")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Long.class).description("Identification of User"),
                                fieldWithPath("email").type(String.class).description("Email of User"),
                                fieldWithPath("userName").type(String.class).description("Name of User"),
                                fieldWithPath("mobileNumber").type(String.class).description("Mobile number of User"),
                                fieldWithPath("stores").type(Collections.class).description("Stores of user owned"),
                                fieldWithPath("_links.self.href").description("Link to self"),
                                fieldWithPath("_links.get-account.href").description("Link to retrieve an user"),
                                fieldWithPath("_links.profile.href").description("Link to update existing event")
                        )

                ));


    }

    @Test
    @MethodDescription("사용자를 삭제 시킨다. ")
    public void deleteUser() throws Exception {
        List<Account> accoutList = this.repository.findByUserNameContaining("테스트_");
        Account account = accoutList.get(accoutList.size() - 1);
        long id = account.getId();
        mockMvc.perform(delete("/accounts/{id}", id)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("delete-account",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("get-accounts").description("Link to retrieve all users"),
                                linkWithRel("profile").description("Link to profile")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Long.class).description("Identification of User"),
                                fieldWithPath("email").type(String.class).description("Email of User"),
                                fieldWithPath("userName").type(String.class).description("Name of User"),
                                fieldWithPath("mobileNumber").type(String.class).description("Mobile number of User"),
                                fieldWithPath("stores").type(Collections.class).description("Stores of user owned"),
                                fieldWithPath("_links.self.href").description("Link to self"),
                                fieldWithPath("_links.get-accounts.href").description("Link to retrieve one user"),
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