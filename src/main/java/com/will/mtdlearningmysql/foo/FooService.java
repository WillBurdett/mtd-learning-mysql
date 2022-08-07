package com.will.mtdlearningmysql.foo;

import com.will.mtdlearningmysql.exceptions.FooNotFound;
import com.will.mtdlearningmysql.exceptions.NameIsEmpty;
import com.will.mtdlearningmysql.exceptions.NegativeLegNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FooService {

    private final FooRepository fooRepository;

    @Autowired
    public FooService(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    public List<Foo> getAllFoo() {
        return fooRepository.findAll();
    }

    public Optional<Foo> getFooByName(String name) {
        Optional<Foo> fooWithName = fooRepository.findById(name);
        if (fooWithName.isPresent()){
           return fooRepository.findById(name);
       } else {
           throw new FooNotFound(name + " not found");
        }
    }

    public void addFoo(Foo foo) {
        if (isNegative(foo.getLegs())){
            throw new NegativeLegNum("cannot have negative leg number");
        } else if (isEmptyString(foo.getName())){
            throw new NameIsEmpty("name must include characters");
        }
        fooRepository.save(foo);
    }

    public void deleteFooByName(String name) {
        Optional<Foo> fooWithName = fooRepository.findById(name);
        if (fooWithName.isPresent()){
            fooRepository.deleteById(name);
        } else {
            throw new FooNotFound(name + " not found");
        }
    }

    public void updateFoo(String name, Foo foo) {
        Optional<Foo> fooWithName = fooRepository.findById(name);
        if (fooWithName.isPresent()){
            Foo updateFoo = fooWithName.get();
            updateFoo.setName(foo.getName());
            updateFoo.setLegs(foo.getLegs());
            updateFoo.setCanFly(foo.getCanFly());
            fooRepository.save(updateFoo);
        } else {
            throw new FooNotFound(name + " not found");
        }
    }
    public Boolean isNegative(Integer legNum){
        return legNum < 0;
    }

    public Boolean isEmptyString(String name){
        return name.trim() == "";
    }
}
