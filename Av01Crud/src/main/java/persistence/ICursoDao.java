package persistence;

import model.Curso;
import java.sql.SQLException;

public interface ICursoDao {
	
	public String iudCurso(String acao, Curso c) throws SQLException, ClassNotFoundException;

}
