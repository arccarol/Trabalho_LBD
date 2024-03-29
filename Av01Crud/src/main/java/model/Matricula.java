package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matricula {
	
	Aluno aluno;
	Disciplina disciplina;
	String data;
	
	
	@Override
	public String toString() {
		return "Matricula [aluno=" + aluno + ", disciplina=" + disciplina + ", data=" + data + "]";
	}

	
	
}
