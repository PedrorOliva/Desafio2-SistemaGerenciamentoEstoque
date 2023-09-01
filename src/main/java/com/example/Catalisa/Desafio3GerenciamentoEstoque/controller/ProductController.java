package com.example.Catalisa.Desafio3GerenciamentoEstoque.controller;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.dto.ProductDTO;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.mapper.ProductMapper;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.factory.ProductFactory;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("api/product")
@Tag(name = "Produtos")
public class ProductController {

  @Autowired
  ProductService productService;

  @Autowired
  ProductMapper productMapper;

  @Operation(summary = "Essa rota permite cadastrar um produto, passando as informações pelo body da " +
      "requisição.", method = "POST")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Criado com sucesso"))
  @PostMapping
  public ResponseEntity<?> register(@Valid @RequestBody ProductModel productModel,
                                             ProductFactory productFactory){
    ProductDTO product = productService.create(productModel, productFactory);
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }
  @Operation(summary = "Essa rota busca todos os produtos cadastrados.", method = "GET")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
  @GetMapping
  public ResponseEntity<List<ProductDTO>> listAllProducts() {
    return ResponseEntity.ok(productService.listAllProducts());
  }

  @Operation(summary = "Essa rota permite buscar um produto pelo ID, bastando passar o valor na url.",
      method = "GET")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
  @GetMapping(path = "/{id}")
  public ResponseEntity<Optional<ProductDTO>> findById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.findProductById(id));
  }

  @Operation(summary = "Essa rota permite buscar um produto pelo nome, bastando passar o nome como " +
      "parametro na url.", method = "GET")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
  @GetMapping(path = "/productName")
  public ResponseEntity<List<ProductDTO>> findByName(@RequestParam String name) {
    return ResponseEntity.ok(productService.findByName(name));
  }

  @Operation(summary = "Essa rota permite buscar um produto pela categorua, bastando passar a categoria " +
      "como parametro na url.", method = "GET")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
  @GetMapping(path = "/category")
  public ResponseEntity<List<ProductDTO>> findByCategory(@RequestParam String category) {
    return ResponseEntity.ok(productService.findByCategory(category));
  }

  @Operation(summary = "Essa rota permite atualizar uma informação do produto", method = "PUT")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Informação atualizada com sucesso"))
  @PutMapping(path = "/{id}")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
    return ResponseEntity.ok(productService.updateProduct(id, productDTO));
  }


  @Operation(summary = "Essa rota permite a retirada de um produto no estoque, para isso informe o ID do " +
      "produto na url, e no corpo da requisição informe a operação de 'saida' e a quantidade que deseja " +
      "retirar.", method = "PATCH")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Retirada realizada com sucesso"))
  @PutMapping(path = "/exit/{id}")
  public ResponseEntity<?> exitProduct(@PathVariable Long id,
                                       @RequestBody ProductModel productModel,  ProductDTO productDTO,
                                                ProductFactory productFactory) {
    return ResponseEntity.ok(productService.exitProduct(id, productModel, productFactory));
  }
  @Operation(summary = "Essa rota permite deletar um produto, basta apenas informar o ID do produto na url.",
      method = "DELETE")
  @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"))
  @DeleteMapping(path = "/{id}")
  public void deleteProduct(@PathVariable Long id){
    productService.delete(id);
  }


}