//Curtis Hynes - Movie Collection application

import java.io.IOException;
import java.util.Scanner;
import java.sql.*;

public class Movie {

	//movie object instance variables 
	private String title, year, director, actor;
	private int rating;
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/movies";

	//Database credentials
	static final String USER = "Curtis";
	static final String PASS = "528491Dal";
	
	//scanner to be used for user input
	private static Scanner input;
	
	Movie()
	{}
	
	//Movie object constructor, each movie has a 
	//title, release year, director, top billed actor, 
	//and a rating out of 10.
	Movie(String t, String y, String D, String A, int r)
	{
		title = t;
		year = y;
		director = D;
		actor = A;
		rating = r;
	}
	
	//set methods for movie instance variables
	public void setTitle(String t)
	{
		title = t;
	}
	
	public void setYear(String y)
	{
		year = y;
	}
	
	public void setDirector(String d)
	{
		director = d;
	}
	
	public void setActor(String a)
	{
		actor = a;
	}
	
	public void setRating(int r)
	{
		rating = r;
	}
	
	//get methods for instance variables
	public String getTitle()
	{
		return title;
	}
	
	public String getYear()
	{
		return year;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public String getActor()
	{
		return actor;
	}

	public int getRating()
	{
		return rating;
	}
	
	public String toString()
	{
		 return "Title: " + getTitle() + "\nReleased: " + getYear() + "\nDirector: " + getDirector() + "\nTop Billed: " + getActor() + "\nMy Rating: " + getRating();
	}

	
	//addMovie() method creates a movie object using user input
	//and adds its variables into the movieTable Table in a mySQL
	//data base.
	public static void addMovie() throws IOException
	{
		Movie m1 = new Movie();
		input = new Scanner(System.in);
		
		System.out.println("Enter the name of the movie: ");
		m1.setTitle(input.nextLine());
		
		System.out.println("Enter the realease year: ");
		m1.setYear(input.nextLine());
		
		System.out.println("Enter the name of the movie's Director: ");
		m1.setDirector(input.nextLine());
		
		System.out.println("Enter the top billed actor: ");
		m1.setActor(input.nextLine());
		
		System.out.println("Enter your rating out of 10 : ");
		int rating = input.nextInt();
		
		//ensures ratings stay in the given scale.
		if(rating > 10)
			m1.setRating(10);
		else 
			m1.setRating(rating); 
		
		//--------add movie to the database------
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try{
		      //Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      System.out.println("Connected database successfully...");
		      
		      //Execute the insert query
		      stmt = conn.prepareStatement("INSERT INTO movieTable(title, year, director, actor, rating)VALUES (?,?,?,?,?)");
		      stmt.setString(1, m1.getTitle());
		      stmt.setString(2, m1.getYear());
		      stmt.setString(3, m1.getDirector());
		      stmt.setString(4, m1.getActor());
		      stmt.setInt(5, m1.getRating());
		      
		      stmt.executeUpdate();
		  
		      System.out.println("Movie added sucessfully.");

		   }
			catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		    }
			catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		    }
			finally{
		      //finally block used to close resources
		      try{
		         if(stmt != null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn != null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		
		
		//checks if the user wants to add another movie into the database
		boolean gaveAnswer = false;
		while(gaveAnswer == false)
		{	
			System.out.println("Add another movie? Yes or No");
			String choice = input.next();
			
			if(choice.equalsIgnoreCase("No"))
			{
				gaveAnswer = true;
				displayMenu();
			}
			
			else if(choice.equalsIgnoreCase("Yes"))
			{
				gaveAnswer = true;
				System.out.println("Sounds good! \n");
				addMovie();
			}
			
			else
				System.out.println("\n\"I'm sorry Dave I can't do that\" - Error: Invalid command. \nSo will you... ");
		}
	}
	
	
	//deleteMovie is a method that removes the movie record that corresponds
	//to the title and year that the user provides
	public static void deleteMovie() throws IOException
	{
		input = new Scanner(System.in);
		String deleteTitle, deleteYear;
		
		System.out.println("Please enter the title of the movie you would like to delete.");
		deleteTitle = input.nextLine();
		System.out.println("Please enter the year of release for the movie you would like to delete.");
		deleteYear = input.nextLine();
		
		//--------delete movie from the database------
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try{
		      //Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      System.out.println("Connected database successfully...");
		      
		      //Execute the insert query
		      stmt = conn.prepareStatement("Delete FROM movieTable WHERE title = ? AND year = ?");
		      stmt.setString(1, deleteTitle);
		      stmt.setString(2, deleteYear);
		      
		      stmt.executeUpdate();
		  
		      System.out.println("Deleted Movie from the table.");

		   }
			catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		    }
			catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		    }
			finally{
		      //finally block used to close resources
		      try{
		         if(stmt != null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn != null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		
		
		//checks if the user wants to remove another movie from the database
		boolean gaveAnswer = false;
		while(gaveAnswer == false)
		{	
			System.out.println("remove another movie? Yes or No");
			String choice = input.next();
			
			if(choice.equalsIgnoreCase("No"))
			{
				gaveAnswer = true;
				displayMenu();
			}
			
			else if(choice.equalsIgnoreCase("Yes"))
			{
				gaveAnswer = true;
				System.out.println("Sounds good! \n");
				deleteMovie();
			}
			
			else
				System.out.println("\n\"I'm sorry Dave I can't do that\" - Error: Invalid command. \nSo will you... ");
		}
	}
	
	
	public static void printData() throws IOException
	{
		Connection conn = null;
		Statement stmt = null;
				
		try{
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");
				     
			stmt = conn.createStatement();
			String sql = "SELECT title, year, director, actor, rating FROM movieTable WHERE title = 'Zodiac'";
			ResultSet r = stmt.executeQuery(sql);
				     
			System.out.println("\n==The Current contents of The Movie Database==\n");
			while(r.next()){	          
				//Retrieve by column name
				String title  = r.getString("title");
				String year = r.getString("year");
				String director = r.getString("director");
				String actor = r.getString("actor");
				int rating = r.getInt("rating");
				          
				//Display values
				System.out.print("Title: " + title + "\n");
				System.out.print("Year: " + year + "\n");
				System.out.print("Director: " + director + "\n");
				System.out.print("Actor: " + actor + "\n");
				System.out.println("Rating: " + rating);
				System.out.println("-------------------------------------");
			}
			r.close();
			}
			catch(SQLException se){
			//Handle errors for JDBC
				se.printStackTrace();
			}
			catch(Exception e){
			//Handle errors for Class.forName
				e.printStackTrace();
			}
			finally{
			//finally block used to close resources
				try{
					if(stmt != null)
				    conn.close();
					}
					catch(SQLException se){}// do nothing
				    	try{
				    		if(conn != null)
				    			conn.close();
				   }
				    catch(SQLException se){
				         se.printStackTrace();
				      }//end finally try
				   }//end try
	}
	
	public static void displayMenu() throws IOException
	{
		String answer;
		
		System.out.println("--Main Menu--");
		System.out.println("Enter 'Add', 'Delete', 'Print' or 'Exit':");
		
		input = new Scanner(System.in);
		answer = input.nextLine();
		if(answer.equalsIgnoreCase("Add"))
			addMovie();
		else if(answer.equalsIgnoreCase("Delete"))
			deleteMovie();
		else if(answer.equalsIgnoreCase("Print"))
			printData();
		else if(answer.equalsIgnoreCase("Exit"))
		{
			System.out.println("Good Bye");
			System.exit(0);
		}
		
		else
		{
			System.out.println("invalid input.");
			displayMenu();
		}
	}
	
}
	