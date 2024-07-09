package com.acpurrinos.literalura;

import com.acpurrinos.literalura.principal.Principal;
import com.acpurrinos.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
@Autowired //inyección de dependencias para poder usar
//JPA
private LibroRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("""
                ************************************
                      LITERALURA - BIENVENIDO
                ************************************""");
		Principal principal = new Principal(repository);
		principal.inicia();

	}


}
