package persistence;

import java.sql.SQLException;
import model.Grade;

public interface IMatriculaDao {
	
	public String iudMatricula(String acao, Grade d) throws SQLException, ClassNotFoundException;


}