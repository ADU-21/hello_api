package com.example.demo;

import com.example.demo.controller.HelloController;
import com.example.demo.controller.UserController;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MockServletContext.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemoApplicationTests {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(
                new HelloController(),
                new UserController()).build();
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello, World!")));
    }

    @Test
    public void getUserList() throws Exception {
        RequestBuilder request;
        request = get("/users/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    public void postAddUser() throws Exception {
        RequestBuilder request;
        request = post("/users/")
                .param("id", "1")
                .param("name", "Alice")
                .param("age", "22");
        mvc.perform(request)
                .andExpect(content().string(equalTo("add success")));

        request = get("/users/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"Alice\",\"age\":22}]")));

        request = get("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"Alice\",\"age\":22}")));
    }

    @Test
    public void putUpdateUser() throws Exception {
        RequestBuilder request;
        request = post("/users/")
                .param("id", "1")
                .param("name", "Alice")
                .param("age", "22");
        mvc.perform(request)
                .andExpect(content().string(equalTo("add success")));

        request = put("/users/1")
                .param("name", "Alice")
                .param("age", "23");
        mvc.perform(request)
                .andExpect(content().string(equalTo("update success")));


        request = get("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"Alice\",\"age\":23}")));
    }

    @Test
    public void deleteRemoveUser() throws Exception {
        RequestBuilder request;
        request = post("/users/")
                .param("id", "1")
                .param("name", "Alice")
                .param("age", "22");
        mvc.perform(request)
                .andExpect(content().string(equalTo("add success")));

        request = delete("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("remove success")));

        request = get("/users/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }
}
