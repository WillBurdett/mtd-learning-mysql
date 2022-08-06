package com.will.mtdlearningmysql.foo.fooUnitTests;

import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooController;
import com.will.mtdlearningmysql.foo.FooService;
import com.will.mtdlearningmysql.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(FooController.class)
public class FooControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FooService service;

    @Test
    public void getAllFoo() throws Exception {

        Foo bob = new Foo ("bob", 2, false);
        when(service.getAllFoo()).thenReturn(Arrays.asList(bob));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("bob")))
                .andExpect(jsonPath("$[0].legs", is(2)))
                .andExpect(jsonPath("$[0].canFly", is(false)));

        verify(service, times(1)).getAllFoo();
    }

    @Test
    public void getFooByName_WhenFooExists() throws Exception {

        Foo bob = new Foo ("bob", 2, false);
        when(service.getFooByName("bob")).thenReturn(Optional.of(bob));

        mockMvc.perform(MockMvcRequestBuilders.get("/foo/bob")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("bob")))
                .andExpect(jsonPath("$.legs", is(2)))
                .andExpect(jsonPath("$.canFly", is(false)));

        verify(service, times(1)).getFooByName("bob");
    }

    @Test
    public void addFoo_WhenReqBodyOK() throws Exception {
        Foo bob = new Foo("bob", 2, false);
        mockMvc.perform(post("/foo")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bob)));

        verify(service, times(1)).addFoo(bob);
    }

    @Test
    public void deleteFooByName_WhenNameExists() throws Exception {
        mockMvc.perform(delete("/foo/bob"));
        verify(service, times(1)).deleteFooByName("bob");
    }

    @Test
    public void updateFoo_WhenFooExistsAndReqBodyOK() throws Exception {
        Foo bob = new Foo ("bob", 2, false);
        mockMvc.perform(put("/foo/bob")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(bob)));
        verify(service, times(1)).updateFoo("bob", bob);
    }
}