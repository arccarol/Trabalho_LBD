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
import persistence.GenericDao;
import persistence.AlunoDao;
import persistence.DisciplinaDao;
import persistence.MatriculaDao;

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

        try {
            alunos = alunoDao.listar();
            disciplinas = disciplinaDao.listar();

        } catch (ClassNotFoundException | SQLException e) {
            erro = e.getMessage();

        } finally {
            request.setAttribute("erro", erro);
            request.setAttribute("alunos", alunos);
            request.setAttribute("disciplinas", disciplinas);

            RequestDispatcher rd = request.getRequestDispatcher("matricula.jsp");
            rd.forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        String cmd = request.getParameter("botao");;
        String alunoCpf = request.getParameter("aluno");
        String disciplinaCodigo = request.getParameter("disciplina");
        String data = request.getParameter("data_m");

        // saída
        String saida = "";
        String erro = "";
        Matricula m = new Matricula();

        List<Aluno> alunos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();
        List<Matricula> matriculas = new ArrayList<>();

        if (!cmd.contains("Listar")) {
        	Aluno a = new Aluno();
        	a.setCpf(alunoCpf);
        	m.setAluno(a);

        }

        try {
            GenericDao gDao = new GenericDao();
            AlunoDao alunoDao = new AlunoDao(gDao);

            alunos = listarAluno();
            disciplinas = listarDisciplinas();

            if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
            	Aluno a = new Aluno();
            	a.setCpf(alunoCpf);

                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(Integer.parseInt(disciplinaCodigo));

                m.setData(data);
                m.setAluno(a);
                m.setDisciplina(disciplina);
            }

            if (cmd.contains("Cadastrar")) {
                cadastrarMatricula(m);
                saida = "Matricula cadastrada com sucesso";
                m = null;
            }
            if (cmd.contains("Alterar")) {
                alterarMatricula(m);
                saida = "Matricula alterada com sucesso";
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

            RequestDispatcher rd = request.getRequestDispatcher("matricula.jsp");
            rd.forward(request, response);
        }
    }

    // Métodos existentes mantidos sem alterações

    private void cadastrarMatricula(Matricula m) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        MatriculaDao matriculaDao = new MatriculaDao(gDao);
        matriculaDao.inserir(m);
    }

    private void alterarMatricula(Matricula m) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        MatriculaDao matriculaDao = new MatriculaDao(gDao);
        matriculaDao.atualizar(m);
    }

    private void excluirMatricula(Matricula m) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        MatriculaDao matriculaDao = new MatriculaDao(gDao);
        matriculaDao.excluir(m);
    }

    private Matricula buscarMatricula(Matricula m) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        MatriculaDao matriculaDao = new MatriculaDao(gDao);
        m = matriculaDao.consultar(m);
        return m;
    }

    private List<Matricula> listarMatricula() throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        MatriculaDao matriculaDao = new MatriculaDao(gDao);
        List<Matricula> matriculas = matriculaDao.listar();
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
}
