package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;
import model.Telefone;
import persistence.AlunoDao;
import persistence.GenericDao;
import persistence.TelefoneDao;

@WebServlet("/telefone")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public TelefoneServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String erro = "";
        List<Aluno> alunos = new ArrayList<>();
        GenericDao gDao = new GenericDao();
        AlunoDao alunoDao = new AlunoDao(gDao);

        try {
            alunos = alunoDao.listar();
           

        } catch (ClassNotFoundException | SQLException e) {
            erro = e.getMessage();

        } finally {
            request.setAttribute("erro", erro);
            request.setAttribute("alunos", alunos);

            RequestDispatcher rd = request.getRequestDispatcher("telefone.jsp");
            rd.forward(request, response);
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// entrada
        String cmd = request.getParameter("botao");;
        String codigo = request.getParameter("codigo");
        String alunoCpf = request.getParameter("aluno");
        String telefone = request.getParameter("telefone");

        // saída
        String saida = "";
        String erro = "";
        Telefone t = new Telefone();

        List<Aluno> alunos = new ArrayList<>();
        List<Telefone> telefones = new ArrayList<>();
        if (!cmd.contains("Listar")) {
        	t.setCodigo(Integer.parseInt(codigo));

        }

        try {
            GenericDao gDao = new GenericDao();
            AlunoDao alunoDao = new AlunoDao(gDao);
     
            alunos = listarAluno();
          
            if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {              
                t.setCodigo(Integer.parseInt(codigo));
                Aluno a = new Aluno();
            	a.setCpf(alunoCpf);  
                t.setTelefone(telefone);
               
                t.setAluno(a);
            
            }

            if (cmd.contains("Cadastrar")) {
            	cadastrarTelefone(t);
            	saida = "Telefone cadastrado com sucesso";
                t = null;
            }
            if (cmd.contains("Alterar")) {
            	alterarTelefone(t);
                saida = "Telefone alterado com sucesso";
                t = null;
            }
            if (cmd.contains("Excluir")) {
                excluirTelefone(t);
                saida = "Telefone excluída com sucesso";
                t = null;
            }
            if (cmd.contains("Buscar")) {
                t = buscarTelefone(t);
            }

            if (cmd.contains("Listar")) {
                telefones = listarTelefone();
                request.setAttribute("tipoTabela", "Listar");
            }

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            request.setAttribute("saida", saida);
            request.setAttribute("erro", erro);
            request.setAttribute("telefones", telefones);
            request.setAttribute("alunos", alunos);

            RequestDispatcher rd = request.getRequestDispatcher("telefone.jsp");
            rd.forward(request, response);
        }

	}


	private void cadastrarTelefone(Telefone t)throws SQLException, ClassNotFoundException {
		    GenericDao gDao = new GenericDao();
	        TelefoneDao tDao = new TelefoneDao(gDao);
	        tDao.inserir(t);
	}


	private void alterarTelefone(Telefone t)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
        TelefoneDao tDao = new TelefoneDao(gDao);
        tDao.excluir(t);
	}


	private void excluirTelefone(Telefone t)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
        TelefoneDao tDao = new TelefoneDao(gDao);
        tDao.excluir(t);;
		
	}


	private Telefone buscarTelefone(Telefone t)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
        TelefoneDao tDao = new TelefoneDao(gDao);
        t = tDao.consultar(t);
      		return t;
	}


	private List<Telefone> listarTelefone()throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
        TelefoneDao tDao = new TelefoneDao(gDao);
        List<Telefone> telefones = tDao.listar();
	    
		 return telefones;
	}
	
	private List<Aluno> listarAluno() throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		AlunoDao alunoDao = new AlunoDao(gDao);
        List<Aluno> alunos = alunoDao.listar();
        return alunos;
	}

}