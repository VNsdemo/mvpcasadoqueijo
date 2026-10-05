package br.com.casadoqueijo.repository;

import br.com.casadoqueijo.database.Database;
import br.com.casadoqueijo.model.Movimentacao;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoRepository {
    public void salvar(int produtoId, String tipo, double quantidade) {
        String sql = "INSERT INTO movimentacoes(produto_id,tipo,quantidade,data_hora) VALUES(?,?,?,?)";
        try (Connection c = Database.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1,produtoId); p.setString(2,tipo); p.setDouble(3,quantidade); p.setString(4,LocalDateTime.now().toString()); p.executeUpdate();
        } catch (SQLException e) { throw new IllegalStateException("Erro ao registrar movimentação.",e); }
    }
    public List<Movimentacao> listarTodas() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT m.id,m.produto_id,p.nome,m.tipo,m.quantidade,m.data_hora FROM movimentacoes m JOIN produtos p ON p.id=m.produto_id ORDER BY m.data_hora DESC";
        try (Connection c = Database.getConnection(); PreparedStatement p = c.prepareStatement(sql); ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(new Movimentacao(r.getInt(1),r.getInt(2),r.getString(3),r.getString(4),r.getDouble(5),LocalDateTime.parse(r.getString(6))));
            return lista;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao consultar movimentações.",e); }
    }
}
