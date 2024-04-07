package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matricula {
	
	int codigo;
	Aluno aluno;
	Disciplina disciplina;
	String data;
	Status status;
	
	
	@Override
	public String toString() {
		return "Matricula [codigo=" + codigo + ", aluno=" + aluno + ", disciplina=" + disciplina + ", data=" + data
				+ ", status=" + status + "]";
	}
	
	
	
	
	
	
	

	
	
}
