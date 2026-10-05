# Casa do Queijo de Coqueiral — Sistema de Estoque

Sistema de controle de estoque em Java 17, Maven, SQLite, Servlets e JSP.

## Estrutura

```text
src/main/java/br/com/casadoqueijo/
├── Main.java
├── database/Database.java
├── model/Fornecedor.java
├── model/Movimentacao.java
├── model/Produto.java
├── repository/FornecedorRepository.java
├── repository/MovimentacaoRepository.java
├── repository/ProdutoRepository.java
├── service/EstoqueService.java
├── menu/MenuPrincipal.java          (interface de console)
└── web/
    ├── AppContextListener.java      (inicializa banco e repositórios)
    ├── ProdutoServlet.java
    ├── FornecedorServlet.java
    ├── MovimentacaoServlet.java
    └── RelatorioServlet.java

src/main/webapp/
├── index.jsp                        (menu principal)
└── WEB-INF/
    ├── web.xml
    └── jsp/
        ├── produtos/lista.jsp
        ├── produtos/cadastro.jsp
        ├── fornecedores/lista.jsp
        ├── fornecedores/cadastro.jsp
        ├── movimentacao/registrar.jsp
        └── relatorio/
            ├── criticos.jsp
            └── historico.jsp
```

## Executar a interface web

Instale e selecione um JDK 17 ou superior como SDK do projeto no IntelliJ IDEA. No terminal, na pasta do projeto, execute:

```text
mvn tomcat7:run
```

Abra o navegador em `http://localhost:8080`.

## Executar a interface de console

```text
mvn clean compile exec:java
```

O arquivo `casadoqueijo.db` é criado na pasta de execução. As tabelas são criadas automaticamente; fornecedores e produtos de demonstração são incluídos somente quando ainda não há produtos.

## Responsabilidade dos arquivos

- `Main.java`: inicializa banco, dados demonstrativos e menu de console.
- `Database.java`: abre conexões, cria as tabelas e prepara os dados iniciais.
- `model`: representa fornecedores, produtos e movimentações.
- `repository`: concentra operações SQLite usando JDBC e `PreparedStatement`.
- `EstoqueService.java`: valida cadastro, controla movimentações e calcula sugestões.
- `MenuPrincipal.java`: interface de console (terminal).
- `web/`: servlets e listener para a interface web.
- `webapp/`: páginas JSP com o visual do sistema.
- `pom.xml`: define Java 17, driver SQLite, Servlet/JSP/JSTL e plugins Maven.
