package br.com.casadoqueijo.repository;

import br.com.casadoqueijo.database.Database;
import br.com.casadoqueijo.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private static final String SELECT = "SELECT p.id,p.nome,p.categoria,p.quantidade,p.estoque_minimo,p.unidade,p.preco_custo,p.fornecedor_id,COALESCE(f.nome,'—') fornecedor_nome FROM produtos p LEFT JOIN fornecedores f ON f.id=p.fornecedor_id";

    public void salvar(Produto p) {
        String sql = "INSERT INTO produtos(nome,categoria,quantidade,estoque_minimo,unidade,preco_custo,fornecedor_id) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1,p.nome()); s.setString(2,p.categoria()); s.setDouble(3,p.quantidade()); s.setDouble(4,p.estoqueMinimo()); s.setString(5,p.unidade()); s.setDouble(6,p.precoCusto());
            if (p.fornecedorId() == null) s.setNull(7, Types.INTEGER); else s.setInt(7,p.fornecedorId());
            s.executeUpdate();
        } catch (SQLException e) { throw new IllegalStateException("Erro ao salvar produto. Verifique o fornecedor selecionado.",e); }
    }

    public List<Produto> listarTodos() { return consultar(SELECT + " ORDER BY p.nome", false); }
    public List<Produto> listarAbaixoDoMinimo() { return consultar(SELECT + " WHERE p.quantidade < p.estoque_minimo ORDER BY p.nome", false); }
    public Produto buscarPorId(int id) {
        List<Produto> resultado = consultar(SELECT + " WHERE p.id = ?", true, id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }
    public void atualizarQuantidade(int id, double quantidade) {
        try (Connection c = Database.getConnection(); PreparedStatement p = c.prepareStatement("UPDATE produtos SET quantidade=? WHERE id=?")) {
            p.setDouble(1,quantidade); p.setInt(2,id); if (p.executeUpdate() == 0) throw new IllegalArgumentException("Produto não encontrado.");
        } catch (SQLException e) { throw new IllegalStateException("Erro ao atualizar estoque.",e); }
    }
    private List<Produto> consultar(String sql, boolean porId, int... id) {
        List<Produto> lista = new ArrayList<>();
        try (Connection c = Database.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            if (porId) p.setInt(1,id[0]);
            try (ResultSet r = p.executeQuery()) { while (r.next()) lista.add(new Produto(r.getInt("id"),r.getString("nome"),r.getString("categoria"),r.getDouble("quantidade"),r.getDouble("estoque_minimo"),r.getString("unidade"),r.getDouble("preco_custo"),(Integer)r.getObject("fornecedor_id"),r.getString("fornecedor_nome"))); }
            return lista;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao consultar produtos.",e); }
    }
}
