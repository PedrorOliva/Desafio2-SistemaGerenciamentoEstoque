package com.example.Catalisa.Desafio3GerenciamentoEstoque;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Estoque API", version = "1", description = "Api para " +
		"gerenciamento de estoque"))
public class Desafio3GerenciamentoEstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(Desafio3GerenciamentoEstoqueApplication.class, args);
	}

}
