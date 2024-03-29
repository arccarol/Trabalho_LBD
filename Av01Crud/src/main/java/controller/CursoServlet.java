package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Curso;
import persistence.CursoDao;
import persistence.GenericDao;

@WebServlet("/curso")
public class CursoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public CursoServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("curso.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("botao");
        String codigo = request.getParameter("codigo");
        String nome = request.getParameter("nome");
        String carga_horaria = request.getParameter("carga_horaria");
        String sigla = request.getParameter("sigla");
        String nota_enade = request.getParameter("nota_enade");

        String saida="";
        String erro="";
        Curso c = new Curso();
        List<Curso> cursos = new ArrayList<>();

        if(!cmd.contains("Listar")) {
            c.setCodigo(Integer.parseInt(codigo));
        }

        if(cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
	    c.setNome(nome);
            c.setCarga_horaria(carga_horaria);
            c.setSigla(sigla);
            
            double nota_Enade = Double.parseDouble(nota_enade);
            
            c.setNota_enade(nota_Enade);
        }

        try {
            if (cmd.contains("Cadastrar")) {
                saida = cadastrarCurso(c);
                c = null;
            }
            if (cmd.contains("Alterar")) {
                saida = alterarCurso(c);
                c = null;
            }
            if (cmd.contains("Excluir")) {
                saida = excluirCurso(c);
                c = null;
            }
            if (cmd.contains("Buscar")) {
                c = buscarCurso(c);
            }
            if (cmd.contains("Listar")) {
                cursos = listarCurso();
            }
        } catch(SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            request.setAttribute("saida", saida);
            request.setAttribute("erro", erro);
            request.setAttribute("curso", c);
            request.setAttribute("cursos", cursos);
            RequestDispatcher rd = request.getRequestDispatcher("curso.jsp");
            rd.forward(request, response);
        }
    }


	private String cadastrarCurso(Curso c)throws SQLException, ClassNotFoundException {
		 GenericDao gDao = new GenericDao();
	        CursoDao pDao = new CursoDao(gDao);
	        return pDao.iudCurso("I", c);
	}


	private String alterarCurso(Curso c)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    CursoDao pDao = new CursoDao (gDao);
		String saida = pDao.iudCurso("U", c);
		return saida;
	}


	private String excluirCurso(Curso c)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    CursoDao pDao = new CursoDao (gDao);
		String saida = pDao.iudCurso("D", c);
		return saida;
	}


	private Curso buscarCurso(Curso c)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    CursoDao pDao = new CursoDao (gDao);
	    c = pDao.consultar(c);
		return c;
	}


	private List<Curso> listarCurso() throws SQLException, ClassNotFoundException{
		GenericDao gDao = new GenericDao();
	    CursoDao pDao = new CursoDao (gDao);
	    List<Curso> cursos = pDao.listar();
	    
		 return cursos;
		 
	}

}