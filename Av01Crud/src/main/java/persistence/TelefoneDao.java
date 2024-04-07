package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;
import model.Telefone;

public class TelefoneDao  implements ICrud<Telefone>{
	 private GenericDao gDao;

	    public TelefoneDao(GenericDao gDao) {
	        this.gDao = gDao;
	    }

		@Override
		public void inserir(Telefone t) throws SQLException, ClassNotFoundException {

		    if (telefoneJaExiste(t)) {
		    	throw new SQLException("O número de telefone já existe para este aluno.");
		    } else {

		    Connection c = null;
		    String sql = "INSERT INTO Telefone (codigo, telefone, aluno) VALUES (?, ?, ?)";
		    try {
		        c = gDao.getConnection();
		        PreparedStatement ps = c.prepareStatement(sql);
		        ps.setInt(1, t.getCodigo());
		        ps.setString(2, t.getTelefone());
		        ps.setString(3, t.getAluno().getCpf());
		        ps.executeUpdate();
		    } finally {
		        if (c != null) {
		            c.close();
		        }
		    } 
		}
		}

		private boolean telefoneJaExiste(Telefone t) throws SQLException, ClassNotFoundException {
		    Connection c = null;
		    String sql = "SELECT COUNT(*) AS count FROM Telefone WHERE telefone = ? AND aluno = ?";
		    try {
		        c = gDao.getConnection();
		        PreparedStatement ps = c.prepareStatement(sql);
		        ps.setString(1, t.getTelefone());
		        ps.setString(2, t.getAluno().getCpf());
		        ResultSet rs = ps.executeQuery();
		        if (rs.next()) {
		            int count = rs.getInt("count");
		            return count > 0;
		        }
		    } finally {
		        if (c != null) {
		            c.close();
		        }
		    }
		    return false;
		}
			


		@Override
		public void atualizar(Telefone t) throws SQLException, ClassNotFoundException {
			 Connection c = gDao.getConnection();
			    String sql = "UPDATE Telefone SET telefone = ? WHERE codigo = ?";
			    try (PreparedStatement ps = c.prepareStatement(sql)) {
			         ps.setString(1, t.getTelefone());
			         ps.setInt(2, t.getCodigo());
			         ps.execute();
			    }
			
		}

		@Override
		public void excluir(Telefone t) throws SQLException, ClassNotFoundException {
			Connection c = gDao.getConnection();
		    String sql = "DELETE FROM Telefone WHERE codigo = ?";
		    try (PreparedStatement ps = c.prepareStatement(sql)) {
		        ps.setInt(1, t.getCodigo());
		        ps.executeUpdate();
		    }
			
		}

		@Override
		public Telefone consultar(Telefone t) throws SQLException, ClassNotFoundException {
			Telefone telefone = null;
	        Connection c = null;
	        String sql = "SELECT * FROM Telefone WHERE codigo = ?";
	        try {
	            c = gDao.getConnection();
	            PreparedStatement ps = c.prepareStatement(sql);
	            ps.setInt(1, t.getCodigo());
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                telefone = new Telefone();
	                telefone.setCodigo(rs.getInt("codigo"));
	                telefone.setTelefone(rs.getString("telefone"));
	            }
	        } finally {
	            if (c != null) {
	                c.close();
	            }
	        }
	        return telefone;
		}

		@Override
		public List<Telefone> listar() throws SQLException, ClassNotFoundException {
			List<Telefone> telefones = new ArrayList<>();
		    Connection c = null;
		    String sql = "SELECT t.codigo, t.telefone, a.nome " +
		                 "FROM Telefone t " +
		                 "INNER JOIN Aluno a ON t.aluno = a.cpf";
		    try {
		        c = gDao.getConnection();
		        PreparedStatement ps = c.prepareStatement(sql);
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		            Telefone telefone = new Telefone();
		            telefone.setCodigo(rs.getInt("codigo"));
		            telefone.setTelefone(rs.getString("telefone"));

		            Aluno aluno = new Aluno();
		            aluno.setNome(rs.getString("nome"));

		            telefone.setAluno(aluno);

		            telefones.add(telefone);
		        }
		    } finally {
		        if (c != null) {
		            c.close();
		        }
		    }
		    return telefones;
	    }
}

