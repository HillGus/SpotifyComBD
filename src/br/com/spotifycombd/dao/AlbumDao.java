package br.com.spotifycombd.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;

import br.com.spotifycombd.bean.AlbumBean;
import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.connection.ConnectionFactory;
import tableModel.CustomTableModel;

public class AlbumDao {

	private Connection conexao;
	
	private static JComboBox<AlbumBean> cbkAlbuns = new JComboBox<>();
	private static CustomTableModel<MusicaBean> musicModel = new CustomTableModel<>();
	
	
 	public AlbumDao() {
		
		conexao = new ConnectionFactory().getConnect();
	}
	
	
	public void addAlbum(AlbumBean obj) {
		
		String sql = "insert into album (nomeAlbum, idUsuario) values (?, ?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomeAlbum"));
			ps.setInt(2, obj.get("idUsuario"));
			
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
			
			new MusicaDao().excluirMusicaByAlbum(id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir álbum.");
			e.printStackTrace();
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

	//Retorna uma comboBox com os objetos dos albuns
	public JComboBox<AlbumBean> getCombo() {
		
		cbkAlbuns.removeAllItems();
		
		String sql = "select * from album";
		
		try {
			
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			//Adiciona os albuns na comboBox
			while (rs.next()) {
				
				AlbumBean ab = new AlbumBean(
							rs.getInt("idAlbum"), 
							rs.getString("nomeAlbum"), 
							rs.getInt("idUsuario"));
				
				
				
				cbkAlbuns.addItem(ab);
			}
			
			st.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter álbuns.");
			e.printStackTrace();
		}
		
		return cbkAlbuns;
	}
	
	public AlbumBean getAlbum(int id) {
		
		AlbumBean album = null;
		
		String sql = "select * from album where idAlbum = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				album = new AlbumBean(
						rs.getInt("idAlbum"), 
						rs.getString("nomeAlbum"),
						rs.getInt("idUsuario"));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informaçoes do álbum.");
		}
		
		return album;
	}

	public AlbumBean getLastAlbum() {
		
		AlbumBean ab = null;
		
		String sql = "select * from album order by idAlbum desc limit 1";
		
		try {
			
			Statement st = conexao.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				
				ab = new AlbumBean(
						rs.getInt("idAlbum"), 
						rs.getString("nomeUsuario"), 
						rs.getInt("idUsuario"));
			}
			
			st.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter álbum.");
			e.printStackTrace();
		}
		
		return ab;
	}

	public String getNomeAlbum(int id) {
		
		String nome = "";
		
		String sql = "select nomeAlbum from album where idAlbum = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				return rs.getString("nomeAlbum");
			}
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter nome do álbum.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Retorna o modelo com as músicas do álbum
	public CustomTableModel<MusicaBean> getMusicModel(int id) {
		
		ArrayList<MusicaBean> musicas = new MusicaDao().getMusicasBy("album", id);
		
		musicModel.setObjects(musicas);
		
		return musicModel;
	}
}