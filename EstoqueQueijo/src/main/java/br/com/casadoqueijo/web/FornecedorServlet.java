package br.com.casadoqueijo.web;

import br.com.casadoqueijo.model.Fornecedor;
import br.com.casadoqueijo.repository.FornecedorRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/fornecedores")
public class FornecedorServlet extends HttpServlet {

    private FornecedorRepository fornecedorRepo;

    @Override
    public void init() throws ServletException {
        fornecedorRepo = (FornecedorRepository) getServletContext().getAttribute("fornecedorRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("novo".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/jsp/fornecedores/cadastro.jsp").forward(req, resp);
            return;
        }
        List<Fornecedor> fornecedores = fornecedorRepo.listarTodos();
        req.setAttribute("fornecedores", fornecedores);
        req.getRequestDispatcher("/WEB-INF/jsp/fornecedores/lista.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nome = param(req, "nome");
            String telefone = param(req, "telefone");
            String cidade = param(req, "cidade");

            fornecedorRepo.salvar(new Fornecedor(nome, telefone, cidade));
            resp.sendRedirect(req.getContextPath() + "/fornecedores?msg=Fornecedor cadastrado com sucesso!");
        } catch (Exception e) {
            req.setAttribute("erro", "Erro ao cadastrar fornecedor: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/fornecedores/cadastro.jsp").forward(req, resp);
        }
    }

    private String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }
}
