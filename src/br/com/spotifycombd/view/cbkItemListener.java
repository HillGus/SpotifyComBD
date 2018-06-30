package br.com.spotifycombd.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JScrollPane;

public class cbkItemListener implements ItemListener {

	protected JScrollPane scrollMusicas;
	
	public cbkItemListener(JScrollPane scrollMusicas) {
		
		this.scrollMusicas = scrollMusicas;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {}	
}
