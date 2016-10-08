package com.hispotech.projecthispo;

import javax.swing.JFrame;

public class JurosTest {
	public static void main(String[] args) {
		Juros juros = new Juros();
		juros.setSize(370, 350);
		juros.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juros.setVisible(true);

		juros.setAutoRequestFocus(true); // IMPEDE DE USAR FRAME
										 // PRINCIPAL QUANDO O SECUNDÁRIO
										 // ESTIVER ABERTO

		juros.setLocationRelativeTo(null); // CONFIGURA PARA APARECER
										   // NO CENTRO DA TELA
		
		juros.setResizable(false); // TAMANHO DA TELA INALTERÁVEL
	}
}