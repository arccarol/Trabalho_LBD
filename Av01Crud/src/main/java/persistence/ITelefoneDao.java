package persistence;

import java.sql.SQLException;
import model.Telefone;

public interface ITelefoneDao {
	
	public String iudTelefone(String acao, Telefone t) throws SQLException, ClassNotFoundException;


}