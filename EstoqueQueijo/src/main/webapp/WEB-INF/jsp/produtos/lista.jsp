<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produtos — Casa do Queijo</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; color: #333; }
        .header { background: #4a2c2a; color: #fff; padding: 20px 30px; }
        .header h1 { font-size: 1.5em; }
        .header p { opacity: 0.8; font-size: 0.9em; margin-top: 4px; }
        .container { max-width: 960px; margin: 30px auto; padding: 0 20px; }
        .alert { padding: 12px 16px; border-radius: 6px; margin-bottom: 16px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .btn { display: inline-block; padding: 10px 20px; border-radius: 6px; text-decoration: none; font-size: 0.9em; font-weight: 600; cursor: pointer; border: none; }
        .btn-primary { background: #4a2c2a; color: #fff; }
        .btn-primary:hover { background: #6b3f3d; }
        table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th { background: #4a2c2a; color: #fff; padding: 12px 16px; text-align: left; font-size: 0.85em; text-transform: uppercase; }
        td { padding: 10px 16px; border-bottom: 1px solid #eee; font-size: 0.9em; }
        tr:last-child td { border-bottom: none; }
        tr:hover { background: #f9f9f9; }
        .badge { padding: 3px 8px; border-radius: 4px; font-size: 0.75em; font-weight: 700; }
        .badge-danger { background: #dc3545; color: #fff; }
        .badge-success { background: #28a745; color: #fff; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .page-header h2 { font-size: 1.3em; }
        .back-link { color: #4a2c2a; text-decoration: none; font-size: 0.9em; }
        .back-link:hover { text-decoration: underline; }
        .footer { text-align: center; padding: 20px; color: #999; font-size: 0.8em; }
    </style>
</head>
<body>
    <fmt:setLocale value="pt_BR"/>
    <div class="header">
        <h1>🧀 Casa do Queijo de Coqueiral</h1>
        <p>Sistema de Controle de Estoque</p>
    </div>
    <div class="container">
        <c:if test="${not empty param.msg}">
            <div class="alert alert-success">${param.msg}</div>
        </c:if>
        <div class="page-header">
            <h2>📦 Produtos Cadastrados</h2>
            <div>
                <a href="${pageContext.request.contextPath}/produtos?action=novo" class="btn btn-primary">+ Novo Produto</a>
                <a href="${pageContext.request.contextPath}/" class="back-link">← Menu</a>
            </div>
        </div>
        <c:choose>
            <c:when test="${empty produtos}">
                <p>Nenhum produto cadastrado.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Produto</th>
                            <th>Categoria</th>
                            <th>Quantidade</th>
                            <th>Estoque Mín.</th>
                            <th>Unidade</th>
                            <th>Preço Custo</th>
                            <th>Fornecedor</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${produtos}">
                            <tr>
                                <td>${p.id}</td>
                                <td>${p.nome}</td>
                                <td>${p.categoria}</td>
                                <td>${p.quantidade} ${p.unidade}</td>
                                <td>${p.estoqueMinimo} ${p.unidade}</td>
                                <td>${p.unidade}</td>
                               <td>R$ <fmt:formatNumber value="${p.precoCusto}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                <td>${p.fornecedorNome}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.quantidade < p.estoqueMinimo}">
                                            <span class="badge badge-danger">CRÍTICO</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-success">OK</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="footer">Casa do Queijo de Coqueiral &copy; 2024</div>
</body>
</html>
