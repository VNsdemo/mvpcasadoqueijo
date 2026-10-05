package br.com.casadoqueijo.web;

import br.com.casadoqueijo.model.Produto;
import br.com.casadoqueijo.repository.ProdutoRepository;
import br.com.casadoqueijo.service.EstoqueService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/movimentacao")
public class MovimentacaoServlet extends HttpServlet {

    private EstoqueService estoqueService;
    private ProdutoRepository produtoRepo;

    @Override
    public void init() throws ServletException {
        estoqueService = (EstoqueService) getServletContext().getAttribute("estoqueService");
        produtoRepo = (ProdutoRepository) getServletContext().getAttribute("produtoRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        java.util.List<Produto> produtos = estoqueService.produtos();
        req.setAttribute("produtos", produtos);
        req.getRequestDispatcher("/WEB-INF/jsp/movimentacao/registrar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int produtoId = Integer.parseInt(param(req, "produtoId"));
            String tipo = req.getParameter("tipo");
            double quantidade = Double.parseDouble(param(req, "quantidade").replace(',', '.'));

            estoqueService.movimentar(produtoId, tipo, quantidade);
            resp.sendRedirect(req.getContextPath() + "/movimentacao?msg=" + tipo + " registrada com sucesso!");
        } catch (IllegalArgumentException e) {
            java.util.List<Produto> produtos = estoqueService.produtos();
            req.setAttribute("produtos", produtos);
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/movimentacao/registrar.jsp").forward(req, resp);
        } catch (Exception e) {
            java.util.List<Produto> produtos = estoqueService.produtos();
            req.setAttribute("produtos", produtos);
            req.setAttribute("erro", "Erro ao registrar movimentação: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/movimentacao/registrar.jsp").forward(req, resp);
        }
    }

    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }
}
