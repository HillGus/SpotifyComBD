package br.com.spotifycombd.bean;

import java.sql.Time;
import java.util.HashMap;

import tableModel.ObjectInfo;

public class MusicaBean implements ObjectInfo {
	
	private HashMap<String, Object> info = new HashMap<>();
	
	public MusicaBean() {}
	
	public MusicaBean(int id, String nome, String genero, Time duracao, int idArtista) {
		
		info.put("idMusica", id);
		info.put("nomeMusica", nome);
		info.put("generoMusica", genero);
		info.put("duracaoMusica", duracao);
		info.put("idArtista", idArtista);
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
		
		return new Object[] {get("idMusica"), get("nomeMusica"), get("generoMusica"), getDuracaoString(), get("idArtista")};
	}

	@Override
	public Object[] getInfoName() {
		
		return new Object[] {"C�digo", "Nome", "G�nero", "Dura��o", "Artista"};
	}
	
	
	private String getDuracaoString() {
		
		String duracao = "";
		
		duracao += ((Time) get("duracaoMusica")).getHours();
		duracao += ":" + ((Time) get("duracaoMusica")).getMinutes();
		
		return duracao;
	}
}