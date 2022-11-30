package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import Auth.Authenticator;

public class AuthGUI extends JPanel {
    
    // Define the components outside of constructor to remain in scope
    private JLabel usernameLabel, passwordLabel, statusLabel;
    private JTextField username;
    private JPasswordField password;
    private JButton signInButton;

    private static String ERR_IO = "<html>Error accessing users. Please check network and try again.</html>";
    private static String ERR_BAD_CRED = "<html>Invalid username/password.<br>Please try again.</html>";

    /**
     * Default constructor: Should be the only constructor used.
     * @param None
     * @return A new instance of a JPanel object to be used in the authenticator.
     */
    public AuthGUI() {
        
    	// Set the overarching layout for Auth GUI
    	setLayout(new GridBagLayout());

        GridBagConstraints c;

        // Status label, which is blank
        statusLabel = new JLabel("<html><br></html>"); statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER); statusLabel.setVerticalAlignment(SwingConstants.CENTER);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; c.weighty = 1; c.gridx = 0; c.gridy = 0; c.gridwidth = 3;
        this.add(statusLabel, c);

        // Sign-in username field
        usernameLabel = new JLabel("Username:   "); usernameLabel.setLabelFor(username);
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT); usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; c.weighty = 1; c.gridx = 0; c.gridy = 1; c.gridwidth = 2;
        this.add(usernameLabel, c);
        username = new JTextField(); username.setColumns(25);
        username.addActionListener(customListener);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE; c.weighty = 1; c.gridx = 2; c.gridy = 1; c.gridwidth = 1;
        this.add(username, c);

        // Sign-in password 
        passwordLabel = new JLabel("Password:   "); passwordLabel.setLabelFor(password);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT); passwordLabel.setVerticalAlignment(SwingConstants.CENTER);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; c.weighty = 1; c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        this.add(passwordLabel, c);
        password = new JPasswordField(); password.setColumns(25);
        password.addActionListener(customListener);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE; c.weighty = 1; c.gridx = 2; c.gridy = 2; c.gridwidth = 1;
        this.add(password, c);

        // Sign-in button
        signInButton = new JButton("Sign In");
        signInButton.addActionListener(customListener);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE; c.weighty = 1; c.gridx = 0; c.gridy = 3; c.gridwidth = 3;
        this.add(signInButton, c);

    }

    private ActionListener customListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // If the action command came from the username,
            // go to the password field
            if(e.getSource() == username) {
                password.requestFocusInWindow();
            }
            // Otherwise, attempt the sign-in.
            else {
                // Did the sign in authenticate?
                try {
                    if(Authenticator.users(username.getText(), String.valueOf(password.getPassword()))) {
                        // Sign in sucessful, request closure
                        statusLabel.setText("Success! Logging you in...");
                        requestExit();
                    }
                    else {
                        statusLabel.setText(ERR_BAD_CRED);
                    }
                } catch (IOException er) {
                    er.printStackTrace();
                    statusLabel.setText(ERR_IO);
                }
            }
            validate();
        }
    };

    /**
	 * Method for requesting closure of self. Used for signing in, and calls the static closure method
	 * in MainGUI.java. Results in destruction of this GUI element in memory.
	 * 
	 * @param none
	 * @return none
	 * @see MainGUI.exit
	 */
    private void requestExit() {
        MainGUI.exit(this);
    }

}
