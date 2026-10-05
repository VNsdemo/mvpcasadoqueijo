package br.com.casadoqueijo.menu;

import br.com.casadoqueijo.model.*;
import br.com.casadoqueijo.repository.*;
import br.com.casadoqueijo.service.EstoqueService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final EstoqueService estoque;
    private final FornecedorRepository fornecedores;
    private final ProdutoRepository produtos;
    private final MovimentacaoRepository movimentacoes;
    private final Scanner scanner = new Scanner(System.in);
    public MenuPrincipal(EstoqueService estoque, FornecedorRepository fornecedores, ProdutoRepository produtos, MovimentacaoRepository movimentacoes) {
        this.estoque=estoque; this.fornecedores=fornecedores; this.produtos=produtos; this.movimentacoes=movimentacoes;
    }
    public void executar() {
        boolean executando=true;
        while (executando) {
            System.out.println("\n====================================\nCASA DO QUEIJO DE COQUEIRAL\nSISTEMA DE ESTOQUE\n====================================");
            System.out.println("1 - Cadastrar produto\n2 - Listar produtos\n3 - Registrar entrada\n4 - Registrar saída\n5 - Cadastrar fornecedor\n6 - Listar fornecedores\n7 - Ver produtos abaixo do estoque mínimo\n8 - Sugestão de reposição\n9 - Histórico de movimentações\n0 - Sair");
            try {
                switch (lerInt("Opção: ")) {
                    case 1 -> cadastrarProduto(); case 2 -> listarProdutos(estoque.produtos());
                    case 3 -> movimentar("ENTRADA"); case 4 -> movimentar("SAÍDA");
                    case 5 -> cadastrarFornecedor(); case 6 -> listarFornecedores();
                    case 7 -> listarProdutos(estoque.criticos()); case 8 -> sugestoes();
                    case 9 -> historico(); case 0 -> executando=false;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Erro ao executar a operação: " + e.getMessage());
            }
        }
        System.out.println("Sistema encerrado.");
    }
    private void cadastrarProduto() {
        try {
            System.out.println("\nCadastro de produto");
            String nome=lerTexto("Nome: "), categoria=lerTexto("Categoria: ");
            double quantidade=lerDouble("Quantidade inicial: "), minimo=lerDouble("Estoque mínimo: ");
            String unidade=lerTexto("Unidade (kg, unidade, caixa...): ");
            double preco=lerDouble("Preço de custo: ");
            listarFornecedores(); Integer fornecedorId=lerInt("ID do fornecedor (0 para nenhum): "); if (fornecedorId==0) fornecedorId=null;
            estoque.cadastrar(new Produto(nome,categoria,quantidade,minimo,unidade,preco,fornecedorId));
            System.out.println("Produto cadastrado.");
        } catch (IllegalArgumentException e) { System.out.println("Não foi possível cadastrar: " + e.getMessage()); }
    }
    private void cadastrarFornecedor() {
        try { String nome=lerTexto("Nome: "), telefone=lerTexto("Telefone: "), cidade=lerTexto("Cidade: ");
            System.out.println("Fornecedor cadastrado com ID " + fornecedores.salvar(new Fornecedor(nome,telefone,cidade)) + ".");
        } catch (RuntimeException e) { System.out.println("Não foi possível cadastrar o fornecedor: " + e.getMessage()); }
    }
    private void movimentar(String tipo) {
        try { listarProdutos(estoque.produtos()); int id=lerInt("ID do produto: "); double quantidade=lerDouble("Quantidade: "); estoque.movimentar(id,tipo,quantidade); System.out.println(tipo+" registrada."); }
        catch (IllegalArgumentException e) { System.out.println("Não foi possível registrar: " + e.getMessage()); }
    }
    private void listarProdutos(List<Produto> lista) {
        System.out.printf("\n%-4s %-22s %-15s %13s %13s %-10s %-20s%n","ID","PRODUTO","CATEGORIA","ATUAL","MÍNIMO","UNIDADE","FORNECEDOR");
        if (lista.isEmpty()) { System.out.println("Nenhum produto encontrado."); return; }
        for (Produto p:lista) System.out.printf("%-4d %-22s %-15s %13s %13s %-10s %-20s%s%n",p.id(),p.nome(),p.categoria(),formatar(p.quantidade()),formatar(p.estoqueMinimo()),p.unidade(),p.fornecedorNome(),p.quantidade()<p.estoqueMinimo()?" CRÍTICO":"");
    }
    private void listarFornecedores() {
        System.out.println("\nFornecedores:"); List<Fornecedor> lista=fornecedores.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum fornecedor cadastrado."); return; }
        for (Fornecedor f:lista) System.out.printf("%d - %s | %s | %s%n",f.id(),f.nome(),f.telefone(),f.cidade());
    }
    private void sugestoes() {
        List<Produto> lista=estoque.criticos(); System.out.printf("\n%-22s %15s %15s %15s%n","PRODUTO","ESTOQUE ATUAL","ESTOQUE MÍNIMO","SUGESTÃO");
        if (lista.isEmpty()) { System.out.println("Nenhum produto precisa de reposição."); return; }
        for (Produto p:lista) System.out.printf("%-22s %12s %-2s %12s %-2s %12s %-2s%n",p.nome(),formatar(p.quantidade()),p.unidade(),formatar(p.estoqueMinimo()),p.unidade(),formatar(estoque.sugestao(p)),p.unidade());
    }
    private void historico() {
        List<Movimentacao> lista=movimentacoes.listarTodas(); System.out.println("\nHistórico de movimentações:");
        if(lista.isEmpty()) { System.out.println("Nenhuma movimentação registrada."); return; }
        DateTimeFormatter formato=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for(Movimentacao m:lista) System.out.printf("%d | %s | %s | %s %s | %s%n",m.id(),m.dataHora().format(formato),m.produtoNome(),m.tipo(),formatar(m.quantidade()),produtos.buscarPorId(m.produtoId()).unidade());
    }
    private String lerTexto(String prompt) { while(true) { System.out.print(prompt); String valor=scanner.nextLine().trim(); if(!valor.isBlank()) return valor; System.out.println("O campo é obrigatório."); } }
    private int lerInt(String prompt) { while(true) { System.out.print(prompt); try { return Integer.parseInt(scanner.nextLine().trim()); } catch(NumberFormatException e) { System.out.println("Digite um número inteiro válido."); } } }
    private double lerDouble(String prompt) { while(true) { System.out.print(prompt); try { double v=Double.parseDouble(scanner.nextLine().trim().replace(',','.')); if(Double.isFinite(v) && v>=0) return v; } catch(NumberFormatException ignored) { } System.out.println("Digite um número válido maior ou igual a zero."); } }
    private String formatar(double valor) { return valor==(long)valor ? Long.toString((long)valor) : String.format("%.2f",valor); }
}
