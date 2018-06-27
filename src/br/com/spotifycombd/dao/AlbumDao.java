package br.com.spotifycombd.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.spotifycombd.bean.AlbumBean;
import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.connection.ConnectionFactory;
import tableModel.CustomTableModel;

public class AlbumDao {

	private Connection conexao;
	
	private CustomTableModel<AlbumBean> modelo = new CustomTableModel<>();
	
	
	public AlbumDao() {
		
		conexao = new ConnectionFactory().getConnect();
	}
	
	
	public void addAlbum(AlbumBean obj) {
		
		String sql = "insert into album (nomeAlbum) values (?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomeAlbum"));
			
			ps.execute();
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao cadastrar álbum.");
		}
	}

	public void excluirAlbum(int id) {
		
		String sql = "delete from album where idAlbum = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir álbum.");
		}
	}
	
	public void alterarAlbum(AlbumBean obj) {
		
		String sql = "alter table set nomeAlbum = ? where idAlbum = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomeAlbum"));
			ps.setInt(2, obj.get("idAlbum"));
			
			ps.execute();
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao alterar álbum.");
		}
	}
	
	public CustomTableModel<AlbumBean> getModel() {
		
		ArrayList<AlbumBean> albuns = new ArrayList<>();
		
		String sql = "select * from album";
		
		try {
			
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				albuns.add(getAlbum(rs.getInt("idAlbum")));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informações do banco");
		}
		
		modelo.setObjects(albuns);
		
		return modelo;
	}

	public AlbumBean getAlbum(int id) {
		
		AlbumBean album = new AlbumBean();
		
		String sql = "select * from album where idAlbum = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				album.set("idAlbum", rs.getInt("idAlbum"));
				album.set("nomeAlbum", rs.getString("nomeAlbum"));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informações do álbum.");
		}
		
		
		ArrayList<MusicaBean> musicas = new ArrayList<>();
		
		sql = "select * from mngram where idAlbum = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				MusicaDao md = new MusicaDao();
				
				musicas.add(md.getMusica(rs.getInt("idMusica")));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter músicas do álbum.");
		}
		
		album.set("musicas", musicas);
		
		return album;
	}
}