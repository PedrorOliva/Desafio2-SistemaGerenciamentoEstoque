package com.example.Catalisa.Desafio3GerenciamentoEstoque.service;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.dto.ProductDTO;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.mapper.ProductMapper;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.factory.ProductFactory;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServceTest {
  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;
  @InjectMocks
  private ProductService productService;

  @DisplayName("Create product")
  @Test
  public void testCreateProduct() {
    ProductModel productDTO = new ProductModel("teste", "testando", 10.00, "entrada", 5, "teste");

    ProductDTO result = productService.create(productDTO, any(ProductFactory.class));
    ProductFactory productFactory;

    assertNotNull(result);
    assertEquals(productDTO.getName(), result.getName());
    assertEquals(productDTO.getDescription(), result.getDescription());
    assertEquals(productDTO.getOperation(), isA(ProductFactory.class));
    assertEquals(productDTO.getOperationAmount(), isA(ProductFactory.class));
    assertEquals(productDTO.getPrice(), result.getPrice());
    assertEquals(productDTO.getAmount(), result.getAmount());
    assertEquals(productDTO.getDescription(), result.getDescription());
  }

  @DisplayName("List all products")
  @Test
  public void testListAllProducts() {
    ProductModel product1 = new ProductModel();
    ProductModel product2 = new ProductModel();
    List<ProductModel> productList = Arrays.asList(product1, product2);

    when(productRepository.findAll()).thenReturn(productList);

    List<ProductDTO> productDTOList = productService.listAllProducts();

    assertEquals(2, productDTOList.size());

    verify(productRepository, times(1)).findAll();
  }

  @DisplayName("Find product by Id")
  @Test
  void testFindById() {
    ProductModel product1 = new ProductModel();
    product1.setId(1L);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

    Optional<ProductDTO> result = productService.findProductById(1L);

    assertTrue(!result.isEmpty());

  }

  @DisplayName("Find when id not found")
  @Test
  void testIdNotFound() throws Exception {
    Long id = 1L;
    when(productRepository.findById(1L)).thenReturn(Optional.empty());
    Optional<ProductDTO> result = productService.findProductById(1L);

    assertFalse(result.isEmpty());
  }


  @DisplayName("Find product by name")
  @Test
  public void testFindByName() {
    ProductModel product1 = new ProductModel();
    product1.setName("teste");
    ProductModel product2 = new ProductModel();
    product2.setName("outoTeste");
    List<ProductModel> productList = Arrays.asList(product1, product2);

    when(productRepository.findByName(product1.getName())).thenReturn(productList);
    List<ProductDTO> result = productService.findByName(product1.getName());

    assertTrue(!result.isEmpty());
  }

  @DisplayName("Find by category")
  @Test
  public void testFindByCategory() {
    ProductModel product1 = new ProductModel();
    product1.setCategory("teste");
    ProductModel product2 = new ProductModel();
    product2.setCategory("outoTeste");
    List<ProductModel> productList = Arrays.asList(product1, product2);

    when(productRepository.findByCategory(product1.getCategory())).thenReturn(productList);
    List<ProductDTO> result = productService.findByCategory(product1.getCategory());

    assertTrue(!result.isEmpty());
  }

  @DisplayName("Update product")
  @Test
  public void testUpdateProduct() {

    ProductModel product = new ProductModel(1L, "teste", "teste", 10.00,
        "entrada", 5, "teste");

    ProductDTO productUpdate = new ProductDTO();
    productUpdate.setName("testeUpdate");

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(productRepository.save(any(ProductModel.class))).thenReturn(product);

    ProductDTO result = productService.updateProduct(1L, productUpdate);

    assertEquals((productUpdate.getName()), result.getName());
  }

  @DisplayName("Delete product")
  @Test
  public void testDeleteProduct() {
    ProductModel product = new ProductModel();
    product.setId(1L);

    productService.delete(product.getId());
  }
}
