package com.example.Catalisa.Desafio3GerenciamentoEstoque.model.factory;

public class OutFactory implements EntranceExitFactory{
  @Override
  public Integer score(Integer currentAmount, Integer entryAmount) {
    return currentAmount - entryAmount;
  }
}
