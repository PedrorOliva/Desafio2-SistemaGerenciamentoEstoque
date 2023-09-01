package com.example.Catalisa.Desafio3GerenciamentoEstoque.mapper;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.dto.ProductDTO;
import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {
  public ProductDTO toProductDTO(ProductModel productModel){
    ProductDTO productDTO = new ProductDTO();

    productDTO.setName(productModel.getName());
    productDTO.setDescription(productModel.getDescription());
    productDTO.setPrice(productModel.getPrice());
    productDTO.setAmount(productModel.getAmount());
    productDTO.setCategory(productModel.getCategory());

    return productDTO;
  }
}
