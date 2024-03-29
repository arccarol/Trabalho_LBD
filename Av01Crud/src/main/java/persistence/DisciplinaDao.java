package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Disciplina;

public class DisciplinaDao implements ICrud<Disciplina>, IDisciplinaDao { 
	

private GenericDao gDao;
	

	public DisciplinaDao(GenericDao gDao) {
		this.gDao = gDao;
	}
	
	

	@Override
	public void inserir(Disciplina d) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO Disciplina VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, d.getCodigo());
		ps.setString(2, d.getNome());
		ps.setString(3, d.getHoras_inicio());
		ps.setInt(4, d.getDuracao());
		ps.setString(5, d.getDia_semana()); 
	
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void atualizar(Disciplina d) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE Disciplina SET nome = ?, horas_inicio = ?, duracao = ?, dia_semana = ? WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, d.getCodigo());
		ps.setString(2, d.getNome());
		ps.setString(3, d.getHoras_inicio());
		ps.setInt(4, d.getDuracao()); 
		ps.setString(5, d.getDia_semana()); 
	
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void excluir(Disciplina d) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE Disciplina WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, d.getCodigo());
		ps.execute();
		ps.close();
		c.close();
		
	}

	@Override
	public Disciplina consultar(Disciplina d) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, horas_inicio, duracao , dia_semana FROM Disciplina WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
	    ps.setInt(1, d.getCodigo());
	    ResultSet rs = ps.executeQuery();
	     if (rs.next()) {
	    	 d.setCodigo(rs.getInt("codigo"));
	    	 d.setNome(rs.getString("nome"));
	    	 d.setHoras_inicio(rs.getString("horas_inicio"));
	    	 d.setDuracao(rs.getInt("duracao"));
	    	 d.setDia_semana(rs.getString("dia_semana"));
	     }
	        rs.close();
			ps.close();
			c.close();
		return d;
	}

	@Override
	public List<Disciplina> listar() throws SQLException, ClassNotFoundException {
		
		List<Disciplina> disciplinas = new ArrayList<>();	
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, horas_inicio, duracao , dia_semana FROM Disciplina";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		 while (rs.next()) {
			 
			 Disciplina d = new Disciplina();
			 d.setCodigo(rs.getInt("codigo"));
	    	 d.setNome(rs.getString("nome"));
	    	 d.setHoras_inicio(rs.getString("horas_inicio"));
	    	 d.setDuracao(rs.getInt("duracao"));
	    	 d.setDia_semana(rs.getString("dia_semana"));
			 disciplinas.add(d);
		 }
		 rs.close();
		 ps.close();
		 c.close();
		return disciplinas;
	}



	@Override
	public String iudDisciplina(String op, Disciplina d) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "CALL GerenciarDisciplina(?,?,?,?,?,?,?)";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, op);
		cs.setInt(2, d.getCodigo());
		cs.setString(3, d.getNome());
		cs.setString(4, d.getHoras_inicio());
		cs.setInt(5, d.getDuracao()); 
		cs.setString(6, d.getDia_semana()); 
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(7);
		cs.close();
		c.close();
		return saida;
	}

}
	

