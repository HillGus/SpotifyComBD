package br.com.spotifycombd.bean;

import java.sql.Time;
import java.util.HashMap;

import br.com.spotifycombd.dao.AlbumDao;
import br.com.spotifycombd.dao.UsuarioDao;
import tableModel.ObjectInfo;

public class MusicaBean implements ObjectInfo {
	
	private HashMap<String, Object> info = new HashMap<>();
	
	public MusicaBean() {}
	
	public MusicaBean(int id, String nome, String genero, Time duracao, int idArtista, int idAlbum) {
		
		//Define as informaçoes no dicionário
		info.put("idMusica", id);
		info.put("nomeMusica", nome);
		info.put("generoMusica", genero);
		info.put("duracaoMusica", duracao);
		info.put("idArtista", idArtista);
		info.put("idAlbum", idAlbum);
		
		//Obtém as informaçoes adicionais do banco de dados
		info.put("nomeArtista", new UsuarioDao().getUser((int) get("idArtista")).get("loginUsuario"));
		info.put("nomeAlbum", new AlbumDao().getNomeAlbum(idAlbum));
	}

	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		
		return (T) info.get(key);
	}
	
	public void set(String key, Object value) {
		
		info.put(key, value);
	}
	
	
	@Override
	public Object[] getInfo() {
		
		//Retorna as informaçoes que aparecerao na tabela
		return new Object[] {get("nomeMusica"), get("generoMusica"), getDuracaoString(), get("nomeAlbum"), get("nomeArtista")};
	}

	@Override
	public Object[] getInfoName() {
		
		//Retorna o título das informaçoes da tabela
		return new Object[] {"Título", "Genero", "Duraçao", "Álbum", "Artista"};
	}
	
	
	private String getDuracaoString() {
		
		//Obtém a duraçao da musica como string
		String duracao = "";
		
		int segundos = ((Time) get("duracaoMusica")).getSeconds();
		
		duracao += ((Time) get("duracaoMusica")).getMinutes();		
		duracao += ":" + (segundos > 9 ? segundos : "0" + segundos);
		
		return duracao;
	}
}
