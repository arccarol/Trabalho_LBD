package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Disciplina {
	
	int codigo;
	String nome;
	String horas_inicio;
	int duracao;
	String dia_semana;
	
	@Override
	public String toString() {
		return nome;
	}
	
	
	
	

}
