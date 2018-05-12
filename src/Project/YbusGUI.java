package Project;

//This class implements the last part of the program's GUI that presents as output the values of the Ybus matrix. Basically, 
//the object that is created takes as attributes the column number and the requested table to present. It creates a frame 
//of specific dimensions and presents all rows and columns of the given matrix

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class YbusGUI extends JFrame {

	JTable table;
	public YbusGUI(String[][] newTable, String[] columns){
		
		TableColumn column = null;
		
		//Creates the layout(size and position) of the frame that the matrxi will be presented
		setLayout(new FlowLayout());
		
		//Takes the requested tables and presents them in GUI
		JTable table = new JTable(newTable,columns);
		table.setPreferredScrollableViewportSize(new Dimension(500,80));
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(scrollPane);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("Calibri",Font.PLAIN, 14));
		
		//Set width of column
		for (int i = 0;i< newTable.length;i++) {
	        column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth(100); 
	    }  
		
	}
}
