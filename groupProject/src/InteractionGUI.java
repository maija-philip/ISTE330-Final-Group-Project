/**
 * Group 5:
 *      Maija Philip
 *      Thomas Pawlak
 *      Max Stein
 *      Will Rost
 *      Julia Sarun
 *
 *  ISTE 330
 *  Presentation Layer
 *
 *  This includes the Main()
 *
 *
 *  Every password for sample entries in our databases is "password"
 *  Our sample student usernames:
 *      mep4741
 *      tap1142
 *      war3857
 *      mhs8558
 *      jns2613
 *  Our sample faculty usernames:
 *      bricra
 *      jimhab
 *      profx
 *      shamas
 *      walwhy
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;


public class InteractionGUI {

    DataLayer dataLayer = new DataLayer();
    String typeOfUser = ""; // going to be STUDENT, PROF, or GUEST

    JFrame loginScreens,
            loginOrSignUp,
            menu;


    public static Font myFontForOutput = new Font("Courier", Font.BOLD, 20);

    /**
     Constructor - Make GUI
     */
    public InteractionGUI(){

        System.out.println("Connecting to the database . . .");


        JPanel Inputbox = new JPanel(new GridLayout(3,2));
        JLabel lblUser     = new JLabel("Username -> ");
        JLabel lblPassword = new JLabel("Password -> ");
        JTextField tfUser     = new JTextField("root");
        //JTextField tfPassword = new JTextField("");
        JTextField tfPassword = new JPasswordField("");
        JLabel lblDatabase    = new JLabel("Database ->");
        JTextField tfDatabase = new JTextField("330finalgroup5");

        Inputbox.add(lblUser);
        Inputbox.add(tfUser);
        Inputbox.add(lblDatabase);
        Inputbox.add(tfDatabase);
        Inputbox.add(lblPassword);
        Inputbox.add(tfPassword);



        lblUser.setFont(myFontForOutput);
        tfUser.setFont(myFontForOutput);
        tfUser.setForeground(Color.decode("#4E09C3"));
        tfUser.setBackground(Color.decode("#E9E0F8"));
        tfUser.setBorder(BorderFactory.createLineBorder(Color.decode("#A88AC2"), 2));
        lblPassword.setFont(myFontForOutput);
        tfPassword.setFont(myFontForOutput);
        tfPassword.setForeground(Color.decode("#4E09C3"));
        tfPassword.setBackground(Color.decode("#E9E0F8"));
        tfPassword.setBorder(BorderFactory.createLineBorder(Color.decode("#A88AC2"), 2));
        lblDatabase.setFont(myFontForOutput);
        tfDatabase.setFont(myFontForOutput);
        tfDatabase.setForeground(Color.decode("#4E09C3"));
        tfDatabase.setBackground(Color.decode("#E9E0F8"));
        tfDatabase.setBorder(BorderFactory.createLineBorder(Color.decode("#A88AC2"), 2));

        JOptionPane.showMessageDialog(null, Inputbox,
                "Input    Default password is \"student\"", JOptionPane.QUESTION_MESSAGE);


        String userName = tfUser.getText();
        String database = tfDatabase.getText();


        String password = new String();
        String passwordInput = new String();

        passwordInput = tfPassword.getText();

        // set the default password to   "student"
        if (passwordInput.equalsIgnoreCase("")) {
            password = "student";                  //CHANGE TO STUDENT
        }
        else
        {
            password = passwordInput;
        }



        //  use userName, password, and database to connect to data layer
        dataLayer.connect(userName,password,database);  //Call DataLayer

        System.out.println("You have connected to the database!");

        // DO THE STUFF

        /*
         * PUT GUI METHODS FOR TASKS IN HERE
         */

        loginScreens();


    } // End of Constructor


    //calculates the hash of a string
    public String hash(String pass){
        String output = "";


        //hash for "password:" 5baa61e4c9b93f3f68225b6cf8331b7ee68fd8

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(pass.getBytes("UTF-8"));

            for (byte x : hash) {
                output += (Integer.toHexString(x & 0xFF));
            }
        }
        catch(NoSuchAlgorithmException e){}
        catch(UnsupportedEncodingException e){}

        return output;
    }


    /**
     * The GUI for logging in as a student, prof, or guest
     */
    public void loginScreens() {
        // Screen 1: Login as student, professor, guest
        JPanel chooseScreen = new JPanel(new GridLayout(4,1));
        JLabel title     = new JLabel("Are you a student or a professor? Click to decide, then press OK");
        JButton professor = new JButton("Professor");
        JButton student = new JButton("Student");
        JButton guest = new JButton("Guest");

        // add professor action listener
        professor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Screen 2: Professor Login - link to create new professor
                typeOfUser = "PROF";
                loginScreens.setVisible(false);
                login();
            }
        });

        // add student action listener
        student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Screen 3: Student Login - link to create new student
                typeOfUser = "STUDENT";
                loginScreens.setVisible(false);
                login();
            }
        });

        // add guest action listener
        guest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Screen 3: Student Login - link to create new student
                typeOfUser = "GUEST";
                loginScreens.setVisible(false);
                menu();
            }
        });

        chooseScreen.add(title);
        chooseScreen.add(professor);
        chooseScreen.add(student);
        chooseScreen.add(guest);


        // show the login screen
        loginScreens = new JFrame("Choose a way to login");
        loginScreens.add(chooseScreen);
        loginScreens.pack();
        loginScreens.setLocationRelativeTo(null);
        loginScreens.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginScreens.setVisible(true);


    }
        private void login() {
            System.out.println("Login");

            // choose new or returning user
            JPanel btns = new JPanel(new GridLayout(3,1));
            JLabel title     = new JLabel("Are you a new or returning user?");
            JButton login = new JButton("Login");
            JButton signUp = new JButton("Sign Up");

            btns.add(title);
            btns.add(login);
            btns.add(signUp);

            // add login action listener
            login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loginOrSignUp.setVisible(false);
                    loginScreen();
                }
            });

            // add sign up action listener
            signUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loginOrSignUp.setVisible(false);
                    // send to new prof or new student method
                    System.out.println("Type of User: " + typeOfUser);
                    if (typeOfUser.equals("PROF")) {
                        newProfessor();

                    }
                    else {
                        newStudent();
                    }
                }
            });

            loginOrSignUp = new JFrame("Login or Sign Up");
            loginOrSignUp.add(btns);
            loginOrSignUp.pack();
            loginOrSignUp.setLocationRelativeTo(null);
            loginOrSignUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginOrSignUp.setVisible(true);

        }
            private void loginScreen() {
                boolean result;
                do {
                    // login
                    JPanel Inputbox = new JPanel(new GridLayout(3, 2));
                    JLabel lblUser = new JLabel("Username -> ");
                    JLabel lblPassword = new JLabel("Password -> ");
                    JTextField tfUser = new JTextField("");
                    JTextField tfPassword = new JPasswordField("");
           
                    Inputbox.add(lblUser);
                    Inputbox.add(tfUser);
                    Inputbox.add(lblPassword);
                    Inputbox.add(tfPassword);

                    JOptionPane.showMessageDialog(null, Inputbox,
                            "Login", JOptionPane.QUESTION_MESSAGE);

                    String username = tfUser.getText();
                    String password = tfPassword.getText();
                    String passwordhash = "";


                    if (password.equals("")) {
                        result = false;
                    }
                    else {
                        passwordhash = hash(password);
                        result = dataLayer.login(username, passwordhash);
                    }


                                      // call login method in the data layer

                    if (!result) {
                        JFrame jFrame = new JFrame();
                        JOptionPane.showMessageDialog(jFrame, "Your username or password is not correct");                    
                    }
                    else {
                        JFrame jFrame = new JFrame();
                        JOptionPane.showMessageDialog(jFrame, "Successful Login");
                    }
                } while (!result);

                menu();
            }

    /**
     * The GUI for new student
     */
    public void newStudent() {
        JPanel Inputbox = new JPanel(new GridLayout(4,2));
        JLabel lblUser     = new JLabel("Username -> ");
        JLabel lblPassword = new JLabel("Password -> ");
        JLabel lblName     = new JLabel("Name -> ");
        JLabel lblEmail    = new JLabel("Email -> ");
        JTextField tfUser     = new JTextField("");
        JTextField tfPassword = new JPasswordField("");
        JTextField tfName     = new JTextField("");
        JTextField tfEmail     = new JTextField("");


        Inputbox.add(lblUser);
        Inputbox.add(tfUser);
        Inputbox.add(lblPassword);
        Inputbox.add(tfPassword);

        Inputbox.add(lblName);
        Inputbox.add(tfName);
        Inputbox.add(lblEmail);
        Inputbox.add(tfEmail);

        JOptionPane.showMessageDialog(null, Inputbox,
                "New Student", JOptionPane.QUESTION_MESSAGE);

        String username = tfUser.getText();
        String password = tfPassword.getText();
        String name = tfName.getText();
        String email = tfEmail.getText();
        String passwordhash = "";

        int rows = 0;
        if (!(password.equals("") || username.equals("") || name.equals(""))) {            passwordhash = hash(tfPassword.getText());
            rows = dataLayer.addStudent(username, passwordhash, name, email);
        }


        if (rows > 1) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Inserted Student");        
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert Failed");        
        }

        menu();
    }

    /**
     * The GUI for new professor
     */
    public void newProfessor() {
        JPanel Inputbox = new JPanel(new GridLayout(7,2));
        JLabel lblUser     = new JLabel("Username -> ");
        JLabel lblPassword = new JLabel("Password -> ");
        JLabel lblName     = new JLabel("Name -> ");
        JLabel lblPhone     = new JLabel("Phone -> ");
        JLabel lblEmail    = new JLabel("Email -> ");
        JLabel lblBuilding    = new JLabel("Building -> ");
        JLabel lblOffice     = new JLabel("Office -> ");
        JTextField tfUser     = new JTextField("");
        JTextField tfPassword = new JPasswordField("");
        JTextField tfName     = new JTextField("");
        JTextField tfPhone    = new JTextField("");
        JTextField tfEmail     = new JTextField("");
        JTextField tfBuilding     = new JTextField("");
        JTextField tfOffice     = new JTextField("");



        Inputbox.add(lblUser);
        Inputbox.add(tfUser);
        Inputbox.add(lblPassword);
        Inputbox.add(tfPassword);

        Inputbox.add(lblName);
        Inputbox.add(tfName);
        Inputbox.add(lblPhone);
        Inputbox.add(tfPhone);
        Inputbox.add(lblEmail);
        Inputbox.add(tfEmail);
        Inputbox.add(lblBuilding);
        Inputbox.add(tfBuilding);
        Inputbox.add(lblOffice);
        Inputbox.add(tfOffice);

        JOptionPane.showMessageDialog(null, Inputbox,
                "New Faculty", JOptionPane.QUESTION_MESSAGE);

        String username = tfUser.getText();
        String password = tfPassword.getText();
        String name = tfName.getText();
        String phone = tfPhone.getText();
        String email = tfEmail.getText();
        String building = tfBuilding.getText();
        String office = tfOffice.getText();
        String passwordhash = "";

        int rows = 0;
        if (!(password.equals("") || username.equals("") || name.equals(""))) {
            passwordhash = hash(tfPassword.getText());
            rows = dataLayer.addFaculty(username, passwordhash, name, phone, email, building, office);
        }


        if (rows > 1) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Inserted Faculty");        
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert Failed");        
        }

        menu();
    }

    public void menu() {
        // choose action
        JPanel btns = new JPanel(new GridLayout(5,1));
        JLabel title     = new JLabel("Choose an action");
        JButton insertAbstract = new JButton("Insert Abstract");
        JButton insertInterest = new JButton("Insert Interest");
        JButton matchInterest = new JButton("Find Matches");
        JButton findStudent = new JButton("Find a Student");
        JButton findProfessor = new JButton("Find a Professor");
        JButton exit = new JButton("Exit");

        exit.setBackground(Color.decode("#d62d2d"));
        exit.setForeground(Color.BLACK);
        exit.setOpaque(true);
        exit.setBorderPainted(false);


        // add action listener
        insertAbstract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                // Insert Abstract
                insertProfessorAbstract();
            }
        });

        // add action listener
        insertInterest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                // send to prof or student interest insert
                if (typeOfUser.equals("PROF")) {
                    insertProfessorInterest();
                }
                else {
                    insertStudentInterest();
                }
            }
        });

        // add action listener
        matchInterest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                // Match Prof to Student
                profToStudentMatchResults();
            }
        });
       
        // add action listener
        findStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                // Match Prof to Student
                searchStudent();
            }
        });
        
         // add action listener
        findProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                // Match Prof to Student
                searchProfessor();
            }
        });

        // add action listener
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);

                // END THE PROGRAM

                System.out.println("\nClosing all connections to database...\n");
                dataLayer.close();

                // End Of Job data  -   EOJ  routines
                java.util.Date today = new java.util.Date();
                System.out.println("\nProgram terminated @ " + today);

                System.out.println("EOJ");
                System.exit(0);

            }
        });

        btns.add(title);

        if (typeOfUser.equals("PROF")) {
            btns.add(insertAbstract);
            btns.add(insertInterest);
            btns.add(matchInterest);
            btns.add(exit);
        } else if (typeOfUser.equals("STUDENT")) {
            btns.add(insertInterest);
            btns.add(matchInterest);
            btns.add(exit);
        } else {
            btns.add(findStudent);
            btns.add(findProfessor);
            btns.add(exit);
        }


        menu = new JFrame("Action Menu");
        menu.add(btns);
        menu.pack();
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setVisible(true);
    }

    /**
     * The GUI for inserting a student interest
     */
    public void insertStudentInterest() {
        JPanel Inputbox = new JPanel(new GridLayout(2,1));
        JLabel lblInterest     = new JLabel("Insert one of your interests");
        JTextField tfinterst     = new JTextField("");


        Inputbox.add(lblInterest);
        Inputbox.add(tfinterst);


        JOptionPane.showMessageDialog(null, Inputbox,
                "Insert Student Interest", JOptionPane.QUESTION_MESSAGE);


        String interest = tfinterst.getText();

        int rows = dataLayer.insertStudentInterest(interest);

        if (rows > 0) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Inserted Interest");        
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert Failed");        
        }



        menu();
    }

    /**
     * The GUI for inserting a professor interest
     */
    public void insertProfessorInterest() {
        JPanel Inputbox = new JPanel(new GridLayout(2,1));
        JLabel lblInterest     = new JLabel("Insert one of your interests");
        JTextField tfinterst     = new JTextField("");


        Inputbox.add(lblInterest);
        Inputbox.add(tfinterst);


        JOptionPane.showMessageDialog(null, Inputbox,
                "Insert Faculty Interest", JOptionPane.QUESTION_MESSAGE);


        String interest = tfinterst.getText();

        int rows = dataLayer.insertFacultyInterest(interest);

        if (rows > 0) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Inserted Interest");        
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert Failed");        
        }
        
        menu();
    }

    /**
     * The GUI for inserting a professor abstract
     */
    public void insertProfessorAbstract() {

        JPanel Inputbox = new JPanel(new GridLayout(2,2));
        JLabel lblTitle     = new JLabel("Abstract Title: ");
        JTextField tfTitle     = new JTextField("");
        JLabel lblBody     = new JLabel("Abstract Body: ");
        JTextField tfBody     = new JTextField("");


        Inputbox.add(lblTitle);
        Inputbox.add(tfTitle);
        Inputbox.add(lblBody);
        Inputbox.add(tfBody);


        JOptionPane.showMessageDialog(null, Inputbox,
                "Insert Professor Abstract", JOptionPane.QUESTION_MESSAGE);


        String title = tfTitle.getText();
        String body = tfBody.getText();


        int rows = dataLayer.insertAbstract(title, body);

        if (rows > 0) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Inserted Abstract");        
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert Failed");        
        }
        
        menu();

    }

    /**
     * The GUI result for matching prof to student
     */
    public void profToStudentMatchResults() {
        JPanel Inputbox = new JPanel(new GridLayout(1,2));
        JLabel lblterm    = new JLabel("Search Term: ");
        JTextField tfterm     = new JTextField("");


        Inputbox.add(lblterm);
        Inputbox.add(tfterm);


        JOptionPane.showMessageDialog(null, Inputbox,
                "Find Match", JOptionPane.QUESTION_MESSAGE);


        String term = tfterm.getText();


        String data = dataLayer.findMatch(term, typeOfUser);

        if (data.equals("")) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "No Results Found");
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, data);
        }

        menu();
    }
    
    public void searchStudent() {
        JPanel Inputbox = new JPanel(new GridLayout(1,2));
        JLabel lblterm    = new JLabel("Enter a term: ");
        JTextField tfterm     = new JTextField("");


        Inputbox.add(lblterm);
        Inputbox.add(tfterm);


        JOptionPane.showMessageDialog(null, Inputbox,
                "Find Student", JOptionPane.QUESTION_MESSAGE);


        String term = tfterm.getText();


        String data = dataLayer.searchStudents(term);

        if (data.equals("")) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "No Results Found");
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, data);
        }

        menu();
    }
    
    public void searchProfessor() {
        JPanel Inputbox = new JPanel(new GridLayout(1,2));
        JLabel lblterm    = new JLabel("Enter a term: ");
        JTextField tfterm     = new JTextField("");


        Inputbox.add(lblterm);
        Inputbox.add(tfterm);


        JOptionPane.showMessageDialog(null, Inputbox,
                "Find Professor", JOptionPane.QUESTION_MESSAGE);


        String term = tfterm.getText();


        String data = dataLayer.searchProfessors(term);

        if (data.equals("")) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "No Results Found");
        }
        else
        {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, data);
        }

        menu();
    }




    /**
     Main Method
     */
    public static void main(String [] args){
        new InteractionGUI();  // Create a new object. An Instantiation



    } // End of main method
} // End of Class