package br.com.casadoqueijo.database;

import br.com.casadoqueijo.model.Fornecedor;
import br.com.casadoqueijo.model.Produto;
import br.com.casadoqueijo.repository.FornecedorRepository;
import br.com.casadoqueijo.repository.ProdutoRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String URL = "jdbc:sqlite:casadoqueijo.db";

    private Database() { }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        try (var statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    public static void initialize() {
        String[] sql = {
                "CREATE TABLE IF NOT EXISTS fornecedores (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, telefone TEXT NOT NULL, cidade TEXT NOT NULL)",
                "CREATE TABLE IF NOT EXISTS produtos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, categoria TEXT NOT NULL, quantidade REAL NOT NULL CHECK(quantidade >= 0), estoque_minimo REAL NOT NULL CHECK(estoque_minimo >= 0), unidade TEXT NOT NULL, preco_custo REAL NOT NULL CHECK(preco_custo >= 0), fornecedor_id INTEGER, FOREIGN KEY(fornecedor_id) REFERENCES fornecedores(id))",
                "CREATE TABLE IF NOT EXISTS movimentacoes (id INTEGER PRIMARY KEY AUTOINCREMENT, produto_id INTEGER NOT NULL, tipo TEXT NOT NULL, quantidade REAL NOT NULL CHECK(quantidade > 0), data_hora TEXT NOT NULL, FOREIGN KEY(produto_id) REFERENCES produtos(id))"
        };
        try (Connection connection = getConnection(); var statement = connection.createStatement()) {
            for (String command : sql) statement.execute(command);
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao criar o banco SQLite.", e);
        }
    }

    public static void seedDemoData(FornecedorRepository fornecedores, ProdutoRepository produtos) {
        if (!produtos.listarTodos().isEmpty()) return;
        try {
            int f1 = fornecedores.salvar(new Fornecedor("Laticínios Serra", "(35) 3333-1000", "Coqueiral"));
            int f2 = fornecedores.salvar(new Fornecedor("Fazenda Boa Vista", "(35) 3333-2000", "Lavras"));
            produtos.salvar(new Produto("Queijo Minas", "Queijos", 5, 15, "kg", 32.50, f1));
            produtos.salvar(new Produto("Muçarela", "Queijos", 24, 10, "kg", 29.90, f2));
            produtos.salvar(new Produto("Requeijão", "Laticínios", 8, 12, "unidade", 9.50, f1));
        } catch (RuntimeException e) {
            throw new IllegalStateException("Erro ao inserir os dados de demonstração.", e);
        }
    }
}
