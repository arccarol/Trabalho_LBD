package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Disciplina;
import model.Matricula;

public class MatriculaDao implements ICrud<Matricula> {

    private GenericDao gDao;

    public MatriculaDao(GenericDao gDao) {
        this.gDao = gDao;
    }

    @Override
    public void inserir(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Matricula (aluno, disciplina, data_m) VALUES (?, ?, ?)";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getAluno().getCpf());
            ps.setInt(2, m.getDisciplina().getCodigo());
            ps.setString(3, m.getData());
            ps.executeUpdate();
        }
    }

    @Override
    public void atualizar(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Matricula SET disciplina = ?, data_m = ? WHERE aluno = ?";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getDisciplina().getCodigo());
            ps.setString(2, m.getData());
            ps.setString(3, m.getAluno().getCpf());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Matricula WHERE aluno = ?";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getAluno().getCpf());
            ps.executeUpdate();
        }
    }

    @Override
    public Matricula consultar(Matricula m) throws SQLException, ClassNotFoundException {
        String sql = "SELECT m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
                   + "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula "
                   + "FROM Matricula m "
                   + "INNER JOIN aluno a ON a.cpf = m.aluno "
                   + "INNER JOIN disciplina d ON d.codigo = m.disciplina "
                   + "WHERE m.aluno = ?";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getAluno().getCpf());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aluno a = new Aluno();
                    a.setCpf(rs.getString("cpfAluno"));
                    a.setNome(rs.getString("nomeAluno"));

                    Disciplina disciplina = new Disciplina();
                    disciplina.setCodigo(rs.getInt("codigoDisciplina"));
                    disciplina.setNome(rs.getString("nomeDisciplina"));

                    m.setData(rs.getString("dataMatricula"));

                    m.setAluno(a);
                    m.setDisciplina(disciplina);
                }
            }
        }
        return m;
    }

    @Override
    public List<Matricula> listar() throws SQLException, ClassNotFoundException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
                + "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula "
                + "FROM Matricula m "
                + "INNER JOIN aluno a ON a.cpf = m.aluno "
                + "INNER JOIN disciplina d ON d.codigo = m.disciplina ";
        try (Connection c = gDao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Matricula m = new Matricula();

                Aluno a = new Aluno();
                a.setCpf(rs.getString("cpfAluno"));
                a.setNome(rs.getString("nomeAluno"));

                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(rs.getInt("codigoDisciplina"));
                disciplina.setNome(rs.getString("nomeDisciplina"));

                m.setData(rs.getString("dataMatricula"));

                m.setAluno(a);
                m.setDisciplina(disciplina);

                matriculas.add(m);
            }
        }
        return matriculas;
    }
}

