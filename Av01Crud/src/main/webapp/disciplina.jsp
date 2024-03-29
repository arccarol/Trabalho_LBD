<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href= "./webapp/style.css">
<title>Disciplina</title>
</head>
<body class="tela_aluno">
    <div class="menu">
       <jsp:include page="menu.jsp"></jsp:include>
    </div>
    <br />
    <div align="center" class="container">
    <form action="disciplina" method="post">
       <p class="title">
       </p>
         <p class="cadastrar"> Cadastre a Disciplina</p>
       <table>
   
          <tr>
              <td class = "aluno" colspan="3">
              <p class = "title">Codigo:</p>
                <input class="cadastro" type="text" 
                id="codigo" name="codigo" placeholder=""
                value='<c:out value="${disciplina.codigo }"></c:out>'>
                 <input type="submit" id="botao" name="botao" value="Buscar">
              </td>
         </tr>
         <tr>
             <td class = "aluno" colspan="4">
              <p class = "title">Nome:</p>
                <input class="cadastro" type="text" id="nome" name="nome" placeholder=""
                 value='<c:out value="${disciplina.nome }"></c:out>'>
            </td>
         </tr>
          <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Hora Inicio:</p>
                <input class="cadastro" type="time" id="horas_inicio" name="horas_inicio" placeholder=""
               value='<c:out value="${disciplina.horas_inicio }"></c:out>'>
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Duração:</p>
                <input class="cadastro" type="number" id="duracao" name="duracao" 
               value='<c:out value="${disciplina.duracao }"></c:out>'>
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Dia Semana:</p>
                <input class="cadastro" type="text" id="dia_semana" name="dia_semana" 
               value='<c:out value="${disciplina.dia_semana }"></c:out>'> 
            </td>
         </tr>
         <tr class="botoes">
            <td>
               <input type="submit" id="botao" name="botao" value="Cadastrar">
            </td>
            <td>
               <input type="submit" id="botao" name="botao" value="Alterar">
            </td>
            <td>
               <input type="submit" id="botao" name="botao" value="Excluir">
            </td>
            <td>
               <input type="submit" id="botao" name="botao" value="Listar">
            </td>
           </tr>
        </table>
     </form>
    </div>
    <br />
    <div align="center">
        <c:if test="${not empty erro }">
            <H2><b><c:out value="${erro }"/></b></H2>
    </c:if>
    </div>
    <div class ="mensagem" align="center">
        <c:if test="${not empty saida }">
            <H3><b><c:out value="${saida }"/></b></H3>
    </c:if>
    </div>
    <br />
    <c:if test="${not empty disciplinas }">
      <table class="table_round">
        <thead>
           <tr>
               <th>Codigo</th>
                <th>Nome</th>
                 <th>Hora Inicio</th>
                  <th>Duração</th>
                  <th>Dia da Semana</th>
           </tr>
        </thead>
      </tbody>
          <c:forEach var="d" items="${disciplinas }">
     <tr>
        <td><c:out value ="${d.codigo }"/></td>
         <td><c:out value ="${d.nome }"/></td>
          <td><c:out value ="${d.horas_inicio }"/></td>
           <td><c:out value ="${d.duracao }"/></td>
            <td><c:out value ="${d.dia_semana }"/></td>
     </tr>
    </c:forEach>
    </tbody>
    </table>
    </c:if>
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
.calculado {
    background-color:#F5F5DC;
    border-radius: 20px; 
    outline: none;
    padding: 0 0.5rem;
    width: 200px; 
    height: 30px;
    border: 1px solid #C0C0C0; 

}

.cadastro {
    background-color:#F5F5DC;
    border-radius: 20px; 
    outline: none;
    padding: 0 0.5rem;
    width: 200px; 
    height: 30px;
    border: 1px solid #C0C0C0; 
}

#nome {
 width: 300px; 
    height: 30px
}

#nome_social {
 width: 250px; 
    height: 30px
}

#data_nascimento {
 width: 150px; 
 height: 30px
 }
 
 #conclusao_segundo_grau {
     width: 150px; 
     height: 30px
 }
 
 #pontuacao_vestibular {
  width: 150px; 
 height: 30px
 }
 
  #posicao_vestibular {
  width: 150px; 
 height: 30px
 }
 
 #ano_ingresso {
  width: 150px; 
 height: 30px
 }
 
 #ano_limite_graduacao{
  width: 100px; 
   height: 30px
 }
 
 #semestre_ingresso{
  width: 100px; 
 height: 30px
 }
 
 #semestre_limite_graduacao{
  width: 100px; 
 height: 30px
 }
 
 .title {
   margin-top: 2px;
   
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
 
form {
  margin-top: 10px;
  margin-left: 30px;
  background-color:	#dcdcdc;
  width: 600px; 
  height: 500px;
  padding: 10px;
  border-radius: 30px; 
 }
 
 
 
 
 .aluno {
   display:flex;
   gap: 25px;
   padding: 10px;
   margin-left:70px;
   
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
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
    background-color: #f0f0f0;
    font-size: 15px;
    
}

.table_round tr td{
    border: 1px solid #dddddd;
    padding: 8px;
    text-align: center;
   
    
}


</style>
</html>