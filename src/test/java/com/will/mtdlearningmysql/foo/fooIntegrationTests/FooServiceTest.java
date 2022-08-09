package com.will.mtdlearningmysql.foo.fooIntegrationTests;

import com.will.mtdlearningmysql.exceptions.FooNotFound;
import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooRepository;
import com.will.mtdlearningmysql.foo.FooService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
@Transactional
@SpringBootTest
public class FooServiceTest{

    @Autowired
    FooService fooService;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private FooRepository fooRepository;

    @After
    public void tearDown(){
        fooRepository.deleteAll();
    }

    @Test
    public void getsAllFoo(){
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
        List<Foo> actual = fooService.getAllFoo();
        List <Foo> expected = Arrays.asList(bob);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getFooByName_ReturnsFooWhenExists(){
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
        Optional<Foo> actual = fooService.getFooByName(bob.getName());
        Optional<Foo> expected = Optional.of(bob);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void  getFooByName_ThrowsFooNotFoundWhenFooDoesNotExist(){
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
        assertThatThrownBy(() -> {
            fooService.getFooByName("doesnotexist");
            // then
        }).isInstanceOf(FooNotFound.class)
                .hasMessage("doesnotexist not found");
    }

    @Test
    public void addsFoo_WhenReqOK() {
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
        Optional<Foo> actual = fooService.getFooByName(bob.getName());
        Optional<Foo> expected = Optional.of(bob);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updatesFooByName_WhenFooExists(){
        // given
        Foo bob = new Foo("bob", 2, false);
        Foo bobUpdated = new Foo("bob", 1, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
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
        entityManager.persist(bob);
        entityManager.flush();

        // when
        fooService.deleteFooByName("bob");

        // then
        assertThatThrownBy(() -> {
            fooService.getFooByName("bob");
        }).isInstanceOf(FooNotFound.class)
                .hasMessage("bob not found");
    }
}
