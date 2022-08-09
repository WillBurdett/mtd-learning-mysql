package com.will.mtdlearningmysql.foo.fooIntegrationTests;

import com.will.mtdlearningmysql.exceptions.FooNotFound;
import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooRepository;
import com.will.mtdlearningmysql.foo.FooService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FooServiceTest{

    @Autowired
    FooService fooService;

    @Autowired
    private FooRepository fooRepository;

    // write test cases here
    @AfterEach
    void tearDown(){
        fooRepository.deleteAll();
    }

    @Test
    public void getsAllFoo(){
        // given
        Foo bob = new Foo("bob", 2, false);
        Foo sam = new Foo("sam", 2, false);
        fooService.addFoo(bob);
        fooService.addFoo(sam);

        // when
        List<Foo> actual = fooService.getAllFoo();
        List <Foo> expected = Arrays.asList(bob, sam);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getFooByName_ReturnsFooWhenExists(){
        // given
        Foo bob = new Foo("bob", 2, false);
        fooService.addFoo(bob);

        // when
        Optional<Foo> actual = fooService.getFooByName(bob.getName());
        Optional<Foo> expected = Optional.of(bob);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void  getFooByName_ThrowsFooNotFoundWhenFooDoesNotExist(){
        //given
        Foo sam = new Foo("sam", 2, false);
        fooService.addFoo(sam);

        // when
        assertThatThrownBy(() -> {
            fooService.getFooByName("bob");
            // then
        }).isInstanceOf(FooNotFound.class)
                .hasMessage("bob not found");
    }

    @Test
    public void addsFoo_WhenReqOK() {
        //given
        Foo sam = new Foo("sam", 2, false);
        fooService.addFoo(sam);

        // when
        Optional<Foo> actual = fooService.getFooByName(sam.getName());
        Optional<Foo> expected = Optional.of(sam);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updatesFooByName_WhenFooExists(){
        // given
        Foo bob = new Foo("bob", 2, false);
        fooService.addFoo(bob);

        // when
        Foo bobUpdated = new Foo("bob", 1, false);
        fooService.updateFoo("bob", bobUpdated);

        Optional<Foo> actual = fooService.getFooByName("bob");
        Optional<Foo> expected = Optional.of(bobUpdated);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deletesFooByName_WhenFooExists(){
        // given
        Foo bob = new Foo("bob", 2, false);
        fooService.addFoo(bob);

        // when
        fooService.deleteFooByName("bob");

        // then
        assertThatThrownBy(() -> {
            fooService.getFooByName("bob");
        }).isInstanceOf(FooNotFound.class)
                .hasMessage("bob not found");
    }
}
