<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href= "./webapp/style.css">
<title>Grade</title>
<style>
  label {
    display: inline-block;
    width: 100px; 
    margin-right: 10px;
  }
</style>
</head>
<body class="tela_aluno">
     <div class="menu">
       <jsp:include page="menu.jsp"></jsp:include>
    </div>
    <br />
    <div align="center" class="container">
        <form action="grade" method="post">
            <p class="title">
                <b class="cadastrar">Vincule a Grade</b>
            </p>
            <table>
            <tr>
              <td class = "aluno">
              <p class = "title">Codigo:</p>
                <input class="cadastro" type="number" 
                id="codigo" name="codigo" placeholder=""
                value='<c:out value="${grade.codigo }"></c:out>'>
              </td>
         </tr>
                <tr>
                    <td class="aluno"><label for="curso">Curso:</label></td>
                    <td>
                        <select class="input_data" id="curso" name="curso">
                            <option value="">Selecione um Curso</option>
                            <c:forEach var="curso" items="${cursos}">
                                <option value="${curso.codigo}"><c:out value="${curso.nome}" /></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr class ="espaco">
                    <td class = "aluno"><label for="disciplina">Disciplina:</label></td>
                    <td>
                        <select class="input_data" id="disciplina" name="disciplina">
                            <option value="">Selecione uma Disciplina</option>
                            <c:forEach var="disciplina" items="${disciplinas }">
                                <option value="${disciplina.codigo}"><c:out value="${disciplina.nome }" /></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr class ="botoes">
					<td><input type="submit" name="botao" value="Cadastrar"></td>
					<td><input type="submit" name="botao" value="Alterar"></td>
					<td><input type="submit" name="botao" value="Excluir"></td>
					<td><input type="submit" name="botao" value="Listar"></td>
                </tr>
            </table>
        </form>
    </div>
    <br />
    <div align="center">
        <c:if test="${not empty erro }">
            <h2>
                <b><c:out value="${erro }" /></b>
            </h2>
        </c:if>
    </div>
    <br />
    <div align="center">
        <c:if test="${not empty saida }">
            <h3>
                <b><c:out value="${saida }" /></b>
            </h3>
        </c:if>
    </div>
    <br />
    <div align="center">
        <c:choose>
            <c:when test="${not empty tipoTabela && tipoTabela eq 'Listar'}">
                <c:if test="${not empty grades }">
                    <table class="table_round">
                        <thead>
                            <tr>
                                <th>Codigo</th>
                                <th>Curso</th>
                                <th>Disciplina</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="grade" items="${grades }">
                                <tr>
                                    <td><c:out value="${grade.codigo }" /></td>
                                    <td><c:out value="${grade.curso.nome }" /></td>
                                    <td><c:out value="${grade.disciplina.nome }" /></td>
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
 table{
   padding: 15px;
 
 }
 
form {
  margin-top: 10px;
  margin-left: 30px;
  background-color:	#dcdcdc;
  width: 600px; 
  height: 350px;
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
 margin-left:30px;
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
   margin-left: -430px;

}

select {

    background-color:#F5F5DC;
    border-radius: 20px; 
    outline: none;
    padding: 0 0.6rem;
    width: 200px; 
    height: 30px;
    border: 1px solid #C0C0C0; 

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




</html>
