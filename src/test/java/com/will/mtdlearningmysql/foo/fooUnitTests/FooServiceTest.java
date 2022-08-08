package com.will.mtdlearningmysql.foo.fooUnitTests;


import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooRepository;
import com.will.mtdlearningmysql.foo.FooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
        when(fooRepository.findAll()).thenReturn(Arrays.asList(bob));

        // when
        List<Foo> actual = fooService.getAllFoo();

        // then
        verify(fooRepository, times(1)).findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getFooByName_WhenFooExists(){
        // given
        Foo bob = new Foo("bob", 1, true);
        when(fooRepository.findById("bob")).thenReturn(Optional.of(bob));

        // when
        Optional<Foo> actual = fooService.getFooByName("bob");

        // then
        Optional<Foo> expected = Optional.of(bob);
        verify(fooRepository, times(2)).findById("bob");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getFooByName_ThrowsFooNotFoundExceptionIfDoesNotExist(){
        // given
        when(fooRepository.findById("bob")).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> {
            fooService.getFooByName("bob");
            // then
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("bob not found");
    }

    @Test
    public void addFoo_AddsFooWhenRequestOK() {
        // given
        Foo bob = new Foo("bob", 1, true);

        // when
        fooService.addFoo(bob);

        // then
        verify(fooRepository, times(1)).save(bob);
    }

    @Test
    public void deleteFooByName_WhenFooExists() {
        // given
        Foo bob = new Foo("bob", 1, true);
        when(fooRepository.findById("bob")).thenReturn(Optional.of(bob));

        // when
        fooService.deleteFooByName("bob");

        // then
        verify(fooRepository, times(1)).deleteById("bob");
    }

    @Test
    public void updateFoo_WhenFooExists() {
        // given
        Foo bob = new Foo("bob", 1, true);
        when(fooRepository.findById("bob")).thenReturn(Optional.of(bob));

        // when
        fooService.updateFoo("bob", bob);

        // then
        verify(fooRepository, times(1)).save(bob);
    }

    @Test
    public void isNegative_ReturnsFalseWhenNotNegative(){
        // given
        Integer legNumber = 1;

        // when
        Boolean actual = fooService.isNegative(legNumber);

        // then
        Boolean expected = false;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void isNegative_ReturnsTrueWhenNegative(){
        // given
        Integer legNumber = -1;

        // when
        Boolean actual = fooService.isNegative(legNumber);

        // then
        Boolean expected = true;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void isEmpty_ReturnsTrueWhenEmpty(){
        // given
        String name = "";

        // when
        Boolean actual = fooService.isEmptyString(name);

        // then
        Boolean expected = true;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void isEmpty_HandlesSpacesWhenEmpty(){
        // given
        String name = "    ";

        // when
        Boolean actual = fooService.isEmptyString(name);

        // then
        Boolean expected = true;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void isEmpty_ReturnsFalseWhenNotEmpty(){
        // given
        String name = "bob";

        // when
        Boolean actual = fooService.isEmptyString(name);

        // then
        Boolean expected = false;
        assertThat(actual).isEqualTo(expected);
    }
}