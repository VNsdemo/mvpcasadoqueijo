package br.com.casadoqueijo.web;

import br.com.casadoqueijo.model.Movimentacao;
import br.com.casadoqueijo.model.Produto;
import br.com.casadoqueijo.repository.MovimentacaoRepository;
import br.com.casadoqueijo.service.EstoqueService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/relatorio")
public class RelatorioServlet extends HttpServlet {

    private EstoqueService estoqueService;
    private MovimentacaoRepository movimentacaoRepo;

    @Override
    public void init() throws ServletException {
        estoqueService = (EstoqueService) getServletContext().getAttribute("estoqueService");
        movimentacaoRepo = (MovimentacaoRepository) getServletContext().getAttribute("movimentacaoRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tipo = req.getParameter("tipo");

        if ("criticos".equals(tipo)) {
            List<Produto> criticos = estoqueService.criticos();
            req.setAttribute("criticos", criticos);
            req.getRequestDispatcher("/WEB-INF/jsp/relatorio/criticos.jsp").forward(req, resp);
        } else if ("historico".equals(tipo)) {
            List<Movimentacao> historico = movimentacaoRepo.listarTodas();
            req.setAttribute("historico", historico);
            req.getRequestDispatcher("/WEB-INF/jsp/relatorio/historico.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
