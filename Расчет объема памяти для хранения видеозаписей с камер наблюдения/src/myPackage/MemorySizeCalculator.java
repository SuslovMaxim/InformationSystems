package myPackage;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ������ ������ ������ ��� �������� ������������ � ����� ����������.
 * @author Max Suslov
 * @version 1.0
 */
public class MemorySizeCalculator {
	
	public static void main(String[] args) {
		MemorySizeCalculator calc = new MemorySizeCalculator();
	}
	
	//	���������
	/** ��������� �� ������. ����� "��������� ��������� ������!" �������� �����. */
	public static final String ERROR_MESSAGE = "<html><span style='color:red'>��������� ��������� ������!</span></html>"; 
	
	// ��������� � �������������� ���������� ����.
	private JFrame frame = new JFrame("������ ������ ������ ��� �������� ������������ � ����� ����������");
	private JPanel windowContent = new JPanel();
	private JLabel label1 = new JLabel("������ ������ ����� (�����):");
	private JTextField field1 = new JTextField("10");
	private JLabel label2 = new JLabel("�������� ������ (����/���):");
	private JTextField field2 = new JTextField("25");
	private JLabel label3 = new JLabel("���������� �����:");
	private JTextField field3 = new JTextField("1");
	private JLabel label4 = new JLabel("���������� �������� �����:");
	private JTextField field4 = new JTextField("24");
	private JButton batton = new JButton("����������");
	private JLabel results = new JLabel("", 0);
	
	/**
	 * ����������� ��� ����������. ������� �� ����� ���� � ������, ������������ �� ���������:
	 * ������ ������ ����� (�����): 10
	 * �������� ������ (����/���): 25
	 * ���������� �����: 1
	 * ���������� �������� �����: 24 
	 */
	public MemorySizeCalculator() {
		// ��������� ������ ��������� ��� �������� ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ������ ������������ GridLayout ��� ����������� ������.
		// ��� ����� ������������� � ������� �������� 5 �� 2.
		// ���������� ����� �������� ������� - 5 �������� �� ����������� � 5 �� ���������.
		GridLayout gl = new GridLayout(5,2,5,5);
		windowContent.setLayout(gl);

		// ��������� ���������� �� ������.
		windowContent.add(label1);
		windowContent.add(field1);
		windowContent.add(label2);
		windowContent.add(field2);
		windowContent.add(label3);
		windowContent.add(field3);
		windowContent.add(label4);
		windowContent.add(field4);
		windowContent.add(batton);
		windowContent.add(results);	
		
		// ������� "���������". ��� ������ ���������� ������ Listener, ������� ����� "�������". 
		// ������� "������� �� ������" � �������� ����������.
		batton.addActionListener(new Listener());
				
		// ��������� ����� � ������.	
		frame.setContentPane(windowContent);
		
		// ����� ������ � ���������� �����.
		frame.setSize(450,150);
		frame.setVisible(true);
	}
	
	/**
	 * ����������� � �����������. ��������� ������ �������� ����� �� ���������.
	 */
	public MemorySizeCalculator(String frameSize, String recordSpeed, String camerasAmount, String hourAmount) {
		// �������� ������ �����������, ������� ��� ����������.
		this();	

		// ��������� ���� �� �����.
		field1.setText(frameSize);
		field2.setText(recordSpeed);
		field3.setText(camerasAmount);
		field4.setText(hourAmount);		
	}
	
	/** ��������� ����� ��� ��������� ������� "������� �� ������". */
	private class Listener implements ActionListener {
		@Override
		// ����� ��������� ��������� ActionListener, ������� �� ������ ����������� ����� actionPerformed.
		// JVM ��������� ���� �����, ����� ������������ �������� �� ������.
		public void actionPerformed(ActionEvent e) {

			// �������� ������ c ����� ����� � ����� getProfit ��� ����������.
			String result = calculate(field1.getText(), field2.getText(), field3.getText(), field4.getText());
			
			// ������� �� ����� ���������� ����������.
			results.setText(result);
		}	
	}
	
	/**
	 * ����� ����������� ����� ������ ��� �������� ������������ � ����� ����������.
	 * ����� ��������� 4 ������, ������������ ������ � ����� �����, ��������� ��������� �� �������.
	 * �������: ����� ������ = ������ ����� * ���������� ������ � ������� * ���������� ����� * ���������� ����� * 3600 / 1024 / 1024.	
	 * @return ������ � ������� ������ � ����������. ���������� ����������� �� ���� ������ ����� �������.
	 * @return ���� ���������� ������ ������ �����������, �� ������������ ������ ERROR_MESSAGE.
	 * @see ERROR_MESSAGE
	 */
	public static String calculate(String frameSize, String recordSpeed, String camerasAmount, String hourAmount) {
		float frameSizeFloat;
		float recordSpeedFloat;
		int camerasAmountInt;
		float hourAmountFloat;
			
		// ������� ������������� ������ � int.
		// � ������ ������ ����� ���������� ������ ERROR_MESSAGE.
		try {
			frameSizeFloat = Float.parseFloat(frameSize);
			recordSpeedFloat = Float.parseFloat(recordSpeed);
			camerasAmountInt = Integer.parseInt(camerasAmount);
			hourAmountFloat = Float.parseFloat(hourAmount);
		} catch(NumberFormatException e) {
			return ERROR_MESSAGE;
		}
		
		//	��� ����� ������ ���� ����������������
		if ((frameSizeFloat < 0) | (recordSpeedFloat < 0) | (camerasAmountInt < 0) | (hourAmountFloat < 0)) 
			return ERROR_MESSAGE;
		
		// ��������� �� �������
		// ������� ��������� �����, ���������� �� 100, � ������� Math.round(), � ����� ����� ����� �� 100.
		// ����� ������� �������� ���������� �� ���� ������ ���� �������. 
		float resultFloat = Math.round(frameSizeFloat * recordSpeedFloat * camerasAmountInt * hourAmountFloat * 3600 / 1024 / 1024 * 100f) / 100f;
				
		// ��������� ����������� � ������������ � ������.
		return String.format("%.2f ��", resultFloat);
	}
}
