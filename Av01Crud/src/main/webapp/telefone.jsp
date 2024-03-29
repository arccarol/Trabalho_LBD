<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css">
<title>Telefone</title>
<style>
    .centered {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh; /* Ajusta a altura da div para preencher toda a tela */
    }
    .container {
        text-align: center;
        margin-top: 20px;
    }
    input[type="text"], input[type="submit"] {
        margin-bottom: 10px;
    }
</style>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp"></jsp:include>
	</div>
	<br />
        <div class="container">
            <form action="telefone" method="post">
                <input type="text" id="cpf" name="cpf" value="${telefone.aluno.cpf}" placeholder="CPF">
                <br>
                <input type="text" id="numero" name="numero" value="${telefone.numero}" placeholder="Número">
                <br>
                <input type="submit" name="botao" value="Cadastrar">
                <input type="submit" name="botao" value="Alterar">
                <input type="submit" name="botao" value="Excluir">
                <input type="submit" name="botao" value="Listar">
            </form>

            <c:if test="${not empty erro }">
                <h2>${erro }</h2>
            </c:if>

            <c:if test="${not empty saida }">
                <h3>${saida }</h3>
            </c:if>

            <c:if test="${not empty telefones }">
                <table border="1">
                    <thead>
                        <tr>
                            <th>CPF</th>
                            <th>Número</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${telefones }">
                            <tr>
                                <td>${t.aluno.cpf }</td>
                                <td>${t.numero }</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

</body>
</html>