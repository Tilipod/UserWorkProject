package ru.tilipod.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.tilipod.models.User;
import ru.tilipod.models.UserStatus;
import ru.tilipod.services.users.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    @Test
    void create() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Vlad");
        user.setEmail("tilipod@mail.ru");
        user.setUrl("rara");

        MvcResult mvcResult = mockMvc.perform(post("/users")
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .content(objectMapper.writeValueAsString(user)))
                                     .andExpect(request().asyncStarted())
                                     .andReturn();
        mvcResult.getAsyncResult();
        mockMvc.perform(asyncDispatch(mvcResult))
               .andExpect(status().isCreated())
               .andExpect(content().json(objectMapper.writeValueAsString(1)))
               .andReturn();
    }

    @Test
    void get() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Vlad");
        user.setEmail("tilipod@mail.ru");
        user.setUrl("rara");

        Mockito.when(userService.getById(Mockito.anyLong())).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                                              .accept(MediaType.APPLICATION_JSON_VALUE))
                                     .andExpect(request().asyncStarted())
                                     .andReturn();
        mvcResult.getAsyncResult();
        mockMvc.perform(asyncDispatch(mvcResult))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(user)))
               .andReturn();
    }

    @Test
    void changeUserStatus() throws Exception {
        UserStatus oldUserStatus = new UserStatus();
        oldUserStatus.setId(1);
        oldUserStatus.setIsOnline(true);

        UserStatus newUserStatus = new UserStatus();
        newUserStatus.setId(1);
        newUserStatus.setIsOnline(false);

        Mockito.when(userService.changeUserStatus(Mockito.anyLong(), Mockito.any(UserStatus.class))).thenReturn(oldUserStatus);

        MvcResult mvcResult = mockMvc.perform(post("/users/1")
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .content(objectMapper.writeValueAsString(newUserStatus)))
                                     .andExpect(request().asyncStarted())
                                     .andReturn();
        mvcResult.getAsyncResult();
        mockMvc.perform(asyncDispatch(mvcResult))
               .andExpect(status().isOk())
               .andExpect(content().json(
                          "{\"new_status\":" + objectMapper.writeValueAsString(newUserStatus)
                          + ",\"old_status\":" + objectMapper.writeValueAsString(oldUserStatus)
                          + ",\"user_id\":1}", false))
               .andReturn();
    }

    @Test
    void getAllIf() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Vlad");
        user.setEmail("tilipod@mail.ru");
        user.setUrl("rara");

        List<User> users = new ArrayList<>();
        users.add(user);

        Mockito.when(userService.getAllIf(null, null)).thenReturn(users);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                                              .accept(MediaType.APPLICATION_JSON_VALUE)
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .content("{}"))
                                     .andExpect(request().asyncStarted())
                                     .andReturn();
        mvcResult.getAsyncResult();
        mockMvc.perform(asyncDispatch(mvcResult))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(users)))
               .andReturn();
    }
}