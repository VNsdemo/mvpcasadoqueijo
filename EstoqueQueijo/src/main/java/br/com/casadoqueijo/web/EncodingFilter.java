package br.com.casadoqueijo.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// Sem isso, o Tomcat lê os campos de formulário (POST) como ISO-8859-1
// e acentos como "Muçarela" ou "SAÍDA" chegam quebrados.
@WebFilter("/*")
public class EncodingFilter implements Filter {

    // O Tomcat 7 usa a API Servlet 3.0, onde init() e destroy() são obrigatórios
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() { }
}
