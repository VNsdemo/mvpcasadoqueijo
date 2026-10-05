<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Fornecedor — Casa do Queijo</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; color: #333; }
        .header { background: #4a2c2a; color: #fff; padding: 20px 30px; }
        .header h1 { font-size: 1.5em; }
        .header p { opacity: 0.8; font-size: 0.9em; margin-top: 4px; }
        .container { max-width: 640px; margin: 30px auto; padding: 0 20px; }
        .alert { padding: 12px 16px; border-radius: 6px; margin-bottom: 16px; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .btn { display: inline-block; padding: 10px 20px; border-radius: 6px; text-decoration: none; font-size: 0.9em; font-weight: 600; cursor: pointer; border: none; }
        .btn-primary { background: #4a2c2a; color: #fff; }
        .btn-primary:hover { background: #6b3f3d; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 4px; font-weight: 600; font-size: 0.9em; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 6px; font-size: 0.9em; }
        .form-group input:focus { outline: none; border-color: #4a2c2a; }
        .card { background: #fff; border-radius: 8px; padding: 24px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .page-header h2 { font-size: 1.3em; }
        .back-link { color: #4a2c2a; text-decoration: none; font-size: 0.9em; }
        .back-link:hover { text-decoration: underline; }
        .footer { text-align: center; padding: 20px; color: #999; font-size: 0.8em; }
    </style>
</head>
<body>
    <div class="header">
        <h1>🧀 Casa do Queijo de Coqueiral</h1>
        <p>Sistema de Controle de Estoque</p>
    </div>
    <div class="container">
        <c:if test="${not empty erro}">
            <div class="alert alert-error">${erro}</div>
        </c:if>
        <div class="page-header">
            <h2>🤝 Cadastrar Fornecedor</h2>
            <a href="${pageContext.request.contextPath}/fornecedores" class="back-link">← Voltar</a>
        </div>
        <div class="card">
            <form method="post" action="${pageContext.request.contextPath}/fornecedores">
                <div class="form-group">
                    <label for="nome">Nome</label>
                    <input type="text" id="nome" name="nome" required placeholder="Ex: Laticínios Serra">
                </div>
                <div class="form-group">
                    <label for="telefone">Telefone</label>
                    <input type="text" id="telefone" name="telefone" required placeholder="Ex: (35) 3333-1000">
                </div>
                <div class="form-group">
                    <label for="cidade">Cidade</label>
                    <input type="text" id="cidade" name="cidade" required placeholder="Ex: Coqueiral">
                </div>
                <button type="submit" class="btn btn-primary">Cadastrar Fornecedor</button>
            </form>
        </div>
    </div>
    <div class="footer">Casa do Queijo de Coqueiral &copy; 2024</div>
</body>
</html>
