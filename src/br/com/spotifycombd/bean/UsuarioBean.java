package br.com.spotifycombd.bean;

import java.util.HashMap;

public class UsuarioBean {
	
	private HashMap<String, Object> info = new HashMap<>();
	
	
	public UsuarioBean() {}
	
	public UsuarioBean(int id, String login, String senha, boolean artista) {
		
		info.put("idUsuario", id);
		info.put("loginUsuario", login);
		info.put("senhaUsuario", senha);
		info.put("artista", artista);
	}
	
	public UsuarioBean(String login, String senha, boolean artista) {
		
		this(0, login, senha, artista);
	}
	
	
	public void set(String key, Object value) {
		
		info.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		
		return (T) info.get(key);
	}
}