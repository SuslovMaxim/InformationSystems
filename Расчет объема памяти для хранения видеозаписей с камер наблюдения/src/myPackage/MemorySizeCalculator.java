package myPackage;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Расчет объема памяти для хранения видеозаписей с камер наблюдения.
 * @author Max Suslov
 * @version 1.0
 */
public class MemorySizeCalculator {
	
	public static void main(String[] args) {
		MemorySizeCalculator calc = new MemorySizeCalculator();
	}
	
	//	Константы
	/** Сообщение об ошибке. Текст "Проверьте введенные данные!" красного цвета. */
	public static final String ERROR_MESSAGE = "<html><span style='color:red'>Проверьте введенные данные!</span></html>"; 
	
	// Объявляем и инициализируем компоненты окна.
	private JFrame frame = new JFrame("Расчет объема памяти для хранения видеозаписей с камер наблюдения");
	private JPanel windowContent = new JPanel();
	private JLabel label1 = new JLabel("Размер одного кадра (Кбайт):");
	private JTextField field1 = new JTextField("10");
	private JLabel label2 = new JLabel("Скорость записи (кадр/сек):");
	private JTextField field2 = new JTextField("25");
	private JLabel label3 = new JLabel("Количество камер:");
	private JTextField field3 = new JTextField("1");
	private JLabel label4 = new JLabel("Количество архивных часов:");
	private JTextField field4 = new JTextField("24");
	private JButton batton = new JButton("Рассчитать");
	private JLabel results = new JLabel("", 0);
	
	/**
	 * Конструктор без аргументов. Выводит на экран окно с полями, заполненными по умолчанию:
	 * Размер одного кадра (Кбайт): 10
	 * Скорость записи (кадр/сек): 25
	 * Количество камер: 1
	 * Количество архивных часов: 24 
	 */
	public MemorySizeCalculator() {
		// Завершить работу программы при закрытии окна
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Задаем расположения GridLayout для компонентов панели.
		// Они будут располагаться в таблице размером 5 на 2.
		// Расстояние между ячейками таблицы - 5 пикселей по горизонтали и 5 по вертикали.
		GridLayout gl = new GridLayout(5,2,5,5);
		windowContent.setLayout(gl);

		// Добавляем компоненты на панель.
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
		
		// Создаем "слушатель". Это объект вложенного класса Listener, который будет "слушать". 
		// событие "Нажатие на кнопку" и вызывать вычисления.
		batton.addActionListener(new Listener());
				
		// Связываем фрейм и панель.	
		frame.setContentPane(windowContent);
		
		// Задаём размер и отображаем фрейм.
		frame.setSize(450,150);
		frame.setVisible(true);
	}
	
	/**
	 * Конструктор с аргументами. Позволяет задать значения полей по умолчанию.
	 */
	public MemorySizeCalculator(String frameSize, String recordSpeed, String camerasAmount, String hourAmount) {
		// Вызываем другой конструктор, который без аргументов.
		this();	

		// Заполняем поля на форме.
		field1.setText(frameSize);
		field2.setText(recordSpeed);
		field3.setText(camerasAmount);
		field4.setText(hourAmount);		
	}
	
	/** Вложенный класс для обработки событий "Нажатие на кнопку". */
	private class Listener implements ActionListener {
		@Override
		// Класс реализует интерфейс ActionListener, поэтому мы должны реализовать метод actionPerformed.
		// JVM выполняет этот метод, когда пользователь нажимает на кнопку.
		public void actionPerformed(ActionEvent e) {

			// Передаем данные c полей формы в метод getProfit для вычислений.
			String result = calculate(field1.getText(), field2.getText(), field3.getText(), field4.getText());
			
			// Выводим на форму результаты вычислений.
			results.setText(result);
		}	
	}
	
	/**
	 * Метод расчитывает объем памяти для хранения видеозаписей с камер наблюдения.
	 * Метод принимает 4 строки, конвертирует строки в целые числа, вычисляет результат по формуле.
	 * Формула: Объем памяти = Размер кадра * Количество кадров в секунде * Количество камер * Количество часов * 3600 / 1024 / 1024.	
	 * @return Строка с объемом памяти в гигабайтах. Количество округляется до двух знаком после запятой.
	 * @return Если переданные методу данные некорректны, то возвращается строка ERROR_MESSAGE.
	 * @see ERROR_MESSAGE
	 */
	public static String calculate(String frameSize, String recordSpeed, String camerasAmount, String hourAmount) {
		float frameSizeFloat;
		float recordSpeedFloat;
		int camerasAmountInt;
		float hourAmountFloat;
			
		// Пробуем преобразовать строки в int.
		// В случае ошибки метод возвращает строку ERROR_MESSAGE.
		try {
			frameSizeFloat = Float.parseFloat(frameSize);
			recordSpeedFloat = Float.parseFloat(recordSpeed);
			camerasAmountInt = Integer.parseInt(camerasAmount);
			hourAmountFloat = Float.parseFloat(hourAmount);
		} catch(NumberFormatException e) {
			return ERROR_MESSAGE;
		}
		
		//	Все числа должны быть неотрицательными
		if ((frameSizeFloat < 0) | (recordSpeedFloat < 0) | (camerasAmountInt < 0) | (hourAmountFloat < 0)) 
			return ERROR_MESSAGE;
		
		// Вычисляем по формуле
		// Сначала округляем число, умноженное на 100, с помощью Math.round(), а потом снова делим на 100.
		// Таким образом получаем округление до двух знаков поле запятой. 
		float resultFloat = Math.round(frameSizeFloat * recordSpeedFloat * camerasAmountInt * hourAmountFloat * 3600 / 1024 / 1024 * 100f) / 100f;
				
		// Результат форматируем и конвертируем в строку.
		return String.format("%.2f Гб", resultFloat);
	}
}
