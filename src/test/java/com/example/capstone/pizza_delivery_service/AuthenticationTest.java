package com.example.capstone.pizza_delivery_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testSuccessfulUserLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login").with(csrf())
                .param("username", "nportman") .param("password", "Nportman1!"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
    @Test public void testFailedUserLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login").with(csrf())
                .param("username", "nportman")
                .param("password", "incorrectPassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testSuccessfulAdminLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login").with(csrf())
                .param("username", "BOBDYLAN")
                .param("password", "BOBDYLAN"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
    @Test @WithMockUser(username = "admin", roles = "USER")
    public void testFailedAdminLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login").with(csrf())
                .param("username", "BOBDYLAN")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }
}

