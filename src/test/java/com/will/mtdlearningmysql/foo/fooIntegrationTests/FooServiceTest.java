package com.will.mtdlearningmysql.foo.fooIntegrationTests;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
}
