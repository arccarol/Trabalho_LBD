<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./webapp/style.css">
<title>Telefone</title>
</head>
<body class="tela_aluno">
    <div class="menu">
        <jsp:include page="menu.jsp"></jsp:include>
    </div>
    <div align="center" class="container">
        <form action="telefone" method="post">
            <p class="title"><b class="cadastrar">Vincule o Telefone</b></p>
            <table>
             <tr>
                    <td class="aluno">
                        <p class="title">Codigo:</p>
                        <input class="cadastro" type="text" id="codigo" name="codigo" value='<c:out value="${telefone.codigo }"></c:out>'>
                    </td>
                </tr>
               <tr>
                     <td class="aluno">
                       <p class="title">Aluno:</p>
                        <select class="input_data" id="aluno" name="aluno">
                            <option value="">Selecione o Aluno</option>
                            <c:forEach var="aluno" items="${alunos}">
                                <option value="${aluno.cpf}">${aluno.nome}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="aluno">
                        <p class="title">Telefone:</p>
                        <input class="cadastro" type="text" id="telefone" name="telefone" value='<c:out value="${telefone.telefone }"></c:out>'>
                    </td>
                </tr>
                <tr class="botoes">
                    <td><input type="submit" name="botao" value="Cadastrar"></td>
                    <td><input type="submit" name="botao" value="Excluir"></td>
                    <td><input type="submit" name="botao" value="Listar"></td>
                </tr>
            </table>
        </form>
    </div>
    <div align="center">
        <c:if test="${not empty erro}">
            <h2><b><c:out value="${erro}" /></b></h2>
        </c:if>
    </div>
    <div align="center">
        <c:if test="${not empty saida}">
            <h3><b><c:out value="${saida}" /></b></h3>
        </c:if>
    </div>
    <div align="center">
        <c:choose>
            <c:when test="${not empty tipoTabela && tipoTabela eq 'Listar'}">
                <c:if test="${not empty telefones }">
                    <table class="table_round">
                        <thead>
                            <tr> 
                                <th>Codigo</th>
                                <th>Aluno</th>
                                <th>Telefone</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="t" items="${telefones }">
                                <tr>
                                   <td><c:out value="${t.codigo }" /></td>
                                    <td><c:out value="${t.aluno.nome }" /></td>
                                    <td><c:out value="${t.telefone }" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </c:when>
        </c:choose>
    </div>
</body>
<style>
@import url('https://fonts.googleapis.com/css2?family=Arbutus+Slab&family=Armata&family=Teko&family=Bebas+Neue&family=Raleway:ital,wght@0,100..900;1,100..900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

* {
    margin: 0;
    padding: 0;
    border: 0;
    box-sizing: border-box;
    font-family: "Poppins";
    text-decoration: none;
    color: black;
  
}

body {
	background-color: #ffffff;
	
}

input[type=submit] {
	color: black;
	background-color: #808080;	
	border-radius: 70px;
	font-size: 15px;
	width: 105px; 
    height: 35px;
    font-weight: 400;
    
}

input[type=submit]:hover {
	background-color: #808080;
	
}

 .title {
   margin-top: 10px;
   margin-bottom: 15px;
   
 }
 .cadastrar{
  text-align: center;
  margin-top: 10px;
  font-family: "Armata";
  text-transform: uppercase;
  font-weight: 600;
  margin-left: 10px;
   padding: 20px;
  
  
 }
 
 .cadastro {
    margin-top: 10px;
    background-color:#F5F5DC;
    border-radius: 20px; 
    outline: none;
    padding: 0 0.5rem;
    width: 200px; 
    height: 30px;
    border: 1px solid #C0C0C0; 
}
 table{
   padding: 15px;
 
 }
 
form {
  margin-top: 10px;
  margin-left: 30px;
  background-color:	#dcdcdc;
  width: 600px; 
  height: 330px;
  padding: 10px;
  border-radius: 30px; 
 }
 
 
 
 
 .aluno {
   display:flex;
   gap: 25px;
   padding: 10px;
   margin-top:-15px;
   
   }
   
 .botoes {
 display:flex;
 gap: 15px;
 padding: 20px;
 
 }
 
 .aviso {
 padding:10px;
 font-weight: 600;
 
 }
 
.tela_aluno {
    background-image: url('imagens/imagem_fundo.png');
 
}

.menu {
background-color: #f0f0f0;
padding:20px;

}

.mensagem {
background-color: #f0f0f0;
font-size: 20px;
}

.table_round {
    width: 50%;
    border-collapse: collapse;
    margin-bottom: 20px;
    background-color: #f0f0f0;
    font-size: 20px;
    
}

.table_round tr td{
    border: 1px solid #dddddd;
    padding: 8px;
    text-align: center;
   
    
}

.input_data {

   margin-bottom: 20px;
   margin-left: -10px;
   margin-top: 10px;

}

select {

    background-color:#F5F5DC;
    border-radius: 20px; 
    outline: none;
    padding: 0 0.5rem;
    width: 200px; 
    height: 30px;
    border: 1px solid #C0C0C0; 

}


</style>
</html>
