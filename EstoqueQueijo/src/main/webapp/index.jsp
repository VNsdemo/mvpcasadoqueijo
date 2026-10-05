<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Casa do Queijo de Coqueiral — Estoque</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; color: #333; }
        .header { background: #4a2c2a; color: #fff; padding: 20px 30px; }
        .header h1 { font-size: 1.5em; }
        .header p { opacity: 0.8; font-size: 0.9em; margin-top: 4px; }
        .container { max-width: 960px; margin: 30px auto; padding: 0 20px; }
        .menu-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 16px; margin-top: 20px; }
        .menu-card {
            background: #fff; border-radius: 8px; padding: 24px; text-align: center;
            text-decoration: none; color: #333; box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .menu-card:hover { transform: translateY(-3px); box-shadow: 0 4px 12px rgba(0,0,0,0.15); }
        .menu-card .icon { font-size: 2em; margin-bottom: 8px; }
        .menu-card .label { font-weight: 600; font-size: 1em; }
        .alert { padding: 12px 16px; border-radius: 6px; margin-bottom: 16px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .btn {
            display: inline-block; padding: 10px 20px; border-radius: 6px; text-decoration: none;
            font-size: 0.9em; font-weight: 600; cursor: pointer; border: none;
        }
        .btn-primary { background: #4a2c2a; color: #fff; }
        .btn-primary:hover { background: #6b3f3d; }
        .btn-secondary { background: #6c757d; color: #fff; }
        .btn-secondary:hover { background: #5a6268; }
        .btn-success { background: #28a745; color: #fff; }
        .btn-danger { background: #dc3545; color: #fff; }
        table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th { background: #4a2c2a; color: #fff; padding: 12px 16px; text-align: left; font-size: 0.85em; text-transform: uppercase; }
        td { padding: 10px 16px; border-bottom: 1px solid #eee; font-size: 0.9em; }
        tr:last-child td { border-bottom: none; }
        tr:hover { background: #f9f9f9; }
        .badge { padding: 3px 8px; border-radius: 4px; font-size: 0.75em; font-weight: 700; }
        .badge-danger { background: #dc3545; color: #fff; }
        .badge-success { background: #28a745; color: #fff; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 4px; font-weight: 600; font-size: 0.9em; }
        .form-group input, .form-group select {
            width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 6px; font-size: 0.9em;
        }
        .form-group input:focus, .form-group select:focus { outline: none; border-color: #4a2c2a; }
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
        <c:if test="${not empty param.msg}">
            <div class="alert alert-success">${param.msg}</div>
        </c:if>
        <h2>Menu Principal</h2>
        <div class="menu-grid">
            <a href="${pageContext.request.contextPath}/produtos" class="menu-card">
                <div class="icon">📦</div>
                <div class="label">Produtos</div>
            </a>
            <a href="${pageContext.request.contextPath}/produtos?action=novo" class="menu-card">
                <div class="icon">➕</div>
                <div class="label">Cadastrar Produto</div>
            </a>
            <a href="${pageContext.request.contextPath}/movimentacao" class="menu-card">
                <div class="icon">🔄</div>
                <div class="label">Movimentação</div>
            </a>
            <a href="${pageContext.request.contextPath}/fornecedores" class="menu-card">
                <div class="icon">🏭</div>
                <div class="label">Fornecedores</div>
            </a>
            <a href="${pageContext.request.contextPath}/fornecedores?action=novo" class="menu-card">
                <div class="icon">🤝</div>
                <div class="label">Cadastrar Fornecedor</div>
            </a>
            <a href="${pageContext.request.contextPath}/relatorio?tipo=criticos" class="menu-card">
                <div class="icon">⚠️</div>
                <div class="label">Estoque Crítico</div>
            </a>
            <a href="${pageContext.request.contextPath}/relatorio?tipo=historico" class="menu-card">
                <div class="icon">📋</div>
                <div class="label">Histórico</div>
            </a>
        </div>
    </div>
    <div class="footer">Casa do Queijo de Coqueiral &copy; 2024</div>
</body>
</html>
