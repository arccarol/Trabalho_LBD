package persistence;

import model.Aluno;
import java.sql.SQLException;

public interface IAlunoDao {
	
	public String iudAluno(String acao, Aluno a) throws SQLException, ClassNotFoundException;

}