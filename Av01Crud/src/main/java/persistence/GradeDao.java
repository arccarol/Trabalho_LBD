package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Curso;
import model.Disciplina;
import model.Grade;

public class GradeDao implements ICrud<Grade> {

    private GenericDao gDao;

    public GradeDao(GenericDao gDao) {
        this.gDao = gDao;
    }

    public void inserir(Grade grade) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sqlVerificar = "SELECT 1 FROM Grade WHERE curso = ? AND disciplina = ?";
        String sqlInserir = "INSERT INTO Grade (codigo, curso, disciplina) VALUES (?, ?, ?)";
        
        try (PreparedStatement psVerificar = c.prepareStatement(sqlVerificar)) {
            psVerificar.setInt(1, grade.getCurso().getCodigo());
            psVerificar.setInt(2, grade.getDisciplina().getCodigo());
            try (ResultSet rs = psVerificar.executeQuery()) {
                if (rs.next()) {
                	
                    throw new SQLException("A disciplina já está cadastrada no curso.");
                }
            }
        }
        
        try (PreparedStatement psInserir = c.prepareStatement(sqlInserir)) {
        	psInserir.setInt(1, grade.getCodigo());
            psInserir.setInt(2, grade.getCurso().getCodigo());
            psInserir.setInt(3, grade.getDisciplina().getCodigo());
            psInserir.executeUpdate();
        }
    }

    @Override
    public void atualizar(Grade grade) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "UPDATE Grade SET disciplina = ?, curso = ? WHERE codigo = ?";;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, grade.getDisciplina().getCodigo());
            ps.setInt(2, grade.getCurso().getCodigo());
            ps.setInt(3, grade.getCodigo());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(Grade grade) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "DELETE FROM Grade WHERE codigo = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, grade.getCodigo());
            ps.executeUpdate();
        }
    }


    @Override
    public Grade consultar(Grade grade) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "SELECT g.codigo AS codigo, g.curso AS codigoCurso, g.disciplina AS codigoDisciplina, "
                   + "c.nome AS nomeCurso, d.nome AS nomeDisciplina "
                   + "FROM Grade g "
                   + "INNER JOIN curso c ON c.codigo = g.curso "
                   + "INNER JOIN disciplina d ON d.codigo = g.disciplina "
                   + "WHERE g.codigo = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, grade.getCodigo());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Curso curso = new Curso();
                    curso.setCodigo(rs.getInt("codigoCurso"));
                    curso.setNome(rs.getString("nomeCurso"));
    
                    Disciplina disciplina = new Disciplina();
                    disciplina.setCodigo(rs.getInt("codigoDisciplina"));
                    disciplina.setNome(rs.getString("nomeDisciplina"));
                
                    grade.setCodigo(rs.getInt("codigo"));
                    
                    grade.setCurso(curso);
                    grade.setDisciplina(disciplina);
                    
                }
            }
        }
        return grade;
    }

    @Override
    public List<Grade> listar() throws SQLException, ClassNotFoundException {
        List<Grade> grades = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT g.codigo AS codigo, g.curso AS codigoCurso, g.disciplina AS codigoDisciplina, "
                   + "c.nome AS nomeCurso, d.nome AS nomeDisciplina "
                   + "FROM Grade g "
                   + "INNER JOIN curso c ON c.codigo = g.curso "
                   + "INNER JOIN disciplina d ON d.codigo = g.disciplina";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Grade grade = new Grade();

                Curso curso = new Curso();
                curso.setCodigo(rs.getInt("codigoCurso"));
                curso.setNome(rs.getString("nomeCurso"));
    
                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(rs.getInt("codigoDisciplina"));
                disciplina.setNome(rs.getString("nomeDisciplina"));
            
                grade.setCodigo(rs.getInt("codigo"));
                grade.setCurso(curso);
                grade.setDisciplina(disciplina);
    
                grades.add(grade);
            }
        }
        return grades;
    }
}
    
 
