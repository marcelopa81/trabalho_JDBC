package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {

	Connection con;
	String sql;
	PreparedStatement pst;
	
	public void salvar(Aluno a) throws SQLException {
		
		try {
			con = db.getConexao();
			sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?,?, ?)";
			pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());
			Date dataSql = Date.valueOf( a.getDt_nasc() );
			pst.setDate(3, dataSql);
			pst.executeUpdate();
			System.out.println("\nCadastro do aluno realizado com sucesso!");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			pst.close();
			db.closeConexao();
		}
	}
	
	public List<Aluno> listar() throws SQLException{
		
		ArrayList<Aluno> list = new ArrayList<Aluno>();
		
		try {
			con = db.getConexao();
			Statement st = con.createStatement();
			String sql = "SELECT * FROM aluno";
			
			 ResultSet rs = st.executeQuery(sql);
		        
		        while (rs.next()) {
		            Aluno aluno = new Aluno();
		            aluno.setId(rs.getInt("id"));
		            aluno.setNome(rs.getString("nome"));
		            aluno.setSexo(rs.getString("sexo"));
		            aluno.setDt_nasc(rs.getDate("dt_nasc").toLocalDate());
		            list.add(aluno);
		        }
		        
		        rs.close();
		        st.close();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			db.closeConexao();
		}
		return list;
	}
	
	public void apagar(int id) {
		
	}
	
	public void alterar(Aluno a) {
		
	}
}
