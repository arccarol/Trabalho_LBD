package controller;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;
import model.Disciplina;
import model.Matricula;
import model.Status;
import persistence.GenericDao;
import persistence.AlunoDao;
import persistence.DisciplinaDao;
import persistence.MatriculaDao;
import persistence.StatusDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/matricula")
public class MatriculaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MatriculaServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String erro = "";
        List<Aluno> alunos = new ArrayList<>();
        GenericDao gDao = new GenericDao();
        AlunoDao alunoDao = new AlunoDao(gDao);

        List<Disciplina> disciplinas = new ArrayList<>();
        DisciplinaDao disciplinaDao = new DisciplinaDao(gDao);
        
        List<Status> statuss = new ArrayList<>();
        StatusDao statusDao = new StatusDao(gDao);

        try {
            alunos = alunoDao.listar();
            disciplinas = disciplinaDao.listar();
            statuss = statusDao.listar();

        } catch (ClassNotFoundException | SQLException e) {
            erro = e.getMessage();

        } finally {
            request.setAttribute("erro", erro);
            request.setAttribute("alunos", alunos);
            request.setAttribute("disciplinas", disciplinas);
            request.setAttribute("statuss", statuss);

            RequestDispatcher rd = request.getRequestDispatcher("matricula.jsp");
            rd.forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        String cmd = request.getParameter("botao");
        String codigo = request.getParameter("codigo");
        String alunoCpf = request.getParameter("aluno");
        String disciplinaCodigo = request.getParameter("disciplina");
        String data = request.getParameter("data_m");
        String status = request.getParameter("status"); // Alteração aqui: mudança para "status"

        // saída
        String saida = "";
        String erro = "";
        Matricula m = new Matricula();

        List<Aluno> alunos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();
        List<Matricula> matriculas = new ArrayList<>();
        List<Status> statuss = new ArrayList<>();

        if (!cmd.contains("Listar")) {
            m.setCodigo(Integer.parseInt(codigo));
        }

        try {
            GenericDao gDao = new GenericDao();
            AlunoDao alunoDao = new AlunoDao(gDao);
            StatusDao statusDao = new StatusDao(gDao);

            alunos = listarAluno();
            disciplinas = listarDisciplinas();
            statuss = listarStatus();

            if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
                Aluno a = new Aluno();
                a.setCpf(alunoCpf);

                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(Integer.parseInt(disciplinaCodigo));
                
                m.setCodigo(Integer.parseInt(codigo));
                
                Status s = new Status();
                s.setNome(status);

                m.setData(data);
                m.setAluno(a);
                m.setDisciplina(disciplina);
                m.setStatus(s);
            }

            if (cmd.contains("Cadastrar")) {
                saida = cadastrarMatricula(m);
                m = null;
            }
            if (cmd.contains("Alterar")) {
                saida = alterarMatricula(m);
                m = null;
            }
            if (cmd.contains("Excluir")) {
                excluirMatricula(m);
                saida = "Matricula excluída com sucesso";
                m = null;
            }
            if (cmd.contains("Buscar")) {
                m = buscarMatricula(m);
            }

            if (cmd.contains("Listar")) {
                matriculas = listarMatricula();
                request.setAttribute("tipoTabela", "Listar");
            }

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            request.setAttribute("saida", saida);
            request.setAttribute("erro", erro);
            request.setAttribute("matriculas", matriculas);
            request.setAttribute("alunos", alunos);
            request.setAttribute("disciplinas", disciplinas);
            request.setAttribute("statuss", statuss);

            RequestDispatcher rd = request.getRequestDispatcher("matricula.jsp");
            rd.forward(request, response);
        }
    }


  

    private String cadastrarMatricula(Matricula m)throws SQLException, ClassNotFoundException {
		 GenericDao gDao = new GenericDao();
	        MatriculaDao pDao = new MatriculaDao(gDao);
	        return pDao.iudMatricula("I", m);
	}


	private String alterarMatricula(Matricula m)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
	    MatriculaDao pDao = new MatriculaDao(gDao);
		String saida = pDao.iudMatricula("U", m);
		return saida;
	}


	private void excluirMatricula(Matricula m)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		 MatriculaDao pDao = new MatriculaDao(gDao);
		pDao.excluir(m);
	}


	private Matricula buscarMatricula(Matricula m)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		 MatriculaDao pDao = new MatriculaDao(gDao);
	    m = pDao.consultar(m);
		return m;
	}


	private List<Matricula> listarMatricula() throws SQLException, ClassNotFoundException{
		GenericDao gDao = new GenericDao();
		MatriculaDao pDao = new MatriculaDao(gDao);
	    List<Matricula> matriculas = pDao.listar();
	    
		 return matriculas;
		 
	}
	
	  private List<Aluno> listarAluno() throws SQLException, ClassNotFoundException {
	        GenericDao gDao = new GenericDao();
	        AlunoDao alunoDao = new AlunoDao(gDao);
	        List<Aluno> alunos = alunoDao.listar();
	        return alunos;
	    }

	    private List<Disciplina> listarDisciplinas() throws SQLException, ClassNotFoundException {
	        GenericDao gDao = new GenericDao();
	        DisciplinaDao disciplinaDao = new DisciplinaDao(gDao);
	        List<Disciplina> disciplinas = disciplinaDao.listar();
	        return disciplinas;
	    }
	    
	    private List<Status> listarStatus() throws SQLException, ClassNotFoundException {
	        GenericDao gDao = new GenericDao();
	        StatusDao statusDao = new StatusDao(gDao);
	        List<Status> statuss = statusDao.listar();
	        return statuss;
	    }
}