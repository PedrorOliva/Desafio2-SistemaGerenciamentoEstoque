package com.example.Catalisa.Desafio3GerenciamentoEstoque.service;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.dto.ProductDTO;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.factory.ProductFactory;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;

  public ProductModel create(ProductModel productModel, ProductFactory productFactory) {
    Integer amount = productFactory.operation(productModel.getOperation())
        .score(productModel.getAmount(), productModel.getOperationAmount());
    productModel.setAmount(amount);
    productModel.setAmount(productModel.getAmount());
    return productRepository.save(productModel);
  }

  public List<ProductModel> findAllProducts() {
    return productRepository.findAll();
  }

  public Optional<ProductModel> findOneById(Long id) {

    return productRepository.findById(id);
  }

  public Optional<ProductModel> findOneByName(String name) {
    return productRepository.findByName(name);
  }

  public List<ProductModel> findProductByCategory(String category) {
    return productRepository.findByCategory(category);
  }

  public ProductModel updateProduct(Long id, ProductModel productModel) {
    ProductModel product = findOneById(id).get();

    if (productModel.getName() != null) {
      product.setName(productModel.getName());
    }
    if (productModel.getDescription() != null) {
      product.setDescription(productModel.getDescription());
    }
    if (productModel.getPrice() != null) {
      product.setPrice(productModel.getPrice());
    }
    if (productModel.getAmount() != null) {
      product.setAmount(productModel.getAmount() + product.getAmount());
    }

    return productRepository.save(product);
  }

  public ProductModel exitProduct(Long id, ProductModel productModel, ProductFactory productFactory) {
    ProductModel product = findOneById(id).get();

      Integer newAmount = productFactory.operation(product.getOperation()).score(product.getAmount(),
          productModel.getOperationAmount());
      product.setOperationAmount(productModel.getOperationAmount());
      product.setAmount(newAmount);
      product.setOperation(productModel.getOperation());

      return productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

}
