package Project;

//This class was written to accomplish the following tasks: 

//1- Create a window for the database user to enter username and password. 

//2- Athenticate username and password.


//*******************************************************************************************

//There is several Methods developed in this class which are:

//DatabaseUserChecker(Frame) Create GUI for the user to enter username and password on desired frame

//UserAuhentication(Username,Password) Compare the entered username and password

//UserLogged() report if the user entered username and password are correct

//UserNotLogged() report if the user entered username and password are not correct


//*******************************************************************************************


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DatabaseUserChecker extends JDialog {

    private JTextField UserNameTextField;
    private JPasswordField PasswordTextField;
    private JLabel UserNameLabel;
    private JLabel PasswordLabel;
    private JButton LoginButton;
    private JButton CancelButton;
    private boolean LoginSuccess;
    private boolean LoginFailure;

    
        public DatabaseUserChecker(Frame parent) {
        super(parent, "Login to Database", true);
 
 // Create a panel for fields and buttons    
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        
 // Create textfield for the user to enter username and label it
        UserNameLabel = new JLabel("Username: ");
        panel.add(UserNameLabel, cs);
        UserNameTextField = new JTextField(20);
        panel.add(UserNameTextField, cs);
        
// Create textfield for the user to enter password and label it
        PasswordLabel= new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(PasswordLabel, cs);
        
        PasswordTextField = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(PasswordTextField, cs);
        
 //Create Login Button and add an ActionLister to it when pressed to proceed to UserAuthentication

        LoginButton = new JButton("Login");
        
// Login actionlistener
        LoginButton.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e) {
                if (UserAuthentication(UserNameTextField.getText().trim(), new String(PasswordTextField.getPassword()))) {
                    LoginSuccess = true;
                    dispose();
                } else {
                	
 //In case of a wrong password show an error massage
                    JOptionPane.showMessageDialog(DatabaseUserChecker.this,
                    "Wrong username or password Please try again",
                    "LoginButton",
                    JOptionPane.ERROR_MESSAGE);
                    		
                    
// Reset the text fields and report the login status
                    UserNameTextField.setText("");
                    PasswordTextField.setText("");
                    LoginSuccess = false;
                    LoginFailure = true;
                }
            }
        });
        
 
//Create cancel Button and add an ActionLister to it when pressed to show error and close program

        CancelButton = new JButton("Cancel");
        CancelButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(DatabaseUserChecker.this,
                        "No userame or password were introduced. Error while attempting to Log in Database. "
                        + "Program Terminated",
                        "CancelButton",
                        JOptionPane.ERROR_MESSAGE);

                
            }
        });
        
//Create a panel to place login and canel buttons
        JPanel placeButton = new JPanel();
        placeButton.add(LoginButton);
        placeButton.add(CancelButton);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(placeButton, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
    

 //UserAuhentication(Username,Password) Compare the entered username and password

    public boolean UserAuthentication(String username, String password) {
  
   
        if (username.equals("root") && password.equals("y9mkrg6wyc8r")) { //Change this if u want to change username and password
            return true;
        }
        return false;
    }
    

    //UserLogged() report login success


    
    public boolean UserLogged() {
        return LoginSuccess;
    }
    
    //UserNotLogged() report login failure
    
    public boolean UserNotLogged(){
    	return LoginFailure;
    }
    


        
}