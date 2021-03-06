package br.com.spotifycombd.view.componentes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;

public class LblMouseListener implements MouseListener {

	
	public static final int CLICK = 1, ENTER = 2, EXIT = 3, PRESS = 4, RELEASE = 5;
	
	private int acao;
	private Runnable run;
	
	protected JScrollPane scrollMusicas;
	
	public LblMouseListener(int acao, Runnable run) {
		
		this.acao = acao;
		this.run = run;
	}
	
	public LblMouseListener(JScrollPane scrollMusicas) {
		
		this.scrollMusicas = scrollMusicas;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		if (acao == CLICK) {
			
			run.run();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		if (acao == ENTER) {
			
			run.run();
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
		if (acao == EXIT) {
			
			run.run();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		if (acao == PRESS) {
			
			run.run();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		if (acao == RELEASE) {
			
			run.run();
		}
	}

}
