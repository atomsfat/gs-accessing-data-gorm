package services;

import hello.Person;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tomas on 8/11/14.
 */
@Service
public class PersonService {

  void save(Person person) {
    person.save(flush:true)
  }

  boolean validate(Person person) {
    person.validate()
  }

  public List<Person> findAll() {
    Person.findAll()
  }
}
