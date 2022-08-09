package com.will.mtdlearningmysql.foo.fooIntegrationTests;

import com.will.mtdlearningmysql.foo.Foo;
import com.will.mtdlearningmysql.foo.FooRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class FooRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FooRepository fooRepository;

    // write test cases here
    @After
    public void tearDown(){
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

    @Test
    public void deleteByName_DeletesFoo() {
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
        fooRepository.deleteById("bob");

        // then
        assertThat(fooRepository.findById("bob")).isEqualTo(Optional.empty());
    }

    @Test
    public void UpdateByName_UpdatesFoo() {
        // given
        Foo bob = new Foo("bob", 2, false);
        entityManager.persist(bob);
        entityManager.flush();

        // when
        bob.setLegs(10);

        // then
        assertThat(fooRepository.findById("bob").get().getLegs()).isEqualTo(10);
    }
}
