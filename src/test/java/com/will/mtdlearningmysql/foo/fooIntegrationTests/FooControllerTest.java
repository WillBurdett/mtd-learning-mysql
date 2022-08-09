package com.will.mtdlearningmysql.foo.fooIntegrationTests;

import com.will.mtdlearningmysql.exceptions.FooNotFound;
import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooController;
import com.will.mtdlearningmysql.foo.FooRepository;
import com.will.mtdlearningmysql.foo.FooService;
import com.will.mtdlearningmysql.utils.JsonUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FooControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FooController fooController;

    @Autowired
    private FooRepository fooRepository;

    @After
    public void tearDown(){
        fooRepository.deleteAll();
    }

    @Test
    public void getsAllFoo() throws Exception {
        // given
        Foo bob = new Foo ("bob", 2, false);
        mockMvc.perform(post("/foo")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bob)));
        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("bob")))
                .andExpect(jsonPath("$[0].legs", is(2)))
                .andExpect(jsonPath("$[0].canFly", is(false)));
    }

    @Test
    public void getFooByName_ReturnsFooWhenExists() throws Exception {
        // given
        Foo bob = new Foo("bob", 2, false);
        mockMvc.perform(post("/foo")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bob)));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/foo/bob")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("bob")))
                .andExpect(jsonPath("$.legs", is(2)))
                .andExpect(jsonPath("$.canFly", is(false)));
    }

    @Test
    public void  getFooByName_BadRequestWhenFooDoesNotExist() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/foo/bob")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addsFoo_WhenReqOK() throws Exception {
        // given
        Foo bob = new Foo("bob", 2, false);
        mockMvc.perform(post("/foo")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bob)));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/foo/bob")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("bob")))
                .andExpect(jsonPath("$.legs", is(2)))
                .andExpect(jsonPath("$.canFly", is(false)));
    }

    @Test
    public void updatesFooByName_WhenFooExists() throws Exception {
        // given
        Foo bob = new Foo("bob", 2, false);
        Foo bobUpdated = new Foo("bob", 10, true);
        mockMvc.perform(post("/foo")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bob)));

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/foo/bob")
                        .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bobUpdated)));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/foo/bob")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("bob")))
                .andExpect(jsonPath("$.legs", is(10)))
                .andExpect(jsonPath("$.canFly", is(true)));
    }


    @Test
    public void deletesFooByName_WhenFooExists() throws Exception {
        // given
        Foo bob = new Foo("bob", 2, false);
        mockMvc.perform(post("/foo")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bob)));

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/foo/bob")
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/foo/bob")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }
}
