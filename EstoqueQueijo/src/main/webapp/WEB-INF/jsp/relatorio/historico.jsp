<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Histórico — Casa do Queijo</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; color: #333; }
        .header { background: #4a2c2a; color: #fff; padding: 20px 30px; }
        .header h1 { font-size: 1.5em; }
        .header p { opacity: 0.8; font-size: 0.9em; margin-top: 4px; }
        .container { max-width: 960px; margin: 30px auto; padding: 0 20px; }
        .btn { display: inline-block; padding: 10px 20px; border-radius: 6px; text-decoration: none; font-size: 0.9em; font-weight: 600; cursor: pointer; border: none; }
        table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th { background: #4a2c2a; color: #fff; padding: 12px 16px; text-align: left; font-size: 0.85em; text-transform: uppercase; }
        td { padding: 10px 16px; border-bottom: 1px solid #eee; font-size: 0.9em; }
        tr:last-child td { border-bottom: none; }
        tr:hover { background: #f9f9f9; }
        .badge { padding: 3px 8px; border-radius: 4px; font-size: 0.75em; font-weight: 700; }
        .badge-success { background: #28a745; color: #fff; }
        .badge-danger { background: #dc3545; color: #fff; }
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
        <div class="page-header">
            <h2>📋 Histórico de Movimentações</h2>
            <a href="${pageContext.request.contextPath}/" class="back-link">← Menu</a>
        </div>
        <c:choose>
            <c:when test="${empty historico}">
                <p>Nenhuma movimentação registrada.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Data/Hora</th>
                            <th>Produto</th>
                            <th>Tipo</th>
                            <th>Quantidade</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="m" items="${historico}">
                            <tr>
                                <td><fmt:formatDate value="${m.dataHora}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                <td>${m.produtoNome}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${m.tipo == 'ENTRADA'}">
                                            <span class="badge badge-success">📥 Entrada</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">📤 Saída</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${m.quantidade}</td>
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
