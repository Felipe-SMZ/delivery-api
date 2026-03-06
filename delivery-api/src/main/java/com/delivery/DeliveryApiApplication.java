package com.delivery;

import com.delivery.model.Restaurante;
import com.delivery.repository.RestauranteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

// Na classe principal ou em um CommandLineRunner
@SpringBootApplication
public class DeliveryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApiApplication.class, args);
    }

//    @Bean
//    CommandLineRunner test(RestauranteRepository repository) {
//        return args -> {
//            // Criar restaurante
//            Restaurante r = new Restaurante();
//            r.setNome("Pizza Bella");
//            r.setEndereco("Rua A, 123");
//            r.setAtivo(true);
//
//            // Salvar
//            repository.save(r);
//
//            // Buscar todos
//            List<Restaurante> todos = repository.findAll();
//            System.out.println("Total: " + todos.size());
//
//            // Buscar ativos
//            List<Restaurante> ativos = repository.findByAtivoTrue();
//            System.out.println("Ativos: " + ativos.size());
//
//            // Buscar por nome
//            Optional<Restaurante> encontrado = repository.findByNome("Pizza Bella");
//            encontrado.ifPresent(rest ->
//                    System.out.println("Encontrado: " + rest.getNome())
//            );
//        };
//    }
}