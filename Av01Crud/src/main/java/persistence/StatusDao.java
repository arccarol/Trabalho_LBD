package persistence;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Curso;
import model.Status;

public class StatusDao {

private GenericDao gDao;
	

	public StatusDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	public void inserir(Status s) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
	    String sql = "INSERT INTO Status_m (nome) VALUES (?)";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, s.getNome());
	    ps.execute();
	    ps.close();
	    c.close();
		
	}
	
	public Status consultar(Status s) throws SQLException, ClassNotFoundException {
		 Connection c = gDao.getConnection();
		    String sql = "SELECT nome FROM Status_m WHERE nome = ?";
		    PreparedStatement ps = c.prepareStatement(sql);
		    ps.setString(1, s.getNome());
		    ResultSet rs = ps.executeQuery();
		    if (rs.next()) {
		        s.setNome(rs.getString("nome"));
		    }
		    rs.close();
		    ps.close();
		    c.close();
		    return s;
	}




	public void excluir(Status s) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE FROM Status_m WHERE nome = ?";
;
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, s.getNome());
		ps.execute();
		ps.close();
		c.close();
		
	}
	

	public List<Status> listar() throws SQLException, ClassNotFoundException {
		  List<Status> statuss = new ArrayList<>();    
		    Connection cc = gDao.getConnection();
		    String sql = "SELECT nome FROM Status_m"; 
		    PreparedStatement ps = cc.prepareStatement(sql);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        Status s = new Status();
		        s.setNome(rs.getString("nome")); 
		        statuss.add(s);
		    }
		    rs.close();
		    ps.close();
		    cc.close();
		    return statuss;
	}
}