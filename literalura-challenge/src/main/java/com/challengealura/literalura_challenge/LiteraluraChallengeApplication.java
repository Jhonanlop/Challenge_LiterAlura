package com.challengealura.literalura_challenge;

import com.challengealura.literalura_challenge.principal.Principal;
import com.challengealura.literalura_challenge.repository.AuthorRepository;
import com.challengealura.literalura_challenge.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class LiteraluraChallengeApplication implements CommandLineRunner {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BooksRepository booksRepository;


	public static void main(String[] args) {
		SpringApplication.run(LiteraluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(authorRepository, booksRepository);
		principal.muestraElMenu();
	}
}
