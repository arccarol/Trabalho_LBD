<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href= "./webapp/style.css">
<title>Aluno</title>
</head>
<body class="tela_aluno">
    <div class="menu">
       <jsp:include page="menu.jsp"></jsp:include>
    </div>
    <br />
    <div align="center" class="container">
    <form action="aluno" method="post">
       <p class="title">
       </p>
         <p class="cadastrar"> Cadastre o Aluno</p>
       <table>
   
          <tr>
              <td class = "aluno" colspan="3">
              <p class = "title">CPF:</p>
                <input class="cadastro" type="text" 
                id="cpf" name="cpf" placeholder=""
                value='<c:out value="${aluno.cpf }"></c:out>'>
                 <input type="submit" id="botao" name="botao" value="Buscar">
              </td>
         </tr>
         <tr>
             <td class = "aluno" colspan="4">
              <p class = "title">Nome:</p>
                <input class="cadastro" type="text" id="nome" name="nome" placeholder=""
                 value='<c:out value="${aluno.nome }"></c:out>'>
            </td>
         </tr>
          <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Nome Social:</p>
                <input class="cadastro" type="text" id="nome_social" name="nome_social" placeholder=""
               value='<c:out value="${aluno.nome_social }"></c:out>'>
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Data de Nascimento:</p>
                <input class="cadastro" type="date" id="data_nascimento" name="data_nascimento" 
               value='<c:out value="${aluno.data_nascimento }"></c:out>'>
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Email Pessoal:</p>
                <input class="cadastro" type="text" id="email_pessoal" name="email_pessoal" 
               value='<c:out value="${aluno.email_pessoal }"></c:out>'> 
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Email Corporativo:</p>
                <input class="cadastro" type="text" id="email_corporativo" name="email_corporativo" 
               value='<c:out value="${aluno.email_corporativo }"></c:out>'> 
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Data de Conclusão do 2° Grau:</p>
                <input class="cadastro" type="date" id="conclusao_segundo_grau" name="conclusao_segundo_grau" 
               value='<c:out value="${aluno.conclusao_segundo_grau }"></c:out>'> 
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Instituição:</p>
                <input class="cadastro" type="text" id="instituicao_conclusao" name="instituicao_conclusao"
               value='<c:out value="${aluno.instituicao_conclusao }"></c:out>'> 
            </td>
         </tr>
         <tr>
             <td class = "aluno" colspan="4">
              <p class = "title">Pontuação Vestibular:</p>
                <input class="cadastro" type="text" id="pontuacao_vestibular" name="pontuacao_vestibular" 
               value='<c:out value="${aluno.pontuacao_vestibular }"></c:out>'> 
            </td>
         </tr>
         <tr>
           <td class = "aluno" colspan="4">
              <p class = "title">Posição Vestibular:</p>
                <input class="cadastro" type="number" id="posicao_vestibular" name="posicao_vestibular" 
               value='<c:out value="${aluno.posicao_vestibular }"></c:out>'> 
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Ano de Ingresso:</p>
                <input class="cadastro" type="text" id="ano_ingresso" name="ano_ingresso" 
               value='<c:out value="${aluno.ano_ingresso }"></c:out>'> 
            </td>
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Semestre de Ingresso:</p>
                <input class="cadastro" type="number" id="semestre_ingresso" name="semestre_ingresso" 
               value='<c:out value="${aluno.semestre_ingresso }"></c:out>'> 
            </td>
         </tr>
          <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Semestre Limite de Graduação:</p>
                <input class="cadastro" type="number" id="semestre_limite_graduacao" name="semestre_limite_graduacao" 
               value='<c:out value="${aluno.semestre_limite_graduacao }"></c:out>'> 
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
           <tr>
              <td class = "aluno" colspan="4">
              <p class = "title">RA:</p>
                <input class="calculado" type="number"
                id="ra" name="ra" placeholder=""
                value='<c:out value="${aluno.ra }"></c:out>'>
              </td> 
         </tr>
         <tr>
            <td class = "aluno" colspan="4">
              <p class = "title">Ano Limite de Graduação:</p>
                <input class="calculado" type="number" id="ano_limite_graduacao" name="ano_limite_graduacao" 
               value='<c:out value="${aluno.ano_limite_graduacao }"></c:out>'> 
            </td>
         </tr>
            </table>
              <p class = "aviso">Cadastre seu Telefone logo após</p>
              <div class= "link_telefone">
              <li class = "telefone"><a href="${pageContext.request.contextPath}/telefone">Telefone</a></li>
              </div>
              <p class = "aviso">Busque por seu CPF e obtenha seu RA e o Ano limite de Graduação</p>
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
    <div align="center">
        <c:choose>
        <c:when test="${not empty tipoTabela && tipoTabela eq 'Listar'}">
       <c:if test="${not empty alunos }">
        <table class="table_round">
        <thead>
           <tr>
               <th>CPF</th>
                <th>RA</th>
                 <th>Nome</th>
                  <th>Nome Social</th>
                  <th>Nascimento</th>
                   <th>Email Pessoal</th>
                    <th>Email Corporativo</th>
                     <th>Data Conclusão 2°Grau</th>
                      <th>Instituição de Conclusão</th>
                       <th>Pontuação Vestibular</th>
                        <th>Posição Vestibular</th>
                         <th>Ano Ingresso</th>
                          <th>Ano Limite</th>
                           <th>Semestre Ingresso</th>
                            <th>Semestre Limite</th>
                               <th>Curso</th>
                            
           </tr>
        </thead>
      </tbody>
          <c:forEach var="a" items="${alunos }">
     <tr>
        <td><c:out value ="${a.cpf }"/></td>
         <td><c:out value ="${a.ra }"/></td>
          <td><c:out value ="${a.nome }"/></td>
           <td><c:out value ="${a.nome_social }"/></td>
            <td><c:out value ="${a.data_nascimento }"/></td>
             <td><c:out value ="${a.email_pessoal }"/></td>
              <td><c:out value ="${a.email_corporativo }"/></td>
               <td><c:out value ="${a.conclusao_segundo_grau }"/></td>
                <td><c:out value ="${a.instituicao_conclusao }"/></td>
                 <td><c:out value ="${a.pontuacao_vestibular }"/></td>
                  <td><c:out value ="${a.posicao_vestibular }"/></td>
                   <td><c:out value ="${a.ano_ingresso }"/></td>
                    <td><c:out value ="${a.ano_limite_graduacao }"/></td>
                     <td><c:out value ="${a.semestre_ingresso }"/></td>
                      <td><c:out value ="${a.semestre_limite_graduacao }"/></td>
                      <td><c:out value="${a.curso.nome }" /></td>
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
  height: 1150px;
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
    font-size: 10px;
    
}

.table_round tr td{
    border: 1px solid #dddddd;
    padding: 8px;
    text-align: center;
   
    
}

.input_data {

   margin-left: -380px;

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

.telefone{
    list-style: none;
    font-weight: 600;
    font-family: "Poppins";
    font-size: 25px;

}

.link_telefone li a{
	color: #5F9EA0;
}

.link_telefone li a:hover{
	color: #191970;
}


</style>
</html>