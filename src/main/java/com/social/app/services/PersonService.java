package com.social.app.services;

import com.social.app.exceptions.PersonNotFoundException;
import com.social.app.models.Person;
import com.social.app.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long uid) {
        return personRepository.findById(uid).orElseThrow(
                () -> new PersonNotFoundException(
                        "Person Not Found with id = "+uid
                )
        );
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public void deletePerson(Long uid) {
        Person person = getPersonById(uid);
        personRepository.delete(person);
    }

}
