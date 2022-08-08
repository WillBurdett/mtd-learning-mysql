package com.will.mtdlearningmysql.foo.fooIntegrationTests;

import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@DataJpaTest
public class FooRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FooRepository fooRepository;

    // write test cases here
    @AfterEach
    void tearDown(){
        fooRepository.deleteAll();
    }
    @Test
    public void findByName_thenReturnEmployee() {
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
        Optional<Foo> found = fooRepository.findById("bob");

        // then
        assertThat(found.get()).isEqualTo(bob);
    }

    @Test
    public void findAllFoo_ShouldReturnAllFoo() throws Exception {
        // given
        Foo bob = new Foo("bob", 2, false);
        Foo sam = new Foo("sam", 2, false);
        entityManager.persistAndFlush(bob);
        entityManager.persistAndFlush(sam);

        // when
        List<Foo> actual = fooRepository.findAll();
        List <Foo> expected = Arrays.asList(bob, sam);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findFooByName_ShouldReturnFoo() throws Exception {
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persistAndFlush(bob);

        // when
        Optional<Foo> actual = fooRepository.findById("bob");
        Optional <Foo> expected = Optional.of(bob);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
