package br.com.casadoqueijo.repository;

import br.com.casadoqueijo.database.Database;
import br.com.casadoqueijo.model.Fornecedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepository {
    public int salvar(Fornecedor f) {
        String sql = "INSERT INTO fornecedores(nome, telefone, cidade) VALUES(?,?,?)";
        try (Connection c = Database.getConnection(); PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, f.nome()); p.setString(2, f.telefone()); p.setString(3, f.cidade()); p.executeUpdate();
            try (ResultSet keys = p.getGeneratedKeys()) { if (keys.next()) return keys.getInt(1); }
            try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT last_insert_rowid()")) { rs.next(); return rs.getInt(1); }
        } catch (SQLException e) { throw new IllegalStateException("Erro ao salvar fornecedor.", e); }
    }

    public List<Fornecedor> listarTodos() {
        List<Fornecedor> lista = new ArrayList<>();
        try (Connection c = Database.getConnection(); PreparedStatement p = c.prepareStatement("SELECT id,nome,telefone,cidade FROM fornecedores ORDER BY nome"); ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(new Fornecedor(r.getInt("id"), r.getString("nome"), r.getString("telefone"), r.getString("cidade")));
            return lista;
        } catch (SQLException e) { throw new IllegalStateException("Erro ao listar fornecedores.", e); }
    }
}
