package Day5;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Music {

    public static void addsongs(PreparedStatement statement) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name of the song: ");
        String song=sc.nextLine();
        System.out.println("Movie of the song: ");
        String movie=sc.nextLine();
        System.out.println("Singer of the song: ");
        String singer=sc.nextLine();
        String favo;
        System.out.println("Is this song your favorite(t/f): ");
        favo=sc.nextLine();
        statement.setString(1,song);
        statement.setString(2,movie);
        statement.setString(3,singer);
        statement.setString(4,favo);
        int row=statement.executeUpdate();
        if(row>0)
        {
            System.out.println("Song added");
        }
        statement.close();
    }
    public static void playsong(Statement str,Test t) throws InterruptedException
    {
        String str1="SELECT * FROM song";
        try (ResultSet result = str.executeQuery(str1)) {
            while (result.next()) {
                String s=result.getString("song");
                String m=result.getString("movie");
                String sr=result.getString("singer");
                System.out.println("Song "+s+"\nMovie: "+m+"\nSinger: "+sr);
                t.some();
            }
            str.close();
        }
         catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public static void songdisplay(ResultSet rs,Test s) throws SQLException, InterruptedException
    {
    	while (rs.next()) {
            String name = rs.getString("song");
            String movie = rs.getString("movie");
            String singer = rs.getString("singer");
            System.out.println( "Song: " + name + "\nMovie:  "+ movie +"\nSinger: "+ singer);
            s.some();
        }
    }
    public static void sortedplay(Test s,Statement stmt) throws InterruptedException, SQLException {
        Scanner scan=new Scanner(System.in);
                int value;
                System.out.println("1.Song Sorted\n2. Movie Sorted\n 3.Singer Sorted");
                value=scan.nextInt();
                if(value==1)
                {
                    System.out.println("Songs play as whether ascending order or in descending order(1/0): ");
                    int option;
                    option=scan.nextInt();
                    if(option==1) {
                    	String query= "Select * from song ORDER by song ASC";
                        ResultSet rs = stmt.executeQuery(query);
                        songdisplay(rs,s);
                    }
                    else{
                    	String queri= "Select * from song ORDER by song DESC";
                        ResultSet result = stmt.executeQuery(queri);
                        songdisplay(result,s);
                       }
                }
                else if(value==2)
                 {
            System.out.println("Songs play as whether ascending order or in descending order(1/0): ");
            int option;
            option=scan.nextInt();
            if(option==1) {
            	String query= "Select * from song ORDER by movie ASC";
                ResultSet rs = stmt.executeQuery(query);
                songdisplay(rs,s);
            }
            else{
            	String queri= "Select * from song ORDER by movie DESC";
                ResultSet result = stmt.executeQuery(queri);
                songdisplay(result,s);
                }

            }
          else if (value==3)
        {
            System.out.println("Songs play as whether ascending order or in descending order(1/0): ");
            int option;
            option=scan.nextInt();
            if(option==1) {
            	String query= "Select * from song ORDER by singer ASC";
                ResultSet rs = stmt.executeQuery(query);
                songdisplay(rs,s);
            }
            else{
            	String queri= "Select * from song ORDER by singer DESC";
                ResultSet result = stmt.executeQuery(queri);
                songdisplay(result,s);
                }

            }
                
    }
    public static void randomplaylist(Test time,Statement stmt)
    {
        System.out.println("Enter your song name to play the song : ");
        String choice;
        Scanner scan=new Scanner(System.in);
        choice=scan.nextLine();
        try {
            System.out.println("Playing song: ");
            String queri= "Select * from song where song ='"+ choice+ "' ";
            ResultSet result = stmt.executeQuery(queri);
            songdisplay(result,time);
        }
        catch (Exception e)
        {
            System.out.println("Music doesn't found: "+e);
        }
    }
    public static void Playfavo(Test s,Statement stmt) throws InterruptedException
    {
    	 try {
             System.out.println("Playing song: ");
             String queri= "Select * from song where favo = 't' ";
             ResultSet result = stmt.executeQuery(queri);
             songdisplay(result,s);
         }
         catch (Exception e)
         {
             System.out.println("Music doesn't found: "+e);
         }
    }
    public static void main(String[] args) {
        try {

            String url = "jdbc:mysql://localhost:3306/music";
            String username = "root";
            String password = "Taehyung@BTS7";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");
            Scanner input = new Scanner(System.in);
            Test t = new Test();
            int choice = 0;
            while (choice != 6) {
                System.out.println("1.Add songs.\n2.Play songs.\n3. Sorted playlist.\n4. User's choice\n5. Play Favorite songs\n6. End of session");
                choice = input.nextInt();
                if (choice == 1) {
                    String option = "y";
                    while (option.equals("y")) {
                        String sql="INSERT INTO song (song, movie, singer, favo) VALUES (?, ?, ?, ?)";
                        PreparedStatement statement=connection.prepareStatement(sql);
                        addsongs(statement);
                        System.out.println("Want to add more songs(y/n): ");
                        option = input.next();
                    }
                 }else if (choice == 2) {
                    try {
                        System.out.println("Playing Songs");
                        Statement state=connection.createStatement();
                        playsong(state,t);
                    }
                    catch (InterruptedException e)
                    {
                        System.out.println("Exception occurs: "+e);
                    }
                } else if (choice == 3) {
                	try {
                    System.out.println("Sorted playlist");
                    Statement state=connection.createStatement();
                    sortedplay(t,state);
                    state.close();
                	}catch(Exception e)
                	{
                		System.out.println("Exception occurred");
                	}
                } else if (choice == 4) {
                	try {
                		  System.out.println("User's choice");
                        Statement state=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        randomplaylist(t,state);
                        state.close();
                    	}catch(Exception e)
                    	{
                    		System.out.println("Exception occurred");
                    	}
                }
            else if(choice==5)
                {
            	try {
            		 System.out.println("Playing favorite songs");
                  Statement state=connection.createStatement();
                  Playfavo(t,state);
                  state.close();
              	}catch(Exception e)
              	{
              		System.out.println("Exception occurred");
              	}
                   
                }
                else if(choice==6)
                {
                    System.out.println("Session Ends");
                    connection.close();
                }
                else if (choice > 6 || choice < 1) {
                    System.out.println("Enter the correct option");
                }
            }
            }
        catch(SQLException  e)
            {
                System.out.println("Oops, error");
                e.printStackTrace();
            }
        }
    }