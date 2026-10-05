package br.com.casadoqueijo;

import br.com.casadoqueijo.database.Database;
import br.com.casadoqueijo.menu.MenuPrincipal;
import br.com.casadoqueijo.repository.FornecedorRepository;
import br.com.casadoqueijo.repository.MovimentacaoRepository;
import br.com.casadoqueijo.repository.ProdutoRepository;
import br.com.casadoqueijo.service.EstoqueService;

public class Main {
    public static void main(String[] args) {
        try {
            Database.initialize();
            FornecedorRepository fornecedores = new FornecedorRepository();
            ProdutoRepository produtos = new ProdutoRepository();
            MovimentacaoRepository movimentacoes = new MovimentacaoRepository();
            Database.seedDemoData(fornecedores, produtos);
            new MenuPrincipal(new EstoqueService(produtos, fornecedores, movimentacoes), fornecedores, produtos, movimentacoes).executar();
        } catch (RuntimeException e) {
            System.err.println("Não foi possível iniciar o sistema: " + e.getMessage());
        }
    }
}
