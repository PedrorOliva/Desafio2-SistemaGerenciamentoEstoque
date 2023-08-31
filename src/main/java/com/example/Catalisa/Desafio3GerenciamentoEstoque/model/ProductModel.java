package com.example.Catalisa.Desafio3GerenciamentoEstoque.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "tb_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome é obrigatório!!")
  @Length(min = 3, max = 50, message = "O nome deverá ter no máximo {max} caracteres.")
  private String name;

  @NotBlank(message = "A descrição é obrigatória!!")
  @Length(min = 10, max = 255, message = "{max} é o máximo de caracteres.")
  @Column(nullable = false)
  private String description;

  @Column(name = "price", nullable = false)
  @NotNull(message = "O preço é obrigatório!")
  private Double price;

  @PositiveOrZero
  private Integer amount = 0;

  @NotBlank(message = "A categoria é obrigatória!!")
  private String category;

  private String operation;

  private Integer operationAmount = 0;

  public ProductModel(long id, String name, String descrption, Double price, Integer amount, String category) {
  }

}
