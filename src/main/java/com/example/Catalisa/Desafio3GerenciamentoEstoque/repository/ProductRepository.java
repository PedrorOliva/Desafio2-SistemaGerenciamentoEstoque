package com.example.Catalisa.Desafio3GerenciamentoEstoque.repository;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
  Optional<ProductModel> findByName(String name);
  List<ProductModel> findByCategory(String category);
}
