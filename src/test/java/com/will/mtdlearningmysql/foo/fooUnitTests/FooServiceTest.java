package com.will.mtdlearningmysql.foo.fooUnitTests;


import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooRepository;
import com.will.mtdlearningmysql.foo.FooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FooService.class)
public class FooServiceTest {

    @Autowired
    FooService fooService;

    @MockBean
    FooRepository fooRepository;

    @Test
    public void getAllFoo() {
        // given
        Foo bob = new Foo("bob", 1, true);
        List<Foo> expected = Arrays.asList(bob);
        when(fooRepository.findById("bob")).thenReturn(Optional.of(bob));
        when(fooRepository.findAll()).thenReturn(Arrays.asList(bob));

        // when
        List<Foo> actual = fooService.getAllFoo();

        // then
        verify(fooRepository, times(1)).findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getFooByName() {
    }

    @Test
    public void addFoo() {
    }

    @Test
    public void deleteFooByName() {
    }

    @Test
    public void updateFoo() {
    }
}