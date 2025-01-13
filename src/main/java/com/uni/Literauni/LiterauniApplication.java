package com.uni.Literauni;

import com.uni.Literauni.Menu.LibroMenu;
import com.uni.Literauni.repository.AutorRepository;
import com.uni.Literauni.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterauniApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepositorio;
	@Autowired
	private AutorRepository AutorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiterauniApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LibroMenu menu = new LibroMenu(libroRepositorio, AutorRepositorio);
		menu.mostrarMenu();
	}
}
