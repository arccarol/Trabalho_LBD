package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Curso;
import model.Disciplina;
import model.Grade;
import persistence.GenericDao;
import persistence.CursoDao;
import persistence.DisciplinaDao;
import persistence.GradeDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/grade")
public class GradeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GradeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String erro = "";
        List<Curso> cursos = new ArrayList<>();
        GenericDao gDao = new GenericDao();
        CursoDao cursoDao = new CursoDao(gDao);

        List<Disciplina> disciplinas = new ArrayList<>();
        DisciplinaDao disciplinaDao = new DisciplinaDao(gDao);

        try {
            cursos = cursoDao.listar();
            disciplinas = disciplinaDao.listar();

        } catch (ClassNotFoundException | SQLException e) {
            erro = e.getMessage();

        } finally {
            request.setAttribute("erro", erro);
            request.setAttribute("cursos", cursos);
            request.setAttribute("disciplinas", disciplinas);

            RequestDispatcher rd = request.getRequestDispatcher("grade.jsp");
            rd.forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // entrada
        String cmd = request.getParameter("botao");
        String cursoCodigo = request.getParameter("curso");
        String disciplinaCodigo = request.getParameter("disciplina");

        // saída
        String saida = "";
        String erro = "";
        Grade grade = new Grade();

        List<Curso> cursos = new ArrayList<>();
        List<Disciplina> disciplinas = new ArrayList<>();
        List<Grade> grades = new ArrayList<>();

        if (!cmd.contains("Listar")) {
        	Curso curso = new Curso();
        	curso.setCodigo(Integer.parseInt(cursoCodigo));
        	grade.setCurso(curso);

        }

        try {
            GenericDao gDao = new GenericDao();
            GradeDao gradeDao = new GradeDao(gDao);

            cursos = listarCursos();
            disciplinas = listarDisciplinas();

            if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
                Curso curso = new Curso();
                curso.setCodigo(Integer.parseInt(cursoCodigo));

                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(Integer.parseInt(disciplinaCodigo));

                grade.setCurso(curso);
                grade.setDisciplina(disciplina);
            }

            if (cmd.contains("Cadastrar")) {
                cadastrarGrade(grade);
                saida = "Grade cadastrada com sucesso";
                grade = null;
            }
            if (cmd.contains("Alterar")) {
                alterarGrade(grade);
                saida = "Grade alterada com sucesso";
                grade = null;
            }
            if (cmd.contains("Excluir")) {
                excluirGrade(grade);
                saida = "Grade excluída com sucesso";
                grade = null;
            }
            if (cmd.contains("Buscar")) {
                grade = buscarGrade(grade);
            }

            if (cmd.contains("Listar")) {
                grades = listarGrades();
                request.setAttribute("tipoTabela", "Listar");
            }

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            request.setAttribute("saida", saida);
            request.setAttribute("erro", erro);
            request.setAttribute("grade", grade);
            request.setAttribute("cursos", cursos);
            request.setAttribute("grades", grades);
            request.setAttribute("disciplinas", disciplinas);

            RequestDispatcher rd = request.getRequestDispatcher("grade.jsp");
            rd.forward(request, response);
        }
    }

    private void cadastrarGrade(Grade grade) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        GradeDao gradeDao = new GradeDao(gDao);
        gradeDao.inserir(grade);
    }

    private void alterarGrade(Grade grade) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        GradeDao gradeDao = new GradeDao(gDao);
        gradeDao.atualizar(grade);
    }

    private void excluirGrade(Grade grade) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        GradeDao gradeDao = new GradeDao(gDao);
        gradeDao.excluir(grade);
    }

    private Grade buscarGrade(Grade grade) throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        GradeDao gradeDao = new GradeDao(gDao);
        grade = gradeDao.consultar(grade);
        return grade;
    }

    private List<Grade> listarGrades() throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        GradeDao gradeDao = new GradeDao(gDao);
        List<Grade> grades = gradeDao.listar();
        return grades;
    }

    private List<Curso> listarCursos() throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        CursoDao cursoDao = new CursoDao(gDao);
        List<Curso> cursos = cursoDao.listar();
        return cursos;
    }

    private List<Disciplina> listarDisciplinas() throws SQLException, ClassNotFoundException {
        GenericDao gDao = new GenericDao();
        DisciplinaDao disciplinaDao = new DisciplinaDao(gDao);
        List<Disciplina> disciplinas = disciplinaDao.listar();
        return disciplinas;
    }
}

