package com.example.Catalisa.Desafio3GerenciamentoEstoque.controller;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.dto.ProductDTO;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.mapper.ProductMapper;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.factory.ProductFactory;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductMapper productMapper;

  @MockBean
  private ProductFactory productFactory;

  @MockBean
  private ProductService productService;

  @DisplayName("Register product")
  @Test
  public void testRegisterProduct() throws Exception {
    ProductModel product = new ProductModel();
    product.setId(1L);
    product.setName("Produto");
    product.setDescription("descrição");
    product.setPrice(240.00);
    product.setOperation("entrada");
    product.setOperationAmount(10);
    product.setCategory("teste");

    ProductDTO productDTO = productMapper.toProductDTO(product);

    when(productService.create(Mockito.any(ProductModel.class),Mockito.any(ProductFactory.class))).thenReturn(productDTO);

    mockMvc.perform(post("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Produto"))
        .andExpect(jsonPath("$.description").value("descrição")).andDo(print())
        .andExpect(jsonPath("$.price").value(240.00))
        .andExpect(jsonPath("$.operation").value("entrada"))
        .andExpect(jsonPath("$.operationAmount").value(10))
        .andExpect(jsonPath("$.amount").value(10))
        .andExpect(jsonPath("$.category").value("teste"));
  }


  @DisplayName("List all products")
  @Test
  public void testListAllProducts() throws Exception {
    List<ProductDTO> productList = new ArrayList<>();
    ProductDTO product1 = new ProductDTO();
    product1.setName("produto1");
    product1.setDescription("descrição");
    product1.setPrice(20.00);
    product1.setAmount(10);
    product1.setCategory("teste");

    ProductDTO product2 = new ProductDTO();
    product2.setName("produto2");
    product2.setDescription("descrição");
    product2.setPrice(10.00);
    product2.setAmount(50);
    product2.setCategory("teste");

    productList.add(product1);
    productList.add(product2);

    Mockito.when(productService.listAllProducts()).thenReturn(productList);

    mockMvc.perform(get("/api/product"))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].name").value("produto1"))
        .andExpect(jsonPath("$[0].description").value("descrição"))
        .andExpect(jsonPath("$[0].price").value(20.00))
        .andExpect(jsonPath("$[0].amount").value(10))
        .andExpect(jsonPath("$[0].category").value("teste"))
        .andExpect(jsonPath("$[1].name").value("produto2"))
        .andExpect(jsonPath("$[1].description").value("descrição"))
        .andExpect(jsonPath("$[1].price").value(10.00))
        .andExpect(jsonPath("$[1].amount").value(50))
        .andExpect(jsonPath("$[1].category").value("teste"));
  }


  @DisplayName("Find product by ID")
  @Test
  public void findProductById() throws Exception {
    ProductDTO product = new ProductDTO();
    product.setName("produto");
    product.setDescription("descrição");
    product.setPrice(20.00);
    product.setAmount(10);
    product.setCategory("teste");

    Mockito.when(productService.findProductById(1L)).thenReturn(Optional.of(product));

    mockMvc.perform(get("/api/product/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.name").value("produto"))
        .andExpect(jsonPath("$.description").value("descrição"))
        .andExpect(jsonPath("$.price").value(20.00))
        .andExpect(jsonPath("$.amount").value(10))
        .andExpect(jsonPath("$.category").value("teste"));
  }

  @DisplayName("Find product when ID not found")
  @Test
  public void findProductIdNotFound() throws Exception {
    mockMvc.perform(get("/api/product/{id}", 1L))
        .andExpect(status().isOk());
  }

    @DisplayName("Findl product by name")
  @Test
  public void findProductByName() throws Exception {
    List<ProductDTO> product = new ArrayList<>();
    ProductDTO product1 = new  ProductDTO("produto", "descrição", 20.00, 10, "teste");
    ProductDTO product2 = new  ProductDTO("teste", "descrição", 25.00, 20, "teste");
    product.add(product1);
    product.add(product2);

    String paramName = "name";
    String paramValue = "produto";

    Mockito.when(productService.findByName(product1.getName())).thenReturn(product);

    mockMvc.perform(get("/api/product/productName")
            .param(paramName, paramValue))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].name").value("produto"))
        .andExpect(jsonPath("$[0].description").value("descrição"))
        .andExpect(jsonPath("$[0].price").value(20.00))
        .andExpect(jsonPath("$[0].amount").value(10))
        .andExpect(jsonPath("$[0].category").value("teste"));
  }

  @DisplayName("Find product when name not found")
  @Test
  public void testFindByNameNotFound() throws Exception {
    String paramName = "name";
    String paramValue = "produto";

    mockMvc.perform(get("/api/product/productName")
            .param(paramName, paramValue))
        .andExpect(status().isOk());
  }

    @DisplayName("Find product by category")
  @Test
  public void findProductByCategory() throws Exception {
    List<ProductDTO> productList = new ArrayList<>();

      ProductDTO product2 = new  ProductDTO("teste", "descrição", 25.00, 20, "teste");


    productList.add(product2);

    String categoryName = "teste";
    String paramName = "category";
    String paramValue = "teste";

    Mockito.when(productService.findByCategory(categoryName)).thenReturn(productList);

    for (ProductDTO product : productList) {
      mockMvc.perform(get("/api/product/category")
              .param(paramName, paramValue))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"))
          .andExpect(jsonPath("$[0].name").value("teste"))
          .andExpect(jsonPath("$[0].description").value("descrição"))
          .andExpect(jsonPath("$[0].price").value(25.00))
          .andExpect(jsonPath("$[0].amount").value(20))
          .andExpect(jsonPath("$[0].category").value("teste"));
    }
  }

  @DisplayName("Find product when category not found")
  @Test
  public void testFindByCategoryNotFound() throws Exception {
    String paramName = "category";
    String paramValue = "teste";

    mockMvc.perform(get("/api/product/category")
            .param(paramName, paramValue))
        .andExpect(status().isNotFound());
  }

  @DisplayName("Delete product")
  @Test
  public void testDeleteProduct() throws Exception {
    when(productService.findProductById(1L)).thenReturn(Optional.ofNullable(any(ProductDTO.class)));
    mockMvc.perform(delete("/api/product/{id}", 1L))
        .andExpect(status().isOk());
  }


}
