package com.example.Catalisa.Desafio3GerenciamentoEstoque.model.factory;

import com.example.Catalisa.Desafio3GerenciamentoEstoque.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

  public EntranceExitFactory operation(String typeOperation){
    if(typeOperation.equalsIgnoreCase("Entrada")){
      return new EntryFactory();
    } else if (typeOperation.equalsIgnoreCase("Saida")){
      return new OutFactory();
    } else {
      return null;
    }
  }
}
