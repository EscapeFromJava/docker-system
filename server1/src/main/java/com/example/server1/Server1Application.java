package com.example.server1;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class Server1Application implements CommandLineRunner {

    private final PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(Server1Application.class, args);
    }

    @GetMapping("/")
    public List<Person> get() {
        return personRepository.findAll();
    }

    @PostMapping("/")
    public String save(@RequestBody PersonDto personDto) {
        return personRepository.save(new Person(personDto.getName(), personDto.getAge())).toString();
    }

    @Override
    public void run(String... args) throws Exception {
        personRepository.saveAll(Stream.generate(
                        () -> new Person("Person " + new Random().nextInt(5000), new Random().nextInt(20, 50)))
                .limit(5)
                .collect(Collectors.toList())
        );
    }
}
