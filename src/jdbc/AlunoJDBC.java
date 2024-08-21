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
	
	public void apagar(int id) throws SQLException {
		
		try {
			con = db.getConexao();
			String sql = "DELETE FROM aluno WHERE id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			
			
			
			pst.setInt(1, id);
			
			int alunoExcluido = pst.executeUpdate();
			
			if (alunoExcluido > 0) {
				System.out.println("Aluno excluído com sucesso!");
			} else {
				System.out.println("Não existe aluno com o ID informado!");
			}
			
			
			
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			db.closeConexao();
		}
		
	}
	
	public void alterar(Aluno a) throws SQLException {
		String sql = "UPDATE aluno SET nome = ? , sexo = ?, dt_nasc = ? WHERE id = ?";
		
		try (Connection con = db.getConexao();
				PreparedStatement pst = con.prepareStatement(sql);
				) {
			
			Statement st = con.createStatement();
			
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());
			pst.setDate(3, Date.valueOf(a.getDt_nasc()));
			pst.setInt(4, a.getId());
			
			int linhaAlterada = pst.executeUpdate();
			
			if (linhaAlterada > 0) {
	            System.out.println("Aluno atualizado com sucesso!");
	        } else {
	            System.out.println("Nenhum aluno encontrado com o ID especificado.");
	        }
			
	        st.close();
			
		} catch (Exception e) {
			System.out.println("Erro ao atualizar aluno: " + e.getMessage());
		} finally {
			db.closeConexao();
		}
	}
}
