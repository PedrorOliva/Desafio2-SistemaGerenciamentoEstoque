package com.example.Catalisa.Desafio3GerenciamentoEstoque.service;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.dto.ProductDTO;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.exceptions.handleCategoryNotFound;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.exceptions.handleIdNotFound;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.exceptions.handleNameNotFound;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.mapper.ProductMapper;
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

  @Autowired
  ProductMapper productMapper;

  public ProductDTO create(ProductModel productModel, ProductFactory productFactory) {
    Integer amount = productFactory.operation(productModel.getOperation())
        .score(productModel.getAmount(), productModel.getOperationAmount());
    productModel.setAmount(amount);
    productModel.setAmount(productModel.getAmount());
    productRepository.save(productModel);
    ProductDTO productDTO = productMapper.toProductDTO(productModel);
    return productDTO;
  }

  public List<ProductDTO> listAllProducts() {
    List<ProductModel> productModels = productRepository.findAll();
    List<ProductDTO> productDTOS = new ArrayList<>();

    for (ProductModel productModel : productModels) {
      productDTOS.add(productMapper.toProductDTO(productModel));
    }
    return productDTOS;
  }

  public Optional<ProductDTO> findProductById(Long id) {
    Optional<ProductModel> productModel = productRepository.findById(id);
    if (productModel.isEmpty()) {
      throw new handleIdNotFound("ID not found!");
    }
    ProductModel product = productModel.get();
    ProductDTO productDTO = productMapper.toProductDTO(product);
    return Optional.of(productDTO);
  }

  public List<ProductDTO> findByName(String name) {
    List<ProductModel> productModels = productRepository.findByName(name);
    if (productModels.isEmpty()) {
      throw new handleNameNotFound("Name not found!");
    }
    List<ProductDTO> product = new ArrayList<>();
    for (ProductModel productModel : productModels) {
      product.add(productMapper.toProductDTO(productModel));
    }
    return product;
  }

  public List<ProductDTO> findByCategory(String category) {
    List<ProductModel> productModels = productRepository.findByCategory(category);
    if (productModels.isEmpty()) {
      throw new handleCategoryNotFound("Category not found!");
    }
    List<ProductDTO> product = new ArrayList<>();
    for (ProductModel productModel : productModels) {
      product.add(productMapper.toProductDTO(productModel));
    }
    return product;
  }

  public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
    Optional<ProductModel> productOptional = productRepository.findById(id);
    if (productOptional.isEmpty()) {
      throw new RuntimeException("ID not found!");
    }

    ProductModel product = productOptional.get();
    if (productDTO.getName() != null) {
      product.setName(productDTO.getName());
    }
    if (productDTO.getDescription() != null) {
      product.setDescription(productDTO.getDescription());
    }
    if (productDTO.getPrice() != null) {
      product.setPrice(productDTO.getPrice());
    }
    if (productDTO.getAmount() != null) {
      product.setAmount(productDTO.getAmount() + product.getAmount());
    }
    productRepository.save(product);
    return productMapper.toProductDTO(product);
  }

  public ProductDTO exitProduct(Long id, ProductModel productModel, ProductFactory productFactory) {
    Optional<ProductModel> productOptional = productRepository.findById(id);
    if (productOptional.isEmpty()) {
      throw new RuntimeException("ID not found!");
    }
    ProductModel product = productOptional.get();
    Integer newAmount = productFactory.operation(productModel.getOperation()).score(product.getAmount(),
          productModel.getOperationAmount());
    product.setOperationAmount(productModel.getOperationAmount());
    product.setAmount(newAmount);
    product.setOperation(productModel.getOperation());

    productRepository.save(product);
    return productMapper.toProductDTO(product);

  }

  public void delete(Long id) {
    Optional<ProductModel> productModel = productRepository.findById(id);

    productRepository.deleteById(id);
  }

}
