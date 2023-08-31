package com.example.Catalisa.Desafio3GerenciamentoEstoque.controller;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.dto.ProductDTO;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.mapper.ProductMapper;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.factory.ProductFactory;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/product")
public class ProductController {

  @Autowired
  ProductService productService;

  @Autowired
  ProductMapper productMapper;

  @PostMapping
  public ResponseEntity<ProductDTO> registerProduct(@Valid @RequestBody ProductModel productModel,
                                                    ProductFactory productFactory) {
    ProductModel newProduct = productService.create(productModel, productFactory);
    return new ResponseEntity<>(productMapper.toProductDTO(newProduct), HttpStatus.CREATED);
  }

  @GetMapping
  public List<ProductDTO> listAllProducts() {
    List<ProductModel> products = productService.findAllProducts();
    List<ProductDTO> productDTO = new ArrayList<>();

    for(ProductModel productModel: products){
      productDTO.add(productMapper.toProductDTO(productModel));
    }
    return productDTO;
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> findProductById(@PathVariable Long id) {
    Optional<ProductModel> productOptional = productService.findOneById(id);
    if (productOptional.isEmpty()) {
      return status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
    }
    ProductModel product = productOptional.get();
    return ResponseEntity.ok(productMapper.toProductDTO(product));
  }

  @GetMapping(path = "/productName")
  public ResponseEntity<?> findProductByName(@RequestParam String name) {
    Optional<ProductModel> productOptional = productService.findOneByName(name);
    if (productOptional.isEmpty()) {
      return status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
    }
    ProductModel product = productOptional.get();
    return ResponseEntity.ok(productMapper.toProductDTO(product));
  }

  @GetMapping(path = "/category")
  public ResponseEntity<?> findProductByCategory(@RequestParam String category) {
    List<ProductModel> products = productService.findProductByCategory(category);
    List<ProductDTO> productDTO = new ArrayList<>();

    if (products.isEmpty()) {
      return status(HttpStatus.NOT_FOUND).body("Categoria não encontrada!");
    }
    for(ProductModel productModel: products){
      productDTO.add(productMapper.toProductDTO(productModel));
    }
    return ResponseEntity.ok(productDTO);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductModel productModel) {
    Optional<ProductModel> productOptional = productService.findOneById(id);
    if (productOptional.isEmpty()) {
      return status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
    }
    productService.updateProduct(id, productModel);

    ProductModel product = productOptional.get();
    return ResponseEntity.ok(productMapper.toProductDTO(product));
  }

  @PutMapping(path = "/exit/{id}")
  public ResponseEntity<?> exitProduct(@PathVariable Long id,
                                       @RequestBody ProductModel productModel, ProductFactory productFactory) {
    Optional<ProductModel> productOptional = productService.findOneById(id);
    if (productOptional.isEmpty()) {
      return status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
    }
    ProductModel product = productService.exitProduct(id, productModel, productFactory);

    return ResponseEntity.ok(productMapper.toProductDTO(product));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
    Optional<ProductModel> productOptional = productService.findOneById(id);
    if (productOptional.isEmpty()) {
      return status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
    }
    productService.deleteProduct(id);
    return ResponseEntity.ok("Deletado com sucesso!");
  }
}
