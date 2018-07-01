package br.com.spotifycombd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.spotifycombd.bean.MusicaBean;
import br.com.spotifycombd.connection.ConnectionFactory;
import tableModel.CustomTableModel;

public class MusicaDao {

	private Connection conexao;
	
	private static CustomTableModel<MusicaBean> modelo = new CustomTableModel<>();
	private static CustomTableModel<MusicaBean> modeloArtista = new CustomTableModel<>();
	private static CustomTableModel<MusicaBean> modeloResumido = new CustomTableModel<>();
	
	
	public MusicaDao() {
		
		conexao = new ConnectionFactory().getConnect();
	}
	
	
	public void addMusica(MusicaBean obj) {
		
		String sql = "insert into musica (nomeMusica, generoMusica, duracaoMusica, idArtista, idAlbum) values (?, ?, ?, ?, ?)";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomeMusica"));
			ps.setString(2, obj.get("generoMusica"));
			ps.setTime(3, obj.get("duracaoMusica"));
			ps.setInt(4, obj.get("idArtista"));
			ps.setInt(5, obj.get("idAlbum"));
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao cadastrar música.");
		}
	}
		
	public void excluirMusica(int id) {
		
		String sql = "delete from musica where idMusica = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir música.");
		}
	}
	
	public void excluirMusicaByAlbum(int id) {
		
		ArrayList<MusicaBean> musicas = new AlbumDao().getMusicModel(id).getObjects();
		
		String sql = "delete from mngrplm where idMusica = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			//Remove todas as músicas de um determinado álbum das playlists
			for (MusicaBean musica : musicas) {
				
				ps.setInt(1, musica.get("idMusica"));
				
				ps.execute();
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao remover músicas das playlists.");
			e.printStackTrace();
		}
		
		//Remove todas as músicas do álbum
		sql = "delete from musica where idAlbum = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Erro ao excluir músicas.");
			e.printStackTrace();
		}
	}
	
	public void alterarMusica(MusicaBean obj) {
		
		String sql = "update musica set nomeMusica = ?, generoMusica = ?, duracaoMusica = ?, idArtista = ? where idMusica = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, obj.get("nomeMusica"));
			ps.setString(2, obj.get("generoMusica"));
			ps.setTime(3, obj.get("duracaoMusica"));
			ps.setInt(4, obj.get("idArtista"));
			ps.setInt(5, obj.get("idMusica"));
			
			ps.execute();
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao alterar música.");
		}
	}
	
	public CustomTableModel<MusicaBean> getModel() {

		modelo.setObjects(getAllMusica());
		
		return modelo;
	}
	
	public CustomTableModel<MusicaBean> getModel(int idArtista) {
		
		modeloArtista.setObjects(getAllMusica(idArtista));
		
		return modeloArtista;
	}
	
	public CustomTableModel<MusicaBean> getResumedModel() {
		
		modeloResumido.setObjects(getAllResumedMusica());
		
		return modeloResumido;
	}
	
	public MusicaBean getMusica(int id) {
		
		MusicaBean musica = new MusicaBean();
		
		String sql = "select * from musica where idMusica = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				musica.set("idMusica", id);
				musica.set("nomeMusica", rs.getString("nomeMusica"));
				musica.set("generoMusica", rs.getString("generoMusica"));
				musica.set("duracaoMusica", rs.getTime("duracaoMusica"));
				musica.set("idArtista", rs.getInt("idArtista"));
				musica.set("nomeArtista", new UsuarioDao().getUser(rs.getInt("idUsuario")).get("loginUsuario"));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter informaçooes da m�sica.");
		}
		
		return musica;
	}
	
	public ArrayList<MusicaBean> getAllMusica() {
		
		ArrayList<MusicaBean> musicas = new ArrayList<>();
		
		String sql = "select * from musica";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				musicas.add(new MusicaBean(
						
							rs.getInt("idMusica"),
							rs.getString("nomeMusica"),
							rs.getString("generoMusica"),
							rs.getTime("duracaoMusica"),
							rs.getInt("idArtista"),
							rs.getInt("idAlbum")
						));
			}
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter músicas.");
			e.printStackTrace();
		}
		
		return musicas;
	}
	
	//Retorna todas as músicas de um determinado artista
	public ArrayList<MusicaBean> getAllMusica(int idUsuario) {
		
		ArrayList<MusicaBean> musicas = new ArrayList<>();
		
		String sql = "select * from musica where idArtista = ?";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, idUsuario);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				musicas.add(new MusicaBean(
						
							rs.getInt("idMusica"),
							rs.getString("nomeMusica"),
							rs.getString("generoMusica"),
							rs.getTime("duracaoMusica"),
							rs.getInt("idArtista"),
							rs.getInt("idAlbum")
						));
			}
			
			ps.close();
			
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter músicas.");
			e.printStackTrace();
		}
		
		return musicas;
	}
	
	//Retorna uma música, mas com os métodos usados pelo CustomTableModel sobrescritos para aparecem menos informaçoes na tabela
	public MusicaBean getResumedMusica(int id) {
		
		MusicaBean musica = new MusicaBean() {
			
			public Object[] getInfo() {
				
				return new Object[] {get("nomeMusica"), get("nomeArtista")};
			}
			
			public Object[] getInfoName() {
				
				return new Object[] {"Título", "Artista"};
			}
		};
		
		MusicaBean m = getMusica(id);
		
		musica.set("idMusica", m.get("idMusica"));
		musica.set("nomeMusica", m.get("nomeMusica"));
		musica.set("generoMusica", m.get("generoMusica"));
		musica.set("duracaoMusica", m.get("duracaoMusica"));
		musica.set("idArtista", m.get("idArtista"));
		musica.set("nomeArtista", m.get("nomeArtista"));
		
		return musica;
	}
	
	public ArrayList<MusicaBean> getAllResumedMusica() {
		
		ArrayList<MusicaBean> musicas = new ArrayList<>();
		
		String sql = "select * from musica";
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				musicas.add(new MusicaBean(
						
							rs.getInt("idMusica"),
							rs.getString("nomeMusica"),
							rs.getString("generoMusica"),
							rs.getTime("duracaoMusica"),
							rs.getInt("idArtista"),
							rs.getInt("idAlbum")
						) {
					
					@Override
					public Object[] getInfo() {
						
						return new Object[] {get("nomeMusica"), get("nomeArtista")};
					}
					
					@Override
					public Object[] getInfoName() {
						
						return new Object[] {"Título", "Artista"};
					}
				});
			}
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter músicas.");
			e.printStackTrace();
		}
		
		return musicas;
	}
	
	//Obtém todas as músicas de um determinado álbum ou playlist
	public ArrayList<MusicaBean> getMusicasBy(String tipo, int id) {
		
		ArrayList<MusicaBean> musicas = new ArrayList<>();
		
		String sql = "select musica.* from musica";
		
		switch (tipo.toLowerCase()) {
		
			case "playlist": sql += ", mngrplm where mngrplm.idPlaylist = ? and musica.idMusica = mngrplm.idMusica";break;
			case "album": sql += " where idAlbum = ?";
		}
		
		try {
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				musicas.add(new MusicaBean(
						
							rs.getInt("idMusica"),
							rs.getString("nomeMusica"),
							rs.getString("generoMusica"),
							rs.getTime("duracaoMusica"),
							rs.getInt("idArtista"),
							rs.getInt("idAlbum")
						));
			}
		} catch (SQLException e) {
			
			System.out.println("Erro ao obter músicas.");
			e.printStackTrace();
		}
		
		return musicas;
	}
}