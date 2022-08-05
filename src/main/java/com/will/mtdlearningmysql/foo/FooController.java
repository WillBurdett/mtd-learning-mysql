package com.will.mtdlearningmysql.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foo")
public class FooController {

    private final FooService fooService;

    @Autowired
    public FooController(FooService fooService) {
        this.fooService = fooService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Foo> getAllFoo(){
        return fooService.getAllFoo();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Optional<Foo> getFooByName(@PathVariable String name){
        return fooService.getFooByName(name.toLowerCase());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addFoo(@RequestBody Foo foo){
        fooService.addFoo(foo);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public void deleteFooByName(@PathVariable String name){
        fooService.deleteFooByName(name);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public void updateFoo(@PathVariable String name, @RequestBody Foo foo){
        fooService.updateFoo(name, foo);
    }
}
