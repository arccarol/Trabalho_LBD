package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Disciplina;
import model.Matricula;
import model.Status;

public class MatriculaDao implements ICrud<Matricula>, IMatriculaDao {

    private GenericDao gDao;

    public MatriculaDao(GenericDao gDao) {
        this.gDao = gDao;
    }

    @Override
    public String iudMatricula(String op, Matricula m) throws SQLException, ClassNotFoundException {
        Connection cc = null;
        CallableStatement cs = null;
        try {
            cc = gDao.getConnection();

            String gerenciarMatriculaSql = "{CALL GerenciarMatriculaD(?, ?, ?, ?, ?, ?, ?)}";
            cs = cc.prepareCall(gerenciarMatriculaSql);
            cs.setString(1, op);
            cs.setInt(2, m.getCodigo());
            cs.setString(3, m.getAluno().getCpf());
            cs.setInt(4, m.getDisciplina().getCodigo());
            cs.setString(5, m.getData());
            cs.setString(6, m.getStatus().getNome());
            cs.registerOutParameter(7, Types.VARCHAR);
            cs.execute();
            
            return cs.getString(7);
        } finally {
            if (cs != null) {
                cs.close();
            }
            if (cc != null) {
                cc.close();
            }
        }
    }




    @Override
    public void inserir(Matricula m) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "INSERT INTO Matricula (codigo, aluno, disciplina, data_m, nome_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getCodigo());
            ps.setString(2, m.getAluno().getCpf());
            ps.setInt(3, m.getDisciplina().getCodigo());
            ps.setString(4, m.getData());
            ps.setString(5, m.getStatus().getNome()); // Aqui está passando o nome do status
            ps.execute();
        }
    }


    @Override
    public void atualizar(Matricula m) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "UPDATE Matricula SET disciplina = ?, data_m = ?, nome_status = ? WHERE codigo = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getDisciplina().getCodigo());
            ps.setString(2, m.getData());
            ps.setString(3, m.getStatus().getNome());
            ps.setInt(4, m.getCodigo());
            ps.execute();
        }
    }


    @Override
    public void excluir(Matricula m) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "DELETE FROM Matricula WHERE codigo = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getCodigo());
            ps.execute();
        }
    }

    @Override
    public Matricula consultar(Matricula m) throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = gDao.getConnection();
            String sql = "SELECT m.codigo AS codigo, m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
                    + "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula, m.nome_status AS status "
                    + "FROM Matricula m "
                    + "INNER JOIN aluno a ON a.cpf = m.aluno "
                    + "INNER JOIN disciplina d ON d.codigo = m.disciplina "
                    + "WHERE m.codigo = ?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, m.getCodigo());
            rs = ps.executeQuery();

            if (rs.next()) {
                Aluno a = new Aluno();
                a.setCpf(rs.getString("cpfAluno"));
                a.setNome(rs.getString("nomeAluno"));

                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(rs.getInt("codigoDisciplina"));
                disciplina.setNome(rs.getString("nomeDisciplina"));
                
                m.setCodigo(rs.getInt("codigo"));
                m.setData(rs.getString("dataMatricula"));
                
                Status s = new Status();
                s.setNome(rs.getString("status"));

                m.setAluno(a);
                m.setStatus(s);
                m.setDisciplina(disciplina);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (c != null) {
                c.close();
            }
        }

        return m;
    }

    @Override
    public List<Matricula> listar() throws SQLException, ClassNotFoundException {
        List<Matricula> matriculas = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT m.codigo AS codigo, m.aluno AS cpfAluno, m.disciplina AS codigoDisciplina, "
        		+ "a.nome AS nomeAluno, d.nome AS nomeDisciplina, m.data_m AS dataMatricula, m.nome_status AS status "
        		+ "FROM Matricula m "
        		+ "INNER JOIN aluno a ON a.cpf = m.aluno "
        		+ "INNER JOIN disciplina d ON d.codigo = m.disciplina ";
        try (PreparedStatement ps = c.prepareStatement(sql);
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
                m.setCodigo(rs.getInt("codigo"));

                Status s = new Status();
                s.setNome(rs.getString("status"));
                
                m.setAluno(a);
                m.setDisciplina(disciplina);
                m.setStatus(s);

                matriculas.add(m);
            }
        }

        return matriculas;
    }
}