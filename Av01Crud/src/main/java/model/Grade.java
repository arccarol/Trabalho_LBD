package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grade {
	
	Curso curso;
	Disciplina disciplina;
	
	@Override
	public String toString() {
		return "Grade [curso=" + curso + ", disciplina=" + disciplina + "]";
	}
	
	

}
