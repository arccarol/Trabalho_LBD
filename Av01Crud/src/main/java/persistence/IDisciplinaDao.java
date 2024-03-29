package persistence;

import java.sql.SQLException;
import model.Disciplina;

public interface IDisciplinaDao {
	
	public String iudDisciplina(String acao, Disciplina d) throws SQLException, ClassNotFoundException;


}
