package persistence;

import java.sql.SQLException;
import model.Grade;

public interface IGradeDao {
	
	public String iudGrade(String acao, Grade d) throws SQLException, ClassNotFoundException;


}