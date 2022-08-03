package com.will.mtdlearningmysql.foo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, String> {
}
