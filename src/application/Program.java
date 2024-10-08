package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import entities.Aluno;
import jdbc.AlunoJDBC;
import jdbc.db;


public class Program {

	public static void main(String[] args) throws IOException, SQLException {
		
		try {
        	
            int opcao = 0;
            Scanner console = new Scanner(System.in);
            
            do {
            	Connection con = db.getConexao();
            	Aluno a = new Aluno();
        		AlunoJDBC acao = new AlunoJDBC();
        		
            	System.out.println("####### Menu #######"
            						+ "\n1 - Cadastrar"
            						+ "\n2 - Listar"
            						+ "\n3 - Alterar"
            						+ "\n4 - Excluir"
            						+ "\n5 - Sair");
            	System.out.println("\n\tOpção:");
            	opcao = Integer.parseInt(console.nextLine());
            	
            	if (opcao == 1) {
         
            		System.out.println("\n ### Cadastrar Aluno ### \n\r");
            		
            		System.out.print("Nome: ");
            		a.setNome(console.nextLine());
            		
            		System.out.print("Sexo: ");
            		a.setSexo(console.nextLine());
            	
            		System.out.print("Data de Nascimento (dd-mm-aaaa): ");
            		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            		a.setDt_nasc( LocalDate.parse(console.nextLine(), formato) );
            		
            		acao.salvar(a);
            		console.nextLine();
            		System.out.println("\n\n\n\n");
            	}
            	
            	if (opcao == 2) {
    				System.out.println("Lista de Alunos\n");
    				List<Aluno> alunos = acao.listar();
    				System.out.println("Id\t\t\tNome\t\t\tSexo\t\t\tdata_Nasc");
    				for (Aluno a1 : alunos) {
    					System.out.println(a1.getId()   + "\t\t\t" + 
    									   a1.getNome() + "\t\t\t" +
    									   a1.getSexo() + "\t\t" +
    									   a1.getDt_nasc());
    				}
    				console.nextLine();
    				
    				}
            	
            	if (opcao == 3) {
            	    System.out.println("Alterar Aluno");
            	    
            	    System.out.print("Informe o ID do aluno que deseja alterar: ");
            	    int id = Integer.parseInt(console.nextLine());
            	    
            	    Aluno aluno = new Aluno();
            	    aluno.setId(id);
            	    
            	    System.out.print("Novo Nome: ");
            	    aluno.setNome(console.nextLine());
            	    
            	    System.out.print("Novo Sexo: ");
            	    aluno.setSexo(console.nextLine());
            	    
            	    System.out.print("Nova Data de Nascimento (dd-mm-aaaa): ");
            	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	    aluno.setDt_nasc(LocalDate.parse(console.nextLine(), formato));
            	    
            	    acao.alterar(aluno);
            	}
            	
            	if (opcao == 4) {
            		System.out.println("Excluir aluno\n");
            		
            		System.out.println("Digite o ID do aluno que deseja excluir: ");
            		
            		int id = Integer.parseInt(console.nextLine());
            		
            		acao.apagar(id);
            		console.nextLine();
            	}
            	
            } while(opcao != 5);
            
        } catch (Exception e){
            System.out.println("Erro: " + e);
        }
	} 
}
