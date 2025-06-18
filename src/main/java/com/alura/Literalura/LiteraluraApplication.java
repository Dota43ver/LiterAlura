package com.alura.Literalura;

import com.alura.Literalura.principal.Principal;
import com.alura.Literalura.repository.AutorRepository;
import com.alura.Literalura.repository.LibroRepository;
import com.alura.Literalura.service.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository repository;
    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repository, autorRepository);
        principal.muestraElMenu();


    }


}
