package com.hibernate.gui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hibernate.dao.PersonaDAO;

import com.hibernate.model.Persona;
import javax.swing.JTextField;
import javax.swing.JButton;

public class App {

	private JFrame frame;
	private PersonaDAO personaDAO;
	private DefaultTableModel model;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnCrear;
	private JButton btnBorrar;
	private JButton btnAct;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	
	public void refrescarTabla() {
		List<Persona> personas = null;
		model.setRowCount(0);
		personas = personaDAO.selectAllPersona();
		personas.forEach(s -> {
			Object[] row = new Object[3];
			row[0] = s.getId();
			row[1] = s.getNombre();
			row[2] = s.getEdad();
			model.addRow(row);
		});

	}
	/**
	 * Initialize the contents of the frame.
	 */
	int id=0;
	String nombre="";
	int edad=0;
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		personaDAO = new PersonaDAO();
		
		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int colum) {
				return false;

			}
		};
		model.addColumn("ID");
		model.addColumn("Nombre");
		model.addColumn("Edad");
		
		
		
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel modelEntrenador = table.getModel();
				
				textField.setText(modelEntrenador.getValueAt(index, 0).toString());
				textField_1.setText(modelEntrenador.getValueAt(index, 1).toString());
				textField_2.setText(modelEntrenador.getValueAt(index, 2).toString());
				
			}
		});
		frame.getContentPane().setLayout(null);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(171, 29, 366, 173);
		frame.getContentPane().add(scrollPane);
		
		refrescarTabla();
		textField = new JTextField();
		textField.setBounds(375, 239, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(375, 285, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(375, 335, 130, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		btnCrear = new JButton("Crear");
		btnCrear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				id=Integer.parseInt(textField.getText());
				nombre=textField_1.getText();
				edad=Integer.parseInt(textField_2.getText());
				
				Persona persona = new Persona(nombre,edad);
				personaDAO.insertPersona(persona);
				refrescarTabla();
				
			
				
			}
		});
		btnCrear.setBounds(171, 239, 117, 29);
		frame.getContentPane().add(btnCrear);
		
		btnAct = new JButton("Act");
		btnAct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				id = Integer.parseInt(textField.getText());
				nombre=textField_1.getText();
				edad=Integer.parseInt(textField_2.getText());

				Persona persona = personaDAO.selectPersonaById(id);
				persona.setNombre(nombre);
				persona.setEdad(edad);

				personaDAO.updatePersona(persona);

				refrescarTabla();
				
			}
		});
		btnAct.setBounds(171, 285, 117, 29);
		frame.getContentPane().add(btnAct);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				id = Integer.parseInt(textField.getText());
				personaDAO.deletePersona(id);
				refrescarTabla();
				
			}
		});
		btnBorrar.setBounds(171, 335, 117, 29);
		frame.getContentPane().add(btnBorrar);
	}

}
