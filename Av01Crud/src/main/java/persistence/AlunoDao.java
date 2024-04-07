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
import model.Curso;
import model.Grade;

public class AlunoDao implements ICrud<Aluno>, IAlunoDao {

    private GenericDao gDao;

    public AlunoDao(GenericDao gDao) {
        this.gDao = gDao;
    }

    @Override
    public void inserir(Aluno a) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "INSERT INTO Aluno VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setLong(1, a.getRa());
        ps.setString(2, a.getCpf());
        ps.setString(3, a.getNome());
        ps.setString(4, a.getNome_social());
        ps.setString(5, a.getData_nascimento());
        ps.setString(6, a.getEmail_pessoal());
        ps.setString(7, a.getEmail_corporativo());
        ps.setString(8, a.getConclusao_segundo_grau());
        ps.setString(9, a.getInstituicao_conclusao());
        ps.setFloat(10, (float) a.getPontuacao_vestibular());
        ps.setInt(11, a.getPosicao_vestibular());
        ps.setInt(12, a.getAno_ingresso());
        ps.setInt(13, a.getAno_limite_graduacao());
        ps.setInt(14, a.getSemestre_ingresso());
        ps.setInt(15, a.getSemestre_limite_graduacao());
        ps.setInt(16, a.getCurso().getCodigo());

        ps.execute();
        ps.close();
        c.close();
    }

    @Override
    public void atualizar(Aluno a) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "UPDATE Aluno SET nome = ?, nome_social = ?, data_nascimento = ?, email_pessoal = ?, "
                + "email_corporativo = ?, conclusao_segundo_grau = ?, instituicao_conclusao = ?, pontuacao_vestibular = ?, "
                + "posicao_vestibular = ?, ano_ingresso = ?, ano_limite_graduacao = ?, semestre_ingresso = ?, "
                + "semestre_limite_graduacao = ?, curso = ? WHERE cpf = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, a.getNome());
        ps.setString(2, a.getNome_social());
        ps.setString(3, a.getData_nascimento());
        ps.setString(4, a.getEmail_pessoal());
        ps.setString(5, a.getEmail_corporativo());
        ps.setString(6, a.getConclusao_segundo_grau());
        ps.setString(7, a.getInstituicao_conclusao());
        ps.setFloat(8, (float) a.getPontuacao_vestibular());
        ps.setInt(9, a.getPosicao_vestibular());
        ps.setInt(10, a.getAno_ingresso());
        ps.setInt(11, a.getAno_limite_graduacao());
        ps.setInt(12, a.getSemestre_ingresso());
        ps.setInt(13, a.getSemestre_limite_graduacao());
        ps.setInt(14, a.getCurso().getCodigo());
        ps.setString(15, a.getCpf());

        ps.execute();
        ps.close();
        c.close();
    }

    @Override
    public void excluir(Aluno a) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "DELETE FROM Aluno WHERE cpf = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getCpf());
            ps.executeUpdate();
        }
    }



    @Override
    public Aluno consultar(Aluno a) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "SELECT A.ra, A.cpf, A.nome, A.nome_social, A.data_nascimento, A.email_pessoal, A.email_corporativo, A.conclusao_segundo_grau, "
                + "A.instituicao_conclusao, A.pontuacao_vestibular, A.posicao_vestibular, A.ano_ingresso, A.ano_limite_graduacao, "
                + "A.semestre_ingresso, A.semestre_limite_graduacao, A.curso AS codigoCurso, C.nome AS nomeCurso "
                + "FROM Aluno A "
                + "INNER JOIN Curso C ON C.codigo = A.curso "
                + "WHERE cpf = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, a.getCpf());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            a.setRa(rs.getInt("ra"));
            a.setCpf(rs.getString("cpf"));
            a.setNome(rs.getString("nome"));
            a.setNome_social(rs.getString("nome_social"));
            a.setData_nascimento(rs.getString("data_nascimento"));
            a.setEmail_pessoal(rs.getString("email_pessoal"));
            a.setEmail_corporativo(rs.getString("email_corporativo"));
            a.setConclusao_segundo_grau(rs.getString("conclusao_segundo_grau"));
            a.setInstituicao_conclusao(rs.getString("instituicao_conclusao"));
            a.setPontuacao_vestibular(rs.getDouble("pontuacao_vestibular"));
            a.setPosicao_vestibular(rs.getInt("posicao_vestibular"));
            a.setAno_ingresso(rs.getInt("ano_ingresso"));
            a.setAno_limite_graduacao(rs.getInt("ano_limite_graduacao"));
            a.setSemestre_ingresso(rs.getInt("semestre_ingresso"));
            a.setSemestre_limite_graduacao(rs.getInt("semestre_limite_graduacao"));

            Curso curso = new Curso();
            curso.setCodigo(rs.getInt("codigoCurso"));
            curso.setNome(rs.getString("nomeCurso"));

            a.setCurso(curso);
        }
        rs.close();
        ps.close();
        c.close();
        return a;
    }

    @Override
    public List<Aluno> listar() throws SQLException, ClassNotFoundException {
        List<Aluno> alunos = new ArrayList<>();
        Connection c = gDao.getConnection();
        String sql = "SELECT A.ra, A.cpf, A.nome, A.nome_social, A.data_nascimento, A.email_pessoal, A.email_corporativo, A.conclusao_segundo_grau, "
                + "A.instituicao_conclusao, A.pontuacao_vestibular, A.posicao_vestibular, A.ano_ingresso, A.ano_limite_graduacao, "
                + "A.semestre_ingresso, A.semestre_limite_graduacao, A.curso AS codigoCurso, C.nome AS nomeCurso "
                + "FROM Aluno A "
                + "INNER JOIN Curso C ON C.codigo = A.curso";
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Aluno a = new Aluno();
                a.setRa(rs.getInt("ra"));
                a.setCpf(rs.getString("cpf"));
                a.setNome(rs.getString("nome"));
                a.setNome_social(rs.getString("nome_social"));
                a.setData_nascimento(rs.getString("data_nascimento"));
                a.setEmail_pessoal(rs.getString("email_pessoal"));
                a.setEmail_corporativo(rs.getString("email_corporativo"));
                a.setConclusao_segundo_grau(rs.getString("conclusao_segundo_grau"));
                a.setInstituicao_conclusao(rs.getString("instituicao_conclusao"));
                a.setPontuacao_vestibular(rs.getDouble("pontuacao_vestibular"));
                a.setPosicao_vestibular(rs.getInt("posicao_vestibular"));
                a.setAno_ingresso(rs.getInt("ano_ingresso"));
                a.setAno_limite_graduacao(rs.getInt("ano_limite_graduacao"));
                a.setSemestre_ingresso(rs.getInt("semestre_ingresso"));
                a.setSemestre_limite_graduacao(rs.getInt("semestre_limite_graduacao"));

                Curso curso = new Curso();
                curso.setCodigo(rs.getInt("codigoCurso"));
                curso.setNome(rs.getString("nomeCurso"));

                a.setCurso(curso);

                alunos.add(a);
            }
        }
        return alunos;
    }

    @Override
    public String iudAluno(String op, Aluno a) throws SQLException, ClassNotFoundException {
        Connection c = gDao.getConnection();
        String sql = "CALL GerenciarMatricula(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        CallableStatement cs = c.prepareCall(sql);
        cs.setString(1, op);
        cs.setString(2, a.getCpf());
        cs.setString(3, a.getNome());
        cs.setString(4, a.getNome_social());
        cs.setString(5, a.getData_nascimento());
        cs.setString(6, a.getEmail_pessoal());
        cs.setString(7, a.getEmail_corporativo());
        cs.setString(8, a.getConclusao_segundo_grau());
        cs.setString(9, a.getInstituicao_conclusao());
        cs.setFloat(10, (float) a.getPontuacao_vestibular());
        cs.setInt(11, a.getPosicao_vestibular());
        cs.setInt(12, a.getAno_ingresso());
        cs.setInt(13, a.getSemestre_ingresso());
        cs.setInt(14, a.getSemestre_limite_graduacao());
        cs.setInt(15, a.getCurso().getCodigo());
        cs.registerOutParameter(16, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(16);
        cs.close();
        c.close();
        return saida;
    }
}

	

