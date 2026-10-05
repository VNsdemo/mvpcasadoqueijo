package br.com.casadoqueijo.web;

import br.com.casadoqueijo.database.Database;
import br.com.casadoqueijo.repository.FornecedorRepository;
import br.com.casadoqueijo.repository.MovimentacaoRepository;
import br.com.casadoqueijo.repository.ProdutoRepository;
import br.com.casadoqueijo.service.EstoqueService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Database.initialize();

        FornecedorRepository fornecedorRepo = new FornecedorRepository();
        ProdutoRepository produtoRepo = new ProdutoRepository();
        MovimentacaoRepository movimentacaoRepo = new MovimentacaoRepository();

        Database.seedDemoData(fornecedorRepo, produtoRepo);

        EstoqueService estoqueService = new EstoqueService(produtoRepo, fornecedorRepo, movimentacaoRepo);

        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("estoqueService", estoqueService);
        ctx.setAttribute("fornecedorRepo", fornecedorRepo);
        ctx.setAttribute("produtoRepo", produtoRepo);
        ctx.setAttribute("movimentacaoRepo", movimentacaoRepo);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
