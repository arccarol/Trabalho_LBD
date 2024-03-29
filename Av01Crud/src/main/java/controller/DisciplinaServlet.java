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
import model.Disciplina;
import persistence.GenericDao;
import persistence.DisciplinaDao;

@WebServlet("/disciplina")
public class DisciplinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DisciplinaServlet() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    	RequestDispatcher rd = request.getRequestDispatcher("disciplina.jsp");
		rd.forward(request, response);
    }
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//entrada
		String cmd = request.getParameter("botao");
		String codigo = request.getParameter("codigo");
		String nome = request.getParameter("nome");
		String horas_inicio = request.getParameter("horas_inicio");
		String duracao = request.getParameter("duracao");
		String dia_semana = request.getParameter("dia_semana");

		//saida
		String saida="";
		String erro="";
		Disciplina d = new Disciplina();
		List<Disciplina> disciplinas = new ArrayList<>();
		
		if(!cmd.contains("Listar")) {
			d.setCodigo(Integer.parseInt(codigo));
		}
		if(cmd.contains("Cadastrar") || cmd.contains("Alterar")){
	    	 d.setNome(nome);
	    	 d.setHoras_inicio(horas_inicio);
	    	 d.setDuracao(Integer.parseInt(duracao));
	    	 d.setDia_semana(dia_semana);
	         
		}
		
		try {
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarDisciplina(d);
				d = null;
			}
			if (cmd.contains("Alterar")) {
				saida = alterarDisciplina(d);
				d = null;
			}
			if (cmd.contains("Excluir")) {
				saida = excluirDisciplina(d);
				d = null;
			}
			if (cmd.contains("Buscar")) {
				d = buscarDisciplina(d);
			}
			if (cmd.contains("Listar")) {
			    disciplinas = listarDisciplina();
			}
		} catch(SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("disciplina", d);
			request.setAttribute("disciplinas", disciplinas);
			
			RequestDispatcher rd = request.getRequestDispatcher("disciplina.jsp");
			rd.forward(request, response);
		}
	}

	private String cadastrarDisciplina(Disciplina d)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    DisciplinaDao pDao = new DisciplinaDao (gDao);
		String saida = pDao.iudDisciplina("I", d);
		return saida;
		
	}

	private String alterarDisciplina(Disciplina d)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		DisciplinaDao pDao = new DisciplinaDao (gDao);
		String saida = pDao.iudDisciplina("U", d);
		return saida;
		
	}

	private String excluirDisciplina(Disciplina d)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		DisciplinaDao pDao = new DisciplinaDao (gDao);
		String saida = pDao.iudDisciplina("D", d);
		return saida;
		
	}

	private Disciplina buscarDisciplina (Disciplina d)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		DisciplinaDao pDao = new DisciplinaDao (gDao);
		d = pDao.consultar(d);
		return d;
	
	}

	private List<Disciplina> listarDisciplina()throws SQLException, ClassNotFoundException {
		
		GenericDao gDao = new GenericDao();
		DisciplinaDao pDao = new DisciplinaDao (gDao);
		List<Disciplina> disciplinas = pDao.listar();
		
	 return disciplinas;
	}

}