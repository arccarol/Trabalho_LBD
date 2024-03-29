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

public class CursoDao implements ICrud<Curso>, ICursoDao{

private GenericDao gDao;
	

	public CursoDao(GenericDao gDao) {
		this.gDao = gDao;
	}


	@Override
	public String iudCurso(String op, Curso c) throws SQLException, ClassNotFoundException {
		Connection cc = gDao.getConnection();
        String sql = "CALL GerenciarCurso(?,?,?,?,?,?,?)";
        CallableStatement cs = cc.prepareCall(sql);
        cs.setString(1, op);
        cs.setInt(2, c.getCodigo());
        cs.setString(3, c.getNome());
        cs.setString(4, c.getCarga_horaria());
        cs.setString(5, c.getSigla());
        cs.setDouble(6, c.getNota_enade());
        cs.registerOutParameter(7, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(7);
        cs.close();
        cc.close();
        return saida;
	}


	@Override
	public void inserir(Curso t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
	    String sql = "INSERT INTO Curso (codigo, nome, carga_horaria, sigla, nota_enade) VALUES (?, ?, ?, ?, ?)";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setInt(1, t.getCodigo());
	    ps.setString(2, t.getNome());
	    ps.setString(3, t.getCarga_horaria());
	    ps.setString(4, t.getSigla());
	    ps.setDouble(5, t.getNota_enade());
	    ps.execute();
	    ps.close();
	    c.close();
		
	}


	@Override
	public void atualizar(Curso t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE Curso SET nome = ?, carga_horaria = ?, sigla = ?, nota_enade = ?  WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, t.getNome());
		ps.setString(2, t.getCarga_horaria());
		ps.setString(4, t.getSigla()); 
		ps.setDouble(4, t.getNota_enade()); 

		ps.execute();
		ps.close();
		c.close();
		
	}


	@Override
	public void excluir(Curso t) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE Curso WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, t.getCodigo());
		ps.execute();
		ps.close();
		c.close();
		
	}


	@Override
	public Curso consultar(Curso t) throws SQLException, ClassNotFoundException {
		 Connection c = gDao.getConnection();
		    String sql = "SELECT codigo, nome, carga_horaria, sigla, nota_enade FROM Curso WHERE codigo = ?";
		    PreparedStatement ps = c.prepareStatement(sql);
		    ps.setInt(1, t.getCodigo());
		    ResultSet rs = ps.executeQuery();
		    if (rs.next()) {
		        t.setNome(rs.getString("nome"));
		        t.setCarga_horaria(rs.getString("carga_horaria"));
		        t.setSigla(rs.getString("sigla"));
		        t.setNota_enade(rs.getDouble("nota_enade"));
		    }
		    rs.close();
		    ps.close();
		    c.close();
		    return t;
	}


	@Override
	public List<Curso> listar() throws SQLException, ClassNotFoundException {
		  List<Curso> cursos = new ArrayList<>();    
		    Connection cc = gDao.getConnection();
		    String sql = "SELECT codigo, nome, carga_horaria, sigla , nota_enade FROM Curso"; 
		    PreparedStatement ps = cc.prepareStatement(sql);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        Curso c = new Curso();
		        c.setCodigo(rs.getInt("codigo")); 
		        c.setNome(rs.getString("nome"));
		        c.setCarga_horaria(rs.getString("carga_horaria"));
		        c.setSigla(rs.getString("sigla"));
		        c.setNota_enade(rs.getDouble("nota_enade"));
		        cursos.add(c);
		    }
		    rs.close();
		    ps.close();
		    cc.close();
		    return cursos;
	}
}