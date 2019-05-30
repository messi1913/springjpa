package me.sangmessi.system;

import me.sangmessi.account.Account;
import me.sangmessi.account.AccountRole;
import me.sangmessi.account.AccountService;
import me.sangmessi.common.BaseControllerTest;
import me.sangmessi.common.MethodDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @MethodDescription("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        String username = "oauth@test.com";
        String pass = "pass";
        Account account = Account.builder()
                .email(username)
                .userName("oauth2")
                .mobileNumber("n/a")
                .password(pass)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.GENERAL))
                .build();
        this.accountService.createUser(account);

        String clientId = "flutter";
        String password = "pass";
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, password))
                .param("username", username)
                .param("password", pass)
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

    }

}