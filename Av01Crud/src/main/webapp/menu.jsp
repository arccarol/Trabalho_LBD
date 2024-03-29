<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href= "./webapp/style.css">
<title></title>
</head>
<body>
  <nav id="menu">
   <ul>
   <div class="logo">
   <li>
    <img alt="Logo" src="imagens/logo_trabalho.webp">
</li>
</div>
<li><a href="index.jsp">Home</a></li>
<li><a href="${pageContext.request.contextPath}/aluno">Aluno</a></li>
<li><a href="${pageContext.request.contextPath}/disciplina">Disciplina</a></li>
<li><a href="${pageContext.request.contextPath}/curso">Curso</a></li>
<li><a href="${pageContext.request.contextPath}/grade">Grade</a></li>
<li><a href="${pageContext.request.contextPath}/matricula">Matricula</a></li>

   </ul>
 </nav>
</body>

<style>

* {
    margin: 0;
    padding: 0;
    border: 0;
    box-sizing: border-box;
    font-family: "Poppins";
    text-decoration: none;
    color: black;
  
}

ul{
    list-style: none;
}


#menu {
	display: flex;
    flex-direction: row;
    gap: 2rem;
    cursor: pointer;
    margin-left: 0px;
    font-weight: 600;
    font-size: 20px;
}

#menu ul {
	display: flex;
	margin-top: 20px;
    margin-left: 0;
    gap: 5rem;
    margin-left: 70px;
		
}

#menu ul li a{
	color: #5F9EA0;
	margin-top: 30px;
}

#menu ul li a:hover{
	color: #191970;
}

.logo {
		margin-top: -10px;
		margin-right: 60px
	
}

img {
	
	width: 60px; 
	height: 50px;
}


}

</style>
</html>