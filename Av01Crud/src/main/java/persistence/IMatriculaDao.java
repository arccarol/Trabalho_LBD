package persistence;

import java.sql.SQLException;
import model.Matricula;

public interface IMatriculaDao {
	
	public String iudMatricula(String acao, Matricula m) throws SQLException, ClassNotFoundException;


}