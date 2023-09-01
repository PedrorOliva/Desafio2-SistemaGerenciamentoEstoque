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
;

@RestController
@RequestMapping("api/product")
public class ProductController {

  @Autowired
  ProductService productService;

  @Autowired
  ProductMapper productMapper;

  @PostMapping
  public ResponseEntity<?> register(@Valid @RequestBody ProductModel productModel,
                                             ProductFactory productFactory){
    ProductDTO product = productService.create(productModel, productFactory);
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ProductDTO>> listAllProducts() {
    return ResponseEntity.ok(productService.listAllProducts());
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Optional<ProductDTO>> findById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.findById(id));
  }

  @GetMapping(path = "/productName")
  public ResponseEntity<List<ProductDTO>> findByName(@RequestParam String name) {
    return ResponseEntity.ok(productService.findByName(name));
  }

  @GetMapping(path = "/category")
  public ResponseEntity<List<ProductDTO>> findByCategory(@RequestParam String category) {
    return ResponseEntity.ok(productService.findByCategory(category));
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
    return ResponseEntity.ok(productService.updateProduct(id, productDTO));
  }

  @PutMapping(path = "/exit/{id}")
  public ResponseEntity<?> exitProduct(@PathVariable Long id,
                                       @RequestBody ProductModel productModel,  ProductDTO productDTO,
                                                ProductFactory productFactory) {
    return ResponseEntity.ok(productService.exitProduct(id, productModel, productFactory));
  }

  @DeleteMapping(path = "/{id}")
  public void deleteProduct(@PathVariable Long id){
    productService.delete(id);
  }


}