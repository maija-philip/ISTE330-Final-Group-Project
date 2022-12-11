/**
 * Group 5:
 *      Maija Philip
 *      Thomas Pawlak
 *      Max Stein
 *      Will Rost
 *      Julia Sarun
 *
 *  ISTE 330
 *  Data Layer
 */

import java.sql.*;

public class DataLayer {
    private Connection conn;
    private ResultSet rs;
    private Statement stmt;
    private String sql;
    private int col;

    private String username;

    final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";

    public DataLayer(){
    }//end of constructor

    public boolean connect(String user, String password, String database){
        conn = null;

        String url = "jdbc:mysql://localhost/" + database;
        url = url + "?serverTimezone=UTC"; //added only required for Mac Users

        try{
            Class.forName(DEFAULT_DRIVER);
            System.out.println("CLASSPATH is set correctly!");

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("\nCreated Connection!\n");
        }// end of try block
        catch(ClassNotFoundException cnfe){
            System.out.println("ERROR, CAN NOT CONNECT!!");
            System.out.println("Class");
            System.out.println("ERROR MESSAGE-> "+cnfe);
            System.exit(0);
        }// end of the first catch block
        catch(SQLException sqle){
            System.out.println("ERROR SQLException in connect()");
            System.out.println("ERROR MESSAGE -> "+sqle);
            // sqle.printStackTrace();
            System.exit(0);
        }//end of  Second catch block

        System.out.println("Connection Successful: " + (conn!=null));
        return (conn!=null);
    } // End of connect method


    /**
     Close the Connection to the Database
     */
    public void close(){
        try {
            //rs.close();
            //stmt.close();
            conn.close();
        }
        catch(SQLException sqle){
            System.out.println("ERROR IN METHOD close()");
            System.out.println("ERROR MESSAGE -> "+sqle);
        }// end of catch
    }//end of method close


   
//here password is actually the passworsd hash passed from the pres layer
    public boolean login(String username, String password) {
        try{
            stmt = conn.createStatement();
            String query = "SELECT password FROM account WHERE username LIKE '" + username + "'";
            rs = stmt.executeQuery(query);
            rs.next();
            if(password.equals(rs.getString(1))){
                this.username = username;
                return true;
            }else{
                return false;
            }
        }
        catch(Exception e){
            System.out.println("Error logging in: " + e);
        }
        return false;
    }

    public int insertStudentInterest(String interest) {
        // the username of the person logged in is a field
        int result = 0;

        try {
            PreparedStatement stmt2;

            stmt2 = conn.prepareStatement("INSERT INTO student_interests(student_ID, interest)  VALUES (?,?)");
            stmt2.setString(1, username);
            stmt2.setString(2, interest);


            result = stmt2.executeUpdate();     // Performs the update command

        }
        catch (Exception e) {
            result = 0;
            System.out.println("Error inserting or closeing");
            System.out.println("Error: " + e );
            e.printStackTrace();
        }

        return result;
    }

    public int insertFacultyInterest(String interest) {
        // the username of the person logged in is a field
        int result = 0;

        try {
            PreparedStatement stmt2;

            stmt2 = conn.prepareStatement("INSERT INTO faculty_interests(faculty_ID, interest)  VALUES (?,?)");
            stmt2.setString(1, username);
            stmt2.setString(2, interest);


            result = stmt2.executeUpdate();     // Performs the update command

        }
        catch (Exception e) {
            result = 0;
            System.out.println("Error inserting or closing");
            System.out.println("Error: " + e );
            e.printStackTrace();
        }

        return result;
    }

    public int insertAbstract(String title, String body) {
        // the username of the person logged in is a field
        int result = 0;

        try {
            PreparedStatement stmt2;

            stmt2 = conn.prepareStatement("INSERT INTO faculty_abstract(faculty_ID, title, content)  VALUES (?,?, ?)");
            stmt2.setString(1, username);
            stmt2.setString(2, title);
            stmt2.setString(3, body);


            result = stmt2.executeUpdate();     // Performs the update command

        }
        catch (Exception e) {
            result = 0;
            System.out.println("Error inserting or closing");
            System.out.println("Error: " + e );
            e.printStackTrace();
        }

        return result;
    }

    public String findMatch(String term, String typeOfUser) {
        // the username of the person logged in is a field

        String data= "";

        try {

            String query = "{CALL MatchStudentProfessor(?, ?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, username);
            stmt.setString(2, term);

            ResultSet rs = stmt.executeQuery();

            // if nothing was returned pass empty string

            data  = "\nMatches\n";

            // Test (condition of while loop) if there are query results
            while (rs.next()) {
                if (typeOfUser.equals("PROF")) {
                    String name  = rs.getString(1);
                    String email  = rs.getString(2);
                    data += name + "\t\t" + email + "\n";
                }
                else if (typeOfUser.equals("STUDENT")) {
                    String name  = rs.getString(1);
                    String email  = rs.getString(2);
                    String building  = rs.getString(3);
                    String office  = rs.getString(4);
                    data += name + "\t\t" + email + "\t\t" + building + "\t\t" + office + "\n";
                }


            }// end of while loop, end of processing the result set

            if (data.equals("\nMatches\n")) {
                return "";
            }
        }//end of try
        catch (SQLException sqle) {
            System.out.println("\nERROR CAN NOT getResultSet()");
            System.out.println("ERROR MESSAGE-> " + sqle + "\n");
            sqle.printStackTrace();
        }// end of catch

        return data;
    }
    
    
    public String searchStudents(String term) {

        String data= "";

        try {

            String query = "{CALL SearchStudents(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, term);

            ResultSet rs = stmt.executeQuery();

            // if nothing was returned pass empty string

            data  = "\nMatches\n";

            // Test (condition of while loop) if there are query results
            while (rs.next()) {

                    String name  = rs.getString(1);
                    String email  = rs.getString(2);
                    data += name + "\t\t" + email + "\n";

            }// end of while loop, end of processing the result set

            if (data.equals("\nMatches\n")) {
                return "";
            }
        }//end of try
        catch (SQLException sqle) {
            System.out.println("\nERROR CAN NOT getResultSet()");
            System.out.println("ERROR MESSAGE-> " + sqle + "\n");
            sqle.printStackTrace();
        }// end of catch

        return data;
    }
    
    public String searchProfessors(String term) {

        String data= "";

        try {

            String query = "{CALL SearchProfessors(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, term);

            ResultSet rs = stmt.executeQuery();

            // if nothing was returned pass empty string

            data  = "\nMatches\n";

            // Test (condition of while loop) if there are query results
            while (rs.next()) {

                    String name  = rs.getString(1);
                    String email  = rs.getString(2);
                    String building  = rs.getString(3);
                    String office  = rs.getString(4);
                    data += name + "\t\t" + email + "\t\t" + building + "\t\t" + office + "\n";

            }// end of while loop, end of processing the result set

            if (data.equals("\nMatches\n")) {
                return "";
            }
        }//end of try
        catch (SQLException sqle) {
            System.out.println("\nERROR CAN NOT getResultSet()");
            System.out.println("ERROR MESSAGE-> " + sqle + "\n");
            sqle.printStackTrace();
        }// end of catch

        return data;
    }
    
    
    
    
   //Adding a student record
   public int addStudent(String username, String password, String name, String email) {
            int rows = 0;

            this.username = username;

            try{
               // prepared statement
               String sql = "INSERT INTO account VALUES (?,?, 'S')";
   
               PreparedStatement stmt = conn.prepareStatement(sql);
               // bind values into the parameters
               stmt.setString(1, username);
               stmt.setString(2, password);
               rows = stmt.executeUpdate();

                sql = "INSERT INTO student VALUES (?,?,?)";

                stmt = conn.prepareStatement(sql);
                // bind values into the parameters
                stmt.setString(1, username);
                stmt.setString(2, name);
                stmt.setString(3, email);
                rows += stmt.executeUpdate();

            }//try
            catch(SQLException sqle){
               System.out.println("SQL ERROR");
               System.out.println("INSERT FAILED!!!!");
               System.out.println("ERROR MESSAGE IS -> "+sqle);
               sqle.printStackTrace();
               return(0);
            }
            catch(Exception e) {
               System.out.println("Error occured in addPassenger method");
               System.out.println("ERROR MESSAGE is -> "+e);
               e.printStackTrace();
               return(0);
            }
            return (rows);
      } 

   //Adding a faculty record
   public int addFaculty(String username, String password, String name, String phone, String email, String building, String office) {
            int rows = 0;

            this.username = username;

            try{
                // prepared statement
                String sql = "INSERT INTO account VALUES (?,?, 'F')";

                PreparedStatement stmt = conn.prepareStatement(sql);
                // bind values into the parameters
                stmt.setString(1, username);
                stmt.setString(2, password);
                rows = stmt.executeUpdate();

               // prepared statement
                sql = "INSERT INTO faculty VALUES (?,?,?,?,?,?)";
   
                stmt = conn.prepareStatement(sql);
               // bind values into the parameters
               stmt.setString(1, username);
               stmt.setString(2, name);
               stmt.setString(3, phone);
               stmt.setString(4, email);
               stmt.setString(5, building);
               stmt.setString(6, office);
               rows += stmt.executeUpdate();

            }//try
            catch(SQLException sqle){
               System.out.println("SQL ERROR");
               System.out.println("INSERT FAILED!!!!");
               System.out.println("ERROR MESSAGE IS -> "+sqle);
               sqle.printStackTrace();
               return(0);
            }
            catch(Exception e) {
               System.out.println("Error occured in addPassenger method");
               System.out.println("ERROR MESSAGE is -> "+e);
               e.printStackTrace();
               return(0);
            }
            return (rows);
      } 



} // End of Class   MySQLDatabase.java