package Project;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;



import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Project.CMPLX;
import Project.CalculateYBus;
import Project.DataBaseClass;
import Project.DatabaseUserChecker;
import Project.YbusGUI;
import Project.ReadEQfile;
import Project.ReadSSHfile;
import java.awt.Color;


@SuppressWarnings("serial")
public class Project1 extends JPanel implements ActionListener {
//The following variables are used to build user GUI interface:
	protected JButton b1, b2, b3, b4;
	protected JLabel 	lblEqFileHas , lblSshFileHas , lblLoginToDatabase , lblProgramHasBeen , lblEhProject , lblDevelopedByAmar , lblThisProgramWill ;
	protected JTable table ;
	private JFrame frmEhProject ;
	private JLabel lblNewLabel;
	
//The following variables are used to for EQ and SSH files import:
	private String EQFileDirectory ;
	private String SSHFileDirectory ;
	
//The following ArrayLists are used to store parsed data from EQ and SSH file:
	private ArrayList<String> BaseVoltage_A,Substation_A,VoltageLevel_A,GeneratingUnit_A,SyncMachine_A,RegulatingControl_A,Transformer_A,EnergyConsumer_A,Winding_A,Breaker_A, TapChanger_A ;
	private ArrayList<String> RegulatingControl_A_SSH,SyncMachine_A_SSH,EnergyConsumer_A_SSH, Breaker_A_SSH, TapChanger_A_SSH ;
	
//The following ArrayLists are used to contain the parsed data which will be inserted to database :
	private ArrayList<String> BaseVoltage_SQL,Substation_SQL,VoltageLevel_SQL,GeneratingUnit_SQL,SyncMachine_SQL,RegulatingControl_SQL,Transformer_SQL,EnergyConsumer_SQL,Winding_SQL,Breaker_SQL, TapChanger_SQL ;

//The following ArrayLists are used in constructing the Y mat:
	private ArrayList<String> breakerClosed, WindingTerminal, TransformerID, WindingBaseVolt ;

//The following variables are used to for presenting Y mat in gui table form:
	private String[] TableColumn;
	private String[][] AdmitMatrix_String;

	
//Main
public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Project1 window = new Project1();
					window.frmEhProject.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//This method is used to build user GUI interface
public Project1() {
	
// Created by windows builder Swing designer

// All GuI elements are created 
	//Frame
		frmEhProject = new JFrame();
		frmEhProject.getContentPane().setBackground(new Color(26, 86, 166));
		frmEhProject.setTitle("EH2745: Project 1");
		frmEhProject.setBounds(100, 100, 504, 551);
		frmEhProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEhProject.getContentPane().setLayout(null);
	//Buttons
		b1 = new JButton("Import EQ");
		b1.setForeground(Color.WHITE);
		b1.setBackground(new Color(26, 86, 166));
		b1.setBounds(38, 117, 149, 41);
		frmEhProject.getContentPane().add(b1);
		
		b2 = new JButton("Import SSH");
		b2.setForeground(Color.WHITE);
		b2.setBackground(new Color(26, 86, 166));
		b2.setBounds(38, 176, 149, 41);
		frmEhProject.getContentPane().add(b2);
		
		b4 = new JButton("Execute");
		b4.setForeground(Color.WHITE);
		b4.setBackground(new Color(26, 86, 166));
		b4.setBounds(38, 299, 149, 41);
		frmEhProject.getContentPane().add(b4);
		
		b3 = new JButton("Login To Database");
		b3.setForeground(Color.WHITE);
		b3.setBackground(new Color(26, 86, 166));;
		b3.setBounds(38, 240, 149, 41);
		frmEhProject.getContentPane().add(b3);
		
	//Labels	
		
		lblEqFileHas = new JLabel("EQ file has been imported");
		lblEqFileHas.setBounds(197, 130, 184, 14);
		lblEqFileHas.setForeground(Color.WHITE);
		
		lblSshFileHas = new JLabel("SSH file has been imported");
		lblSshFileHas.setBounds(197, 189, 184, 14);
		lblSshFileHas.setForeground(Color.WHITE);
		
		lblLoginToDatabase = new JLabel("Login to database is successful");
		lblLoginToDatabase.setBounds(197, 253, 184, 14);
		lblLoginToDatabase.setForeground(Color.WHITE);
		
		lblProgramHasBeen = new JLabel("Program has been executed successfully");
		lblProgramHasBeen.setBounds(197, 312, 261, 14);
		lblProgramHasBeen.setForeground(Color.WHITE);
		
		JLabel lblEhProject = new JLabel("EH2745: Project 1");
		lblEhProject.setForeground(Color.WHITE);
		lblEhProject.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 25));
		lblEhProject.setBounds(104, 11, 282, 53);
		frmEhProject.getContentPane().add(lblEhProject);
		
		JLabel lblDevelopedByAmar = new JLabel("Developed by: Amar Abideen & Hatem Alatawi");
		lblDevelopedByAmar.setForeground(Color.WHITE);
		lblDevelopedByAmar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDevelopedByAmar.setBounds(129, 481, 228, 20);
		frmEhProject.getContentPane().add(lblDevelopedByAmar);
		
		JLabel lblThisProgramWill = new JLabel("This program will read CIM file then store it into a database \r\nand calculate Y matrix");
		lblThisProgramWill.setForeground(Color.WHITE);
		lblThisProgramWill.setBounds(10, 61, 468, 28);
		frmEhProject.getContentPane().add(lblThisProgramWill);
		
	    // Picture   
        lblNewLabel = new JLabel("");
        Image img = new ImageIcon(this.getClass().getResource("/kth_logo.gif")).getImage();
        lblNewLabel.setIcon(new ImageIcon(img));
        lblNewLabel.setBounds(192, 370, 100, 100);
        frmEhProject.getContentPane().add(lblNewLabel);
        
        // Buttons settings

        b1.setToolTipText("Click this button to Load the system EQ file");
        b2.setToolTipText("Click this button to Load the system SSH file");
        b3.setToolTipText("Click this button to access Database");
        b4.setToolTipText("Click this button to load data into database and view the Y matrix");


        // Initial Display
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);

        lblEqFileHas.setEnabled(false); 
    	lblSshFileHas.setEnabled(false); 
    	lblLoginToDatabase.setEnabled(false); 
    	lblProgramHasBeen.setEnabled(false); 

    	// Buttons ction commands and listeners
    	
	        b1.setActionCommand("1");
	        b2.setActionCommand("2");
	        b3.setActionCommand("3");
	        b4.setActionCommand("4");

	        b1.addActionListener(this);
	        b2.addActionListener(this);
	        b3.addActionListener(this);
	        b4.addActionListener(this);



		
	}

//This method is used to define the actionPerformed for buttons actionListerner
public void actionPerformed(ActionEvent e) {
    	
    	
    	if ("1".equals(e.getActionCommand())) {
    		ImportEQ();
    	} 
        
        if ("2".equals(e.getActionCommand())) {
        	ImportSSH() ;
        } 
        
        if ("3".equals(e.getActionCommand())) {
        	DatabaseLog();
        }
        
        if ("4".equals(e.getActionCommand())) {
        	
            ReadEQfile EQfileObject = new ReadEQfile(EQFileDirectory);
            
        	ReadSSHfile SSHfileObject = new ReadSSHfile(SSHFileDirectory);
        	
    		ParseImportedFiles(EQfileObject,SSHfileObject);
    		
    		LoadDatabase() ;
    		
    		
    		ConstructYmat() ;
			
    		ShowYmat() ;

        }
}

//This method is used to proceed to EQ import file browser
private void ImportEQ(){
	int EQopenFile;
	
// Open file browser
	JFileChooser EQfileChoose = new JFileChooser();
	EQfileChoose.setDialogTitle("Choose Directory Of EQ");
	EQfileChoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
	EQopenFile = EQfileChoose.showOpenDialog(null);


	if(EQopenFile == JFileChooser.APPROVE_OPTION){

		File myFile = EQfileChoose.getSelectedFile(); 

			try{
// Save directory
				EQFileDirectory = myFile.getPath(); 
				EQFileDirectory = EQFileDirectory.replace("\\","\\\\");
		        System.out.println(EQFileDirectory);
	    		b2.setEnabled(true);
	    		frmEhProject.getContentPane().add(lblEqFileHas);
	            lblEqFileHas.setEnabled(true); 
				

				}catch (NullPointerException e1){
					
				}catch (Exception otherE){
					otherE.printStackTrace();
				}
	}

	else{

		System.out.println("EQ File is not Loaded");

	}



}

//This method is used to proceed to SSH import file browser
private void ImportSSH(){
	
	int SSHopenFile;
	
	
	JFileChooser SSHfileChoose = new JFileChooser();
	SSHfileChoose.setDialogTitle("Choose Directory");
	SSHfileChoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
	SSHopenFile = SSHfileChoose.showOpenDialog(null);


	if(SSHopenFile == JFileChooser.APPROVE_OPTION){

		File myFile = SSHfileChoose.getSelectedFile();


			try{


				SSHFileDirectory = myFile.getPath(); 

				SSHFileDirectory = SSHFileDirectory.replace("\\","\\\\");
				System.out.println(SSHFileDirectory);
				
				b3.setEnabled(true);
	    		frmEhProject.getContentPane().add(lblSshFileHas);
	    		lblSshFileHas.setEnabled(true); 


				}catch (NullPointerException e1){

					System.out.println("SSH File is not Loaded");

				}catch (Exception otherE){
					
					System.out.println("SSH File is not Loaded");
					otherE.printStackTrace();

				}

	}

	else{

		System.out.println("SSH File is not Loaded");

	}
	
}

//This method is used to proceed to database login frame
private void DatabaseLog(){

// Create frame 
	
	final JFrame LoginFrame = new JFrame("Login To Database1");

    LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    LoginFrame.setSize(300,100);

    LoginFrame.setVisible(false);

    
 // Go to this method
    DatabaseUserChecker login = new DatabaseUserChecker(LoginFrame);

	    login.setVisible(true);

	    if(login.UserLogged()){
	    	
	   try {
	    		Class.forName("com.mysql.jdbc.Driver");
	    		String dbUrl="jdbc:mysql://localhost/testing?user=root&password=y9mkrg6wyc8r";
	    		DriverManager.getConnection(dbUrl);
	    		
	    	} catch (ClassNotFoundException | SQLException e2) {
	    		// TODO Auto-generated catch block
	    		e2.printStackTrace();
	   }

	    new DataBaseClass("project1grid");
    	b4.setEnabled(true);
    	frmEhProject.getContentPane().add(lblLoginToDatabase);
    	lblLoginToDatabase.setEnabled(true);

	    LoginFrame.setVisible(false);
	    
	}
	
}

//This method is used to proceed to parsing imported data
private void ParseImportedFiles(ReadEQfile EQfileObject,ReadSSHfile SSHfileObject) {

	EQfileObject.FileParse();
	SSHfileObject.FileParse();

//For BaseVoltage  ------------------------------------------------------------------------------------------------ ------------------------------------------------------------------------------------------------

	BaseVoltage_A = new ArrayList<String>();
	BaseVoltage_SQL = new ArrayList<String>();	
	
// Create and Extract NodeList
	EQfileObject.createNodeList("cim:BaseVoltage");
	EQfileObject.extractNode("cim:BaseVoltage",BaseVoltage_A);
	
// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<BaseVoltage_A.size();i+=2){

			double BaseVoltageValue = Double.parseDouble(BaseVoltage_A.get(i+1)); // 
			
			//                             ID                  Nominal Value 
			BaseVoltage_SQL.add("'"+BaseVoltage_A.get(i)+"',"+BaseVoltageValue+"");
			
		}
	}catch(ArrayIndexOutOfBoundsException e1){
		e1.printStackTrace();
	}

//For Substation  ------------------------------------------------------------------------------------------------
	
	Substation_A = new ArrayList<String>();
	Substation_SQL = new ArrayList<String>();

// Create and Extract NodeList
	EQfileObject.createNodeList("cim:Substation");
	EQfileObject.extractNode("cim:Substation",Substation_A);

// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<Substation_A.size();i+=3){
			
			//                      Substation ID                     Name                      RegionID
			Substation_SQL.add("'"+Substation_A.get(i)+"','"+Substation_A.get(i+1)+"','"+Substation_A.get(i+2)+"'");
		
		}
	}catch(ArrayIndexOutOfBoundsException e2){
		e2.printStackTrace();
	}
	  		
//For VoltageLevel  ------------------------------------------------------------------------------------------------
	
	VoltageLevel_A = new ArrayList<String>();
	VoltageLevel_SQL = new ArrayList<String>();

// Create and Extract NodeList
	EQfileObject.createNodeList("cim:VoltageLevel");
	EQfileObject.extractNode("cim:VoltageLevel",VoltageLevel_A);
	
// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<VoltageLevel_A.size();i+=4){
			double VoltageLevelValue = Double.parseDouble(VoltageLevel_A.get(i+1));	
			
			//                            VoltageID                    Name                SubstationID                   BaseVoltageID
			VoltageLevel_SQL.add("'"+VoltageLevel_A.get(i)+"',"+VoltageLevelValue+",'"+VoltageLevel_A.get(i+2)+"','"+VoltageLevel_A.get(i+3)+"'");
			
		}
	}catch(ArrayIndexOutOfBoundsException e3){
		e3.printStackTrace();
	}

// For GeneratingUnit  ------------------------------------------------------------------------------------------------

	GeneratingUnit_A = new ArrayList<String>();
	GeneratingUnit_SQL = new ArrayList<String>();
	
// Create and Extract NodeList
	EQfileObject.createNodeList("cim:GeneratingUnit");
	EQfileObject.extractNode("cim:GeneratingUnit",GeneratingUnit_A);

// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<GeneratingUnit_A.size();i+=5){
			double GeneratingUnit_MaxP = Double.parseDouble(GeneratingUnit_A.get(i+2));	
			double GeneratingUnit_MinP = Double.parseDouble(GeneratingUnit_A.get(i+3));	
			
	        //                            GeneratingUnitID                     Name                        MaxP                    MinP                      SubstationID
			GeneratingUnit_SQL.add("'"+GeneratingUnit_A.get(i)+"','"+GeneratingUnit_A.get(i+1)+"',"+GeneratingUnit_MaxP+","+GeneratingUnit_MinP+",'"+GeneratingUnit_A.get(i+4)+"'");
			
		}
	}catch(ArrayIndexOutOfBoundsException e4){
		e4.printStackTrace();
	}

// For RegulatingControl  ------------------------------------------------------------------------------------------------		

	RegulatingControl_A = new ArrayList<String>();
	RegulatingControl_A_SSH = new ArrayList<String>();
	RegulatingControl_SQL = new ArrayList<String>();

// Create and Extract NodeList
// Note: EQ node will be stored in RegulatingControl_A 
	EQfileObject.createNodeList("cim:RegulatingControl");
	EQfileObject.extractNode("cim:RegulatingControl",RegulatingControl_A); 
	SSHfileObject.createNodeList("cim:RegulatingControl");
	SSHfileObject.extractNodeSSH("cim:RegulatingControl",RegulatingControl_A_SSH);

// Combine node EQ and SSH
	RegulatingControl_A = CombineArrayLists(RegulatingControl_A,RegulatingControl_A_SSH);

// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<RegulatingControl_A.size();i+=3){
			double RegulatingControl_TargetValue = Double.parseDouble(RegulatingControl_A.get(i+2));
			
			//                              RegulatingControlID                        Name                               Value
			RegulatingControl_SQL.add("'"+RegulatingControl_A.get(i)+"','"+RegulatingControl_A.get(i+1)+"',"+RegulatingControl_TargetValue+"");
		
		}
	}catch(ArrayIndexOutOfBoundsException e6){
		e6.printStackTrace();
	}

// For SyncronousMachine  ------------------------------------------------------------------------------------------------	
	  			
	SyncMachine_A = new ArrayList<String>();
	SyncMachine_A_SSH = new ArrayList<String>();
	SyncMachine_SQL = new ArrayList<String>();

// Create and Extract NodeList
// Note: EQ node will be stored in SyncMachin_A 
	EQfileObject.createNodeList("cim:SynchronousMachine");
	EQfileObject.extractNode("cim:SynchronousMachine",SyncMachine_A);
	SSHfileObject.createNodeList("cim:SynchronousMachine");
	SSHfileObject.extractNodeSSH("cim:SynchronousMachine",SyncMachine_A_SSH);

// Combine node EQ and SSH
	SyncMachine_A = CombineArrayLists(SyncMachine_A,SyncMachine_A_SSH);	

// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<SyncMachine_A.size();i+=8){
			double SyncMachine_S = Double.parseDouble(SyncMachine_A.get(i+2));
			double SyncMachine_P = Double.parseDouble(SyncMachine_A.get(i+6));
			double SyncMachine_Q = Double.parseDouble(SyncMachine_A.get(i+7));
			
			//                       MachineID                         Name                     S                  P                Q               GeneratingUnitID            RegulatingControlID             VoltageLevelID
			SyncMachine_SQL.add("'"+SyncMachine_A.get(i)+"','"+SyncMachine_A.get(i+1)+"',"+SyncMachine_S+","+SyncMachine_P+","+SyncMachine_Q+",'"+SyncMachine_A.get(i+3)+"','"+SyncMachine_A.get(i+4)+"','"+SyncMachine_A.get(i+5)+"'");
		
		}
	}catch(ArrayIndexOutOfBoundsException e5){
		e5.printStackTrace();
	}

// For PowerTransformer  ------------------------------------------------------------------------------------------------	

	Transformer_A = new ArrayList<String>();
	Transformer_SQL = new ArrayList<String>();	

// Used for the Y mat calculation
	TransformerID = new ArrayList<String>();
	
// Create and Extract NodeList
	EQfileObject.createNodeList("cim:PowerTransformer");
	EQfileObject.extractNode("cim:PowerTransformer",Transformer_A);

// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<Transformer_A.size();i+=3){
			
			//                         TransformerID                    Name                      SubtationID
			Transformer_SQL.add("'"+Transformer_A.get(i)+"','"+Transformer_A.get(i+1)+"','"+Transformer_A.get(i+2)+"'");
			
			TransformerID.add(Transformer_A.get(i));	//Used for Y mat to consider only IDs for connected transformers
		}
	}catch(ArrayIndexOutOfBoundsException e7){
		e7.printStackTrace();
	}

	

// For EnergyConsumer  ------------------------------------------------------------------------------------------------

	EnergyConsumer_A = new ArrayList<String>();
	EnergyConsumer_A_SSH = new ArrayList<String>();
	EnergyConsumer_SQL = new ArrayList<String>();	
	
// Create and Extract NodeList
// Note: EQ node will be stored in EnergyConsumer_A 
	EQfileObject.createNodeList("cim:EnergyConsumer");
	EQfileObject.extractNode("cim:EnergyConsumer",EnergyConsumer_A);
	SSHfileObject.createNodeList("cim:EnergyConsumer");
	SSHfileObject.extractNodeSSH("cim:EnergyConsumer",EnergyConsumer_A_SSH);

// Combine node EQ and SSH
	EnergyConsumer_A = CombineArrayLists(EnergyConsumer_A,EnergyConsumer_A_SSH);
	
// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<EnergyConsumer_A.size();i+=6){
			double EnergyConsumer_P = Double.parseDouble(EnergyConsumer_A.get(i+4));
			double EnergyConsumer_Q = Double.parseDouble(EnergyConsumer_A.get(i+5));
			
			//                             ConsumerID                        Name                          P                    Q                  VoltageLevelID
			EnergyConsumer_SQL.add("'"+EnergyConsumer_A.get(i)+"','"+EnergyConsumer_A.get(i+1)+"',"+EnergyConsumer_P+","+EnergyConsumer_Q+",'"+EnergyConsumer_A.get(i+2)+"'");
		
		}
	}catch(ArrayIndexOutOfBoundsException e8){
		e8.printStackTrace();
	}
 			
// For Breaker  ------------------------------------------------------------------------------------------------

	Breaker_A = new ArrayList<String>();
	Breaker_A_SSH = new ArrayList<String>();
	Breaker_SQL = new ArrayList<String>();	

//Used for the Y mat calculation	
	breakerClosed = new ArrayList<String>();	
	
// Create and Extract NodeList
// Note: EQ node will be stored in Breaker_A 
	EQfileObject.createNodeList("cim:Breaker");
	EQfileObject.extractNode("cim:Breaker",Breaker_A);
	SSHfileObject.createNodeList("cim:Breaker");
	SSHfileObject.extractNodeSSH("cim:Breaker",Breaker_A_SSH);

// Combine node EQ and SSH
	Breaker_A = CombineArrayLists(Breaker_A,Breaker_A_SSH);

// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<Breaker_A.size();i+=4){
			boolean BreakerState = Boolean.parseBoolean(Breaker_A.get(i+3));
			
			//                     BreakerID                 Name              BreakerState       VoltageLevelID
			Breaker_SQL.add("'"+Breaker_A.get(i)+"','"+Breaker_A.get(i+1)+"',"+BreakerState+",'"+Breaker_A.get(i+2)+"'");
			
			breakerClosed.add(Breaker_A.get(i));
			breakerClosed.add(Breaker_A.get(i+3));		//Breaker state

		}
	}catch(ArrayIndexOutOfBoundsException e10){
		e10.printStackTrace();
	}
 			
// For TransformerWinding  ------------------------------------------------------------------------------------------------

	Winding_A = new ArrayList<String>();
	Winding_SQL = new ArrayList<String>();	//Used exclusively for the SQL Database

//Used for the Y mat calculation
	WindingTerminal = new ArrayList<String>();	
	WindingBaseVolt = new ArrayList<String>();	

// Create and Extract NodeList
	EQfileObject.createNodeList("cim:PowerTransformerEnd");
	EQfileObject.extractNode("cim:PowerTransformerEnd",Winding_A);

	try{
		for(int i = 0;i<Winding_A.size();i+=7){
			double Winding_r = Double.parseDouble(Winding_A.get(i+2));	//r
			double Winding_x = Double.parseDouble(Winding_A.get(i+3));	//x
			
			//                    Winding_ID                  Name                 R             X            TransformerID              BaseVoltageID
			Winding_SQL.add("'"+Winding_A.get(i)+"','"+Winding_A.get(i+1)+"',"+Winding_r+","+Winding_x+",'"+Winding_A.get(i+5)+"','"+Winding_A.get(i+6)+"'");
			
			WindingTerminal.add(Winding_A.get(i));
			//Winding Terminal ID
			WindingTerminal.add(Winding_A.get(i+4));	
			WindingBaseVolt.add(Winding_A.get(i));
			//Winding BaseVoltage ID
			WindingBaseVolt.add(Winding_A.get(i+6));	
		}
	}catch(ArrayIndexOutOfBoundsException e9){
		e9.printStackTrace();
	}

// For TapChanger  ------------------------------------------------------------------------------------------------
	
	TapChanger_A = new ArrayList<String>();
	TapChanger_A_SSH = new ArrayList<String>();
	TapChanger_SQL = new ArrayList<String>();
	
// Create and Extract NodeList
// Note: EQ node will be stored in TapChanger_A
	EQfileObject.createNodeList("cim:RatioTapChanger");
	EQfileObject.extractNode("cim:RatioTapChanger",TapChanger_A);
	SSHfileObject.createNodeList("cim:RatioTapChanger");
	SSHfileObject.extractNodeSSH("cim:RatioTapChanger",TapChanger_A_SSH);

// Combine node EQ and SSH
	TapChanger_A = CombineArrayLists(TapChanger_A,TapChanger_A_SSH);

// Prepare SQL table to be ready for dispatch
	try{
		for(int i = 0;i<TapChanger_A.size();i+=4){
			int TapChanger_Step = Integer.parseInt(TapChanger_A.get(i+3));
			
			//                       TapchangerID                    Name                  ChangeStep              WindingID
			TapChanger_SQL.add("'"+TapChanger_A.get(i)+"','"+TapChanger_A.get(i+1)+"',"+TapChanger_Step+",'"+TapChanger_A.get(i+2)+"'");
		}
	}catch(ArrayIndexOutOfBoundsException e11){
		e11.printStackTrace();
	}

}

//This method is used to proceed to loading parsed data into the SQL database
private void LoadDatabase() {

	
	try{
		
		DataBaseClass database = new DataBaseClass("Project1Grid");

//BaseVoltage Table ------------------------------------------------------------------------------------------------ ------------------------------------------------------------------------------------------------
		
			database.CreateTable("BaseVoltage","(ID VARCHAR(100) NOT NULL, NOMINAL_VALUE FLOAT, PRIMARY KEY(ID))");
			database.InsertArrayToTable("BaseVoltage",BaseVoltage_SQL);

//Substation Table ------------------------------------------------------------------------------------------------
			
			database.CreateTable("Substation","(SUBSTATION_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), REGION_ID VARCHAR(100), PRIMARY KEY(SUBSTATION_ID))");
			database.InsertArrayToTable("Substation",Substation_SQL);
		
//VoltageLevel Table ------------------------------------------------------------------------------------------------
			
			database.CreateTable("VoltageLevel","(VOLTAGE_ID VARCHAR(100) NOT NULL, NAME FLOAT, SUBSTATION_ID VARCHAR(100), BASEVOLTAGE_ID VARCHAR(100), PRIMARY KEY(VOLTAGE_ID),"

					+ "FOREIGN KEY (SUBSTATION_ID) REFERENCES Substation(SUBSTATION_ID) ON DELETE CASCADE ON UPDATE CASCADE,"

					+ "FOREIGN KEY (BASEVOLTAGE_ID) REFERENCES BaseVoltage(ID) ON DELETE CASCADE ON UPDATE CASCADE)");

			database.InsertArrayToTable("VoltageLevel",VoltageLevel_SQL);

//GeneratingUnit Table ------------------------------------------------------------------------------------------------

			database.CreateTable("GeneratingUnit","(GENUNIT_ID VARCHAR(100) NOT NULL, NAME VARCHAR(30), MAX_P FLOAT, MIN_P FLOAT, SUBSTATION_ID VARCHAR(100), PRIMARY KEY (GENUNIT_ID),"

									+ "FOREIGN KEY (SUBSTATION_ID) REFERENCES Substation(SUBSTATION_ID) ON DELETE CASCADE ON UPDATE CASCADE)");

			database.InsertArrayToTable("GeneratingUnit",GeneratingUnit_SQL);

//RegulatingControl Table ------------------------------------------------------------------------------------------------		

			database.CreateTable("RegulatingControl","(REGCONTROL_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), VALUE FLOAT, PRIMARY KEY (REGCONTROL_ID))");

			database.InsertArrayToTable("RegulatingControl",RegulatingControl_SQL);

//SyncronousMachine Table ------------------------------------------------------------------------------------------------	
			
			database.CreateTable("SyncronousMachine","(MACHINE_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), S FLOAT, P FLOAT, Q FLOAT, GENERATINGUNIT_ID VARCHAR(100), REGULATINGCONTROL_ID VARCHAR(100), VOLTAGELEVEL_ID VARCHAR(100), PRIMARY KEY(MACHINE_ID),"

									+ "FOREIGN KEY (GENERATINGUNIT_ID) REFERENCES GeneratingUnit(GENUNIT_ID) ON DELETE CASCADE ON UPDATE CASCADE,"

									+ "FOREIGN KEY (REGULATINGCONTROL_ID) REFERENCES RegulatingControl(REGCONTROL_ID) ON DELETE CASCADE ON UPDATE CASCADE,"

									+ "FOREIGN KEY (VOLTAGELEVEL_ID) REFERENCES VoltageLevel(VOLTAGE_ID) ON DELETE CASCADE ON UPDATE CASCADE)");

			database.InsertArrayToTable("SyncronousMachine",SyncMachine_SQL);

//PowerTransformer Table ------------------------------------------------------------------------------------------------	

			database.CreateTable("PowerTransformer","(TRANSFORMER_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), SUBSTATION_ID VARCHAR(100), PRIMARY KEY (TRANSFORMER_ID),"

									+ "FOREIGN KEY (SUBSTATION_ID) REFERENCES Substation(SUBSTATION_ID) ON DELETE CASCADE ON UPDATE CASCADE)");
			
			database.InsertArrayToTable("PowerTransformer",Transformer_SQL);

//EnergyConsumer Table ------------------------------------------------------------------------------------------------


			database.CreateTable("EnergyConsumer","(CONSUMER_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), P FLOAT, Q FLOAT, VOLTAGELEVEL_ID VARCHAR(100), PRIMARY KEY(CONSUMER_ID),"

									+ "FOREIGN KEY (VOLTAGELEVEL_ID) REFERENCES VoltageLevel(VOLTAGE_ID) ON DELETE CASCADE ON UPDATE CASCADE)");

			database.InsertArrayToTable("EnergyConsumer",EnergyConsumer_SQL);

			
//Breaker Table ------------------------------------------------------------------------------------------------


			database.CreateTable("Breaker","(BREAKER_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), BREAKER_STATE BOOL, VOLTAGELEVEL_ID VARCHAR(100), PRIMARY KEY(BREAKER_ID),"

									+ "FOREIGN KEY (VOLTAGELEVEL_ID) REFERENCES VoltageLevel(VOLTAGE_ID) ON DELETE CASCADE ON UPDATE CASCADE)");

			database.InsertArrayToTable("Breaker",Breaker_SQL);

			
//TransformerWinding Table ------------------------------------------------------------------------------------------------


			database.CreateTable("TransformerWinding","(WINDING_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), R FLOAT, X FLOAT, TRANSFORMER_ID VARCHAR(100), BASEVOLTAGE_ID VARCHAR(100), PRIMARY KEY(WINDING_ID, TRANSFORMER_ID),"

									+ "FOREIGN KEY (TRANSFORMER_ID) REFERENCES PowerTransformer(TRANSFORMER_ID) ON DELETE CASCADE ON UPDATE CASCADE,"

									+ "FOREIGN KEY (BASEVOLTAGE_ID) REFERENCES BaseVoltage (ID) ON DELETE CASCADE ON UPDATE CASCADE)");

			
			database.InsertArrayToTable("TransformerWinding",Winding_SQL);


//TapChanger Table ------------------------------------------------------------------------------------------------


			database.CreateTable("TapChanger","(TAPCHANGER_ID VARCHAR(100) NOT NULL, NAME VARCHAR(20), CHANGE_STEP INTEGER, WINDING_ID VARCHAR(100), PRIMARY KEY(TAPCHANGER_ID),"

									+ "FOREIGN KEY (WINDING_ID) REFERENCES TransformerWinding (WINDING_ID) ON DELETE CASCADE ON UPDATE CASCADE)");

			database.InsertArrayToTable("TapChanger",TapChanger_SQL);

			

//Close Database ------------------------------------------------------------------------------------------------

			database.CloseDatabase();	



		}catch (Exception sqle){

			sqle.printStackTrace();

		}
}

//This method is used to proceed to constructing Y mat
private void ConstructYmat() {
	
    ReadEQfile EQfileObject = new ReadEQfile(EQFileDirectory);
	ReadSSHfile SSHfileObject = new ReadSSHfile(SSHFileDirectory);
	
	EQfileObject.FileParse();
	SSHfileObject.FileParse();
	
	
	/* Step 1 - 1 - Create an arrayList from EQ for "cim:ConnectivityNode" with ID.
	* 			2 - Create an arrayList from EQ for "cim:Terminal" with ID, Connectivity Node and Conducting Equipment. 
	* 			3 - Create an arrayList from SSH for "cim:Terminal" with ID and Status. 
	* 			4 - Create an arrayList from EQ for "cim:ACLineSegment" with ID and base voltage, extract ID into Line ID
	*  Step 2 - 1 - Look for Breakers and terminals that are open or not connected and remove them from the arrayList
	* 			2 - Change Transformer ID into Winding ID for ease of calculation.
	* 			3 - Group 1 Terminals that share the same "cim:ConnectivityNode" into an arrayList.
	* 			4 - Create Terminal arrList based on breaker, 2 Terminal for each breaker
	* 			5 - Using Group 1 and terminal arrayList above we regroup it again creating the busses and create the admistiance
	*  Step 3 - 1 - Find the 2 terminals associated with with each "cim:ACLineSegment"
	* 			2 - For each line Terminals find the connected busses and used index for marking.
	* 			3 - Using The LineID find the associated vales for base voltage , then calculate (r+jx) and (g+jb)/2
	* 			4 - Add line admittance to both diagonal and non-diagonal elements, Add shunt Admittance to the diagonal elements.
	*  Step 4 - 1 - Find the Windings associated with each transformer see step (2 - 2)
	* 			2 - Find the terminal associated with each winding.
	* 			3 - For each transformar windings terminal find the connected bus and used index for marking.
	* 			4 - Using The windingID find the associated vales for base voltage, then calculate line and shunt admittance  and 
	* 			5 - Add line admittance (r1+jx1+r2+jx2) to both diagonal and non-diagonal elements, 
	* 			6 - Add corresponding shunt Admittance to the corresponding diagonal elements g1+jb1 and g2+jb2.
	    		*/
	    		
	    		double basePower = 1000;
	    		// Step 1
	    		
	    		ArrayList<String> connNode = new ArrayList<String>();
	    		EQfileObject.createNodeList("cim:ConnectivityNode");
	    		EQfileObject.extractNode("cim:ConnectivityNode",connNode);

	    		ArrayList<String> terminal = new ArrayList<String>();
	    		EQfileObject.createNodeList("cim:Terminal");
	    		EQfileObject.extractNode("cim:Terminal",terminal);

	    		ArrayList<String> TerminalStatus = new ArrayList<String>();
	    		SSHfileObject.createNodeList("cim:Terminal");
	    		SSHfileObject.extractNodeSSH("cim:Terminal",TerminalStatus);

	    		ArrayList<String> LineVoltage = new ArrayList<String>();	
	    		ArrayList<String> LineID = new ArrayList<String>();			
	    		EQfileObject.createNodeList("cim:ACLineSegment");
	    		EQfileObject.extractNode("cim:ACLineSegment",LineVoltage);
	    		for(int i = 0;i<LineVoltage.size();i+=2){
	    			LineID.add(LineVoltage.get(i));
	    		}
	    		
	    		CalculateYBus myobj = new CalculateYBus();
	    		
	    		// Step 2

	    		myobj.BreakerStatus(breakerClosed);	
				myobj.TerminalConnectionStatus(terminal,TerminalStatus);

				myobj.WindingTerminalReplacement(WindingTerminal,terminal,TransformerID);

				ArrayList<ArrayList<String>> GridBuses = new ArrayList<ArrayList<String>>();
				GridBuses = myobj.TerminalsInNode(terminal,connNode);
				
				

				ArrayList<String> breakerTerminal = new ArrayList<String>();
				breakerTerminal = myobj.BreakerTerminals(breakerClosed,terminal);

				GridBuses = myobj.TerminalsInBreakers(GridBuses,breakerTerminal);
				
				
				CMPLX[][] AdmitMatrix = new CMPLX[GridBuses.size()][GridBuses.size()];
				for(int i = 0;i<GridBuses.size();i++){
					for(int j = 0;j<GridBuses.size();j++){
						AdmitMatrix[i][j] = new CMPLX();
					}
				}
				
				// Step 3	
				for(int i = 0;i<LineID.size();i++){
				int index1 = 0;
				int index2 = 0;
				boolean FirstTerminalCheck = false;
				ArrayList<String> LineTerminals = new ArrayList<String>();
				String TempId = LineID.get(i);
				LineTerminals = myobj.LineTerminalss(TempId,terminal);
				for(int j = 0;j<LineTerminals.size();j++){
					if(!FirstTerminalCheck){
						for(int k = 0;k<GridBuses.size();k++){
							if(myobj.BusConnection(LineTerminals.get(j), GridBuses.get(k))){
								index1 = GridBuses.indexOf(GridBuses.get(k));
								FirstTerminalCheck = true;
								break;
							}
						}
					}
					else if(FirstTerminalCheck){
						for(int k = 0;k<GridBuses.size();k++){
							if(myobj.BusConnection(LineTerminals.get(j), GridBuses.get(k)) && GridBuses.indexOf(GridBuses.get(k))!=index1){
								index2 = GridBuses.indexOf(GridBuses.get(k));
								break;
							}
						}
					}
				}
				double baseVoltLine = myobj.BaseVoltageValue(TempId,BaseVoltage_A,LineVoltage);
				CMPLX[] calcAdmittance = myobj.LineAdmittance(EQfileObject,TempId,basePower,baseVoltLine);
					AdmitMatrix[index1][index2] = AdmitMatrix[index1][index2].Substraction(calcAdmittance[0]);
					AdmitMatrix[index2][index1] = AdmitMatrix[index2][index1].Substraction(calcAdmittance[0]);
					AdmitMatrix[index1][index1] = AdmitMatrix[index1][index1].Addition(calcAdmittance[0]);
					AdmitMatrix[index2][index2] = AdmitMatrix[index2][index2].Addition(calcAdmittance[0]);
					AdmitMatrix[index1][index1] = AdmitMatrix[index1][index1].Addition(calcAdmittance[1]);
					AdmitMatrix[index2][index2] = AdmitMatrix[index2][index2].Addition(calcAdmittance[1]);
				}

				// Step 4	
				for(int i = 0;i<TransformerID.size();i++){
					int index1 = 0;
					int index2 = 0;
					boolean FirstTerminalCheck = false;
					ArrayList<String> TransfWinding = new ArrayList<String>();
					String TempId = TransformerID.get(i);
					TransfWinding = EQfileObject.TransformerID2Windings(TempId);
					for(int j = 0;j<TransfWinding.size();j++){
						String winding1 = TransfWinding.get(j);	//Stores the ID of the first winding to use it for the calculations
						String windTerminal1 = myobj.WindingTerminals(winding1,terminal);
						if(!windTerminal1.equals("")){
							for(int k = 0;k<GridBuses.size();k++){
								if(myobj.BusConnection(windTerminal1, GridBuses.get(k))){
									index1 = GridBuses.indexOf(GridBuses.get(k));
									FirstTerminalCheck = true;
									break;
								}
							}
						}
						for(int m = j+1;m<TransfWinding.size();m++){
							String winding2 = TransfWinding.get(m);
							String windTerminal2 = myobj.WindingTerminals(winding2,terminal);
							if(!windTerminal1.equals("") && FirstTerminalCheck){
								for(int n = 0;n<GridBuses.size();n++){
									if(myobj.BusConnection(windTerminal2, GridBuses.get(n)) && GridBuses.indexOf(GridBuses.get(n))!=index1){
										index2 = GridBuses.indexOf(GridBuses.get(n));
										double baseVolt1 = myobj.BaseVoltageValue(winding1, BaseVoltage_A, WindingBaseVolt);
										double baseVolt2 = myobj.BaseVoltageValue(winding2, BaseVoltage_A, WindingBaseVolt);
										CMPLX[] calcAdmittance = myobj.TransAdmittance(EQfileObject,winding1,winding2,baseVolt1,baseVolt2,basePower);
										
										AdmitMatrix[index1][index2] = AdmitMatrix[index1][index2].Substraction(calcAdmittance[0]);
										AdmitMatrix[index2][index1] = AdmitMatrix[index2][index1].Substraction(calcAdmittance[0]);
										AdmitMatrix[index1][index1] = AdmitMatrix[index1][index1].Addition(calcAdmittance[0]);
										AdmitMatrix[index2][index2] = AdmitMatrix[index2][index2].Addition(calcAdmittance[0]);
										AdmitMatrix[index1][index1] = AdmitMatrix[index1][index1].Addition(calcAdmittance[1]);
										AdmitMatrix[index2][index2] = AdmitMatrix[index2][index2].Addition(calcAdmittance[2]);
										
										
									}
								}
							}
						}
					}
				}
				
				TableColumn = new String[GridBuses.size()];
				for(int i = 0;i<TableColumn.length;i++){
					TableColumn[i] = Integer.toString(i+1);
				}
				
				AdmitMatrix_String = new String[GridBuses.size()][GridBuses.size()];

				for(int i = 0;i<GridBuses.size();i++){
					for(int j = 0;j<GridBuses.size();j++){
						if (AdmitMatrix[i][j].getImgPart()>=0) {
							AdmitMatrix_String[i][j] = String.format("%.4f+%.4f" + "i",AdmitMatrix[i][j].getRealPart(), AdmitMatrix[i][j].getImgPart());
						}
						if (AdmitMatrix[i][j].getImgPart()<0) {
							AdmitMatrix_String[i][j] = String.format("%.4f%.4f" + "i",AdmitMatrix[i][j].getRealPart(), AdmitMatrix[i][j].getImgPart());
						}
					}
				}	
		
	}

//This method is used to show the Y mat in GUI
private void ShowYmat() {
	 
	 YbusGUI gui = new YbusGUI(AdmitMatrix_String,TableColumn);

	 gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	 gui.setSize(600,200);

	 gui.setVisible(true);

	 gui.setTitle("Ybus Admittance Matrix");
	 
	frmEhProject.getContentPane().add(lblProgramHasBeen);
	lblProgramHasBeen.setEnabled(true); 

 }
    
//This method is used to combine the extracted EQ and SSH ArrayList for nodes that have attributes in both

public static ArrayList<String> CombineArrayLists(ArrayList<String> EqList, ArrayList<String> sshList){

	

	List<String> SplitEQ = new ArrayList<String>();
	List<String> SplitSSH = new ArrayList<String>();
	ArrayList<String> NewCombinedArrayList = new ArrayList<String>();

	

// Step 1: Sweep through EQ and SSH ArrayList and locate "Dummy" indices then store them in  DummiesEQ & DummiesSSH 

	ArrayList<Integer> DummiesEQ = new ArrayList<Integer>();
	ArrayList<Integer> DummiesSSH = new ArrayList<Integer>();

	for(int i = 0;i<EqList.size();i++){
		if(EqList.get(i) == "Dummy"){
			DummiesEQ.add(i);
		}
	}

	for(int j = 0;j<sshList.size();j++){
		if(sshList.get(j) == "Dummy"){
			DummiesSSH.add(j);
		}
	}


//Step2: Check if the size of dummies matrices are equal
	if(DummiesEQ.size() == DummiesSSH.size()){

		if(DummiesEQ.size() == 1){

//Step 3: In case the size is one, add the ID and dummy index to arraylist first list and remove it from second list
//if the size is not one then proceed to step 4

			if(EqList.get(1).equals(sshList.get(1))){

				EqList.remove(0);
				sshList.remove(0);
				sshList.remove(0);

				NewCombinedArrayList.addAll(EqList);
				NewCombinedArrayList.addAll(sshList);

			}

		}

		else{

//Step 4: Based on indices from DummiesEQ and DummiesSSH then split both lists and add them to form NewCombinedArrayList

			DummiesEQ.add(EqList.size());
			DummiesSSH.add(sshList.size());

			for(int i = 0;i<DummiesEQ.size()-1;i++){
				for(int j = 0;j<DummiesSSH.size()-1;j++){

					if(EqList.get(DummiesEQ.get(i)+1).equals(sshList.get(DummiesSSH.get(j)+1))){

							SplitEQ = EqList.subList(DummiesEQ.get(i),DummiesEQ.get(i+1));
							SplitSSH = sshList.subList(DummiesSSH.get(j)+2,DummiesSSH.get(j+1));

							NewCombinedArrayList.addAll(SplitEQ);
							NewCombinedArrayList.addAll(SplitSSH);

					}
				}
			}
		}
	}

	else

System.out.println("For the given node the EQ and SSH Arraylists size does not match");

	for(int i = 0;i<NewCombinedArrayList.size();i++){

		if(NewCombinedArrayList.get(i) == "Dummy"){
			NewCombinedArrayList.remove(i);
			i=0;
		}
	}

	return NewCombinedArrayList;

}
}
