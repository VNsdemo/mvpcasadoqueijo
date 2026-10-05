<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movimentação — Casa do Queijo</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f5f5; color: #333; }
        .header { background: #4a2c2a; color: #fff; padding: 20px 30px; }
        .header h1 { font-size: 1.5em; }
        .header p { opacity: 0.8; font-size: 0.9em; margin-top: 4px; }
        .container { max-width: 640px; margin: 30px auto; padding: 0 20px; }
        .alert { padding: 12px 16px; border-radius: 6px; margin-bottom: 16px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .btn { display: inline-block; padding: 10px 20px; border-radius: 6px; text-decoration: none; font-size: 0.9em; font-weight: 600; cursor: pointer; border: none; }
        .btn-success { background: #28a745; color: #fff; }
        .btn-danger { background: #dc3545; color: #fff; }
        .btn-group { display: flex; gap: 10px; margin-top: 16px; }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; margin-bottom: 4px; font-weight: 600; font-size: 0.9em; }
        .form-group input, .form-group select { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 6px; font-size: 0.9em; }
        .form-group input:focus, .form-group select:focus { outline: none; border-color: #4a2c2a; }
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
        <c:if test="${not empty param.msg}">
            <div class="alert alert-success">${param.msg}</div>
        </c:if>
        <c:if test="${not empty erro}">
            <div class="alert alert-error">${erro}</div>
        </c:if>
        <div class="page-header">
            <h2>🔄 Registrar Movimentação</h2>
            <a href="${pageContext.request.contextPath}/" class="back-link">← Menu</a>
        </div>
        <div class="card">
            <form method="post" action="${pageContext.request.contextPath}/movimentacao">
                <div class="form-group">
                    <label for="produtoId">Produto</label>
                    <select id="produtoId" name="produtoId" required>
                        <option value="">Selecione um produto...</option>
                        <c:forEach var="p" items="${produtos}">
                            <option value="${p.id}">${p.nome} — Estoque: ${p.quantidade} ${p.unidade}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Tipo de Movimentação</label>
                    <div class="btn-group">
                        <label style="flex:1; text-align:center;">
                            <input type="radio" name="tipo" value="ENTRADA" required style="display:none;">
                            <button type="button" class="btn btn-success" style="width:100%;" onclick="selectType(this, 'ENTRADA')">📥 Entrada</button>
                        </label>
                        <label style="flex:1; text-align:center;">
                            <input type="radio" name="tipo" value="SAÍDA" style="display:none;">
                            <button type="button" class="btn btn-danger" style="width:100%;" onclick="selectType(this, 'SAÍDA')">📤 Saída</button>
                        </label>
                    </div>
                    <input type="hidden" id="tipoHidden" name="tipo">
                </div>
                <div class="form-group">
                    <label for="quantidade">Quantidade</label>
                    <input type="number" id="quantidade" name="quantidade" step="0.01" min="0.01" required>
                </div>
                <button type="submit" class="btn btn-success" style="width:100%;">Registrar Movimentação</button>
            </form>
        </div>
    </div>
    <script>
        function selectType(btn, type) {
            document.getElementById('tipoHidden').value = type;
            document.querySelectorAll('.btn-group .btn').forEach(b => {
                b.style.opacity = '0.5';
                b.style.border = '2px solid transparent';
            });
            btn.style.opacity = '1';
            btn.style.border = '2px solid #333';
        }
    </script>
    <div class="footer">Casa do Queijo de Coqueiral &copy; 2024</div>
</body>
</html>
