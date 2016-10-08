package com.hispotech.projecthispo;

import java.awt.Button;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;
import org.joda.time.base.AbstractInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Juros extends JFrame {

	private JButton botaoCalcular;

	private JTextField valor;
	private JTextField dia;
	private JTextField mes;
	private JTextField ano;
	private JTextField juros;
	
	private JPanel panel;

	private JLabel label1;
	private JLabel label2;

	private JMenu arquivo;
	private JMenu ajuda;

	private Container container;

	private FlowLayout layout;
	
	private GridLayout gridLayout;

	public Juros() {
		super("Hispo Tech");

		Icon logo = new ImageIcon(getClass().getResource("_tech.png"));
		label1 = new JLabel(logo);
		label1.setPreferredSize(new Dimension(100, 80));
		add(label1, BorderLayout.NORTH);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(6, 6));
				

		// CRIA UMA BARRA DE MENU PARA O JFRAME
		JMenuBar menu = new JMenuBar();

		// ADICIONA A BARRA DE MENU AO FRAME
		setJMenuBar(menu);

		// DEFINE E ADICIONA OS MENUS NA BARRA DE MENU
		JMenu arquivo = new JMenu("Arquivo");
		JMenu ajuda = new JMenu("Ajuda");

		// CRIA E ADICIONA ITENS PARA O MENU
		JMenuItem novo = new JMenuItem("Novo");
		JMenuItem sair = new JMenuItem("Sair");
		JMenuItem sobre = new JMenuItem("Sobre");
		JMenuItem comoFunciona = new JMenuItem("Como Funciona?");

		// ADICIONA OS MENUS
		menu.add(arquivo);
		menu.add(ajuda);

		// CRIA E ADICIONA UM CHECKBUTTON
		arquivo.add(novo);
		arquivo.add(sair);
		ajuda.add(sobre);
		ajuda.add(comoFunciona);

		container = this.getContentPane();
		//container.setLayout(new FlowLayout());
		//layout.setAlignment(FlowLayout.CENTER);

		// CRIA BOTÃO
		botaoCalcular = new JButton("Calcular");

		// CRIA ESPAÇOS PARA DIGITAR VALORES
		dia = new JTextField(10);
		mes = new JTextField(10);
		ano = new JTextField(10);
		valor = new JTextField(10);
		juros = new JTextField(10);

		// ADICIONA INFORMAÇÂO DOS ESPAÇOS
		panel.add(new JLabel("Valor a ser pago"));
		panel.add(valor);

		panel.add(new JLabel("Dia"));
		panel.add(dia);
		panel.add(new JLabel("Mês"));
		panel.add(mes);
		panel.add(new JLabel("Ano"));
		panel.add(ano);

		panel.add(new JLabel("Juros"));
		panel.add(juros);

		// CONFIGURA ÍCONE DO BOTÃO
		Icon imPercent = new ImageIcon(getClass().getResource("percent.png"));
		botaoCalcular = new JButton("Calcular", imPercent);
		add(botaoCalcular, BorderLayout.SOUTH);
		
		add(panel, BorderLayout.WEST);
		
		// DEFINE COR DA TELA
		container.setBackground(Color.ORANGE);

		botaoCalcular.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				botaoCalcularAction();
			}
		});

		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sairAction();
			}
		});

		sobre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sobreAction();
			}
		});

		novo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				novoAction();
			}
		});

		comoFunciona.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comoFuncionaAction();
			}
		});

	} // FIM DO CONSTRUTOR

	private void novoAction() {
		limpar();
	}

	/**
	 * Método que limpa todos os dados inseridos e reinicia programa.
	 */
	private void limpar() {
		valor.setText("");
		dia.setText("");
		mes.setText("");
		ano.setText("");
		juros.setText("");
	}

	/**
	 * Método que efetua saída do programa.
	 */
	private void sairAction() {
		System.exit(0);
	}

	private void sobreAction() {
		JOptionPane.showMessageDialog(null,
				"Criado por Edson F.\n31-07-2016\nVersão 2.0", "Sobre",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void comoFuncionaAction() {
		JOptionPane
				.showMessageDialog(
						null,
						"O programa calcula os juros baseado nos dias de atraso.\n"
								+ "Ao digitar o valor em reais deve-se usar o ponto \".\" e não a vírgula \",\" !\n"
								+ "Exemplo: 130.35\nO mesmo se aplica ao digitar o valor do juros.\n"
								+ "Exemplo: 0.04", "Como Funciona",
						+JOptionPane.INFORMATION_MESSAGE);
	}

	private void botaoCalcularAction() {

		if (isCamposConsistentes()) {

			// CONVERTE JTextField EM UM INTEIRO
			int day = Integer.parseInt(dia.getText());
			int month = Integer.parseInt(mes.getText());
			int year = Integer.parseInt(ano.getText());
			double valorJuros = Double.parseDouble(juros.getText());
			double valorPagar = Double.parseDouble(valor.getText());

			DateTime hoje = new DateTime(); // DATA ATUAL, HOJE
			DateTime dataVencimento = new DateTime(year, month, day, 0, 0); // ENTRADA
																			// DO
																			// USUÁRIO

			// CALCULA DIFERENÇA DE DIAS
			Days diferenca = Days.daysBetween(dataVencimento, hoje);

			int diasAtraso = diferenca.getDays();

			// VARIÁVEIS DE JUROS E TOTAL
			valorJuros /= 100;
			valorJuros = diasAtraso * valorJuros * valorPagar;
			double totalPagar = valorJuros + valorPagar;

			// STRING NECESSÁRIA PARA SAÍDA COM DUAS CASAS DECIMAIS APÓS VÍRGULA
			String resultado = String
					.format("Valor: %.2f\nDias de atraso: %d\nJuros de R$: %.2f\nTotal a pagar: %.2f",
							valorPagar, diasAtraso, valorJuros, totalPagar);

			// IMPRIME RESULTADO
			JOptionPane.showMessageDialog(null, resultado, "Resultados",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Campo(s) inválido(s)!");
		}

	} // FIM DO MÉTODO botaoCaclularAction

	private boolean isCamposConsistentes() {
		String day = dia.getText().trim();
		String month = mes.getText().trim();
		String year = ano.getText().trim();
		String valorJuros = juros.getText().trim();
		String valorPagar = valor.getText().trim();

		if (day.equals("")) {
			dia.requestFocus();
			return false;
		} else if (month.equals("")) {
			mes.requestFocus();
			return false;
		} else if (year.equals("")) {
			ano.requestFocus();
			return false;
		} else if (valorJuros.equals("")) {
			ano.requestFocus();
			return false;
		} else if (valorPagar.equals("")) {
			ano.requestFocus();
			return false;
		}
		try {
			Integer.parseInt(day);
		} catch (Exception e) {
			dia.requestFocus();
			return false;
		}

		try {
			Integer.parseInt(month);
		} catch (Exception e) {
			mes.requestFocus();
			return false;
		}

		try {
			Integer.parseInt(year);
		} catch (Exception e) {
			ano.requestFocus();
			return false;
		}

		try {
			Double.parseDouble(valorJuros);
		} catch (Exception e) {
			juros.requestFocus();
			return false;
		}

		try {
			Double.parseDouble(valorPagar);
		} catch (Exception e) {
			valor.requestFocus();
			return false;
		}

		return true;
	}
} // FIM DA CLASSE _JUROS