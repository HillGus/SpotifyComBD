package br.com.spotifycombd.view;

import java.awt.Panel;
import java.util.HashMap;

import javax.swing.JComponent;

public class HPanel extends Panel {

	
	private HashMap<String, JComponent> componentes = new HashMap<>();
	
	
	public HPanel() {
		
		setLayout(null);
	}
	
	
	public void add(JComponent comp, String key) {
		
		add(comp);
		
		componentes.put(key, comp);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		
		return (T) componentes.get(key);
	}
}
