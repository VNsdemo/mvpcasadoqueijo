package br.com.casadoqueijo.model;

import java.time.LocalDateTime;

public record Movimentacao(int id, int produtoId, String produtoNome, String tipo, double quantidade, LocalDateTime dataHora) { }
