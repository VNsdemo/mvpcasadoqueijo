package br.com.casadoqueijo.web;

import br.com.casadoqueijo.model.Produto;
import br.com.casadoqueijo.repository.FornecedorRepository;
import br.com.casadoqueijo.service.EstoqueService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private EstoqueService estoqueService;
    private FornecedorRepository fornecedorRepo;

    @Override
    public void init() throws ServletException {
        estoqueService = (EstoqueService) getServletContext().getAttribute("estoqueService");
        fornecedorRepo = (FornecedorRepository) getServletContext().getAttribute("fornecedorRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("novo".equals(action)) {
            req.setAttribute("fornecedores", fornecedorRepo.listarTodos());
            req.getRequestDispatcher("/WEB-INF/jsp/produtos/cadastro.jsp").forward(req, resp);
            return;
        }
        List<Produto> produtos = estoqueService.produtos();
        req.setAttribute("produtos", produtos);
        req.getRequestDispatcher("/WEB-INF/jsp/produtos/lista.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("cadastrar".equals(action)) {
            cadastrar(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/produtos");
        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nome = param(req, "nome");
            String categoria = param(req, "categoria");
            String unidade = param(req, "unidade");
            double quantidade = Double.parseDouble(param(req, "quantidade").replace(',', '.'));
            double estoqueMinimo = Double.parseDouble(param(req, "estoqueMinimo").replace(',', '.'));
            double precoCusto = Double.parseDouble(param(req, "precoCusto").replace(',', '.'));

            Integer fornecedorId = null;
            String fId = req.getParameter("fornecedorId");
            if (fId != null && !fId.isEmpty() && !fId.equals("0")) {
                fornecedorId = Integer.parseInt(fId);
            }

            estoqueService.cadastrar(new Produto(nome, categoria, quantidade, estoqueMinimo, unidade, precoCusto, fornecedorId));
            resp.sendRedirect(req.getContextPath() + "/produtos?msg=Produto cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            req.setAttribute("fornecedores", fornecedorRepo.listarTodos());
            req.getRequestDispatcher("/WEB-INF/jsp/produtos/cadastro.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("erro", "Erro ao cadastrar produto: " + e.getMessage());
            req.setAttribute("fornecedores", fornecedorRepo.listarTodos());
            req.getRequestDispatcher("/WEB-INF/jsp/produtos/cadastro.jsp").forward(req, resp);
        }
    }

    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }
}
