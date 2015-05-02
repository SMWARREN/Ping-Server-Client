//Sean Warren
//3/1/2014
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class clientthread implements Runnable{ //implements Runnable makes this project multithreaded
	Socket socket=null;
	static ArrayList<String> Name = new ArrayList<String>(); // starts a hashmap for the arraylist Name
	static ArrayList<String> Number = new ArrayList<String>();//starts a hashmap for the arraylist Number
	static int counter=0; //sets the counter string as 0
	
	 private String Write(String name, String number) // starts a private write string to insert values into the hashmaps
	   {
	     String servercode="100 OK"; // sets the server side message for this function
	     while (counter>0) // only while the counter is greater than 0 carry out these actions
	     {
	       if (counter==0) // if counter is  0 do nothing, you just need this for syntax errors
	       {}
	     }
	     counter=1; //set the counter to 1
	     Name.add(name); // add inputed name into the name array
	     Number.add(number);//add number to the number array
	     counter=0; // set counter back to 0
	     
	     return servercode; // return servercode
	   }
	   
	      private String Remove(String name)// starts another private String to remove values from the hashmaps
	   {
	        String servercode2="300: Not Found"; // If the value u want to find is not there produce a server error
	     int r=Name.indexOf(name); // sets r to the index of the array
	       if (r>=0) // only start this function if the index of the array is greater than or equal to 0
	     {
	     while (counter>0)// only when the index is greater than or equal to 0 and the counter is greater than 0, continue with this function
	     {
	       if (counter==0)// if  the r value is greater than or equal to 0 and the counter = 0 do nothing..
	       {}
	     }
	     counter=1;
	     Name.remove(r); // remove the name from the arraylist
	     Number.remove(r);// remove the number from the arraylist
	     servercode2="100: OK"; //sets server code if the name was remove and it was found
	     counter=0; 
	     }
	     return servercode2; // uses either the first server code or uses the second depending on what happens
	   }
	   
	   private String Get(String name) //to get a user and there number
	   {
	     String servercode2=""; // initiate a value for servercode 2
	     int g=Name.indexOf(name); // g is = to the index of the names
	       if (g>=0) // if g is greater than or = to 0 get the number
	       { 
	         servercode2="200:" + (name) + Number.get(g);   // sets your server code 2 200 + gives you the number at the index.
	       }
	       else
	       {
	         servercode2="300: Number not found"; // name not found
	       }
	     return servercode2;
	   }
	
	public clientthread(Socket socket){// this applies the clienttrhead to each of the new users
		this.socket=socket;
	}

	protected void handleConnection() throws IOException
	{
		 String s=""; //sets the input string to nothing
	  
	  //to get input from the client
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	  //Out to the client -- Enable auto-flush
	  PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

     // prints out the basic information for this program
	  out.println("TalkServer for CS-430 by Prof. Sattar, To close connection type, END. To use Phonebook type [STORE, GET, REMOVE] a Phone Number");
	  //prints out the commands you are able to use

	 // outpuds to the the server someone has connected, this is important because we will have multi users
	  System.out.println("SERVER> " + socket.getInetAddress().getHostName() + ", connected to The server" +"\n");
	  
	


	 
	  while (true) //while the connection is truee
	  {       // get input from the client
		  if((s = in.readLine()).length() != 0) // if the user writes a line, meaning the lines doesnt = 0
		  {
			  s=s.toUpperCase(); // sets all of the inputs to uppercasee
			  System.out.println("Echoing: " + s); // echos what the user says to the server
			  
			  if (s.split("\\s+")[0].equals("END"))  // splits the input value and removes the trailing and leading whitespaces
              {
                System.out.println("Connection with " + (socket.getInetAddress().getHostName()) + "has ended"+"\n");
              // outputs to the server that the server is ended 
                break;
               }

			else if (s.split("\\s+")[0].equals("STORE"))// splits the input value and removes the trailing and leading whitespaces
			{
			if (s.split("\\s+").length>2) // if the length of store is greater then to, then move to the subroutine
			{
			s=Write(s.split("\\s+")[1], s.split("\\s+")[2]); // writes the splitted vaules to the the hashmaps
     }
     else
     {
       s="400: Bad Request"; // this is stating that the message wasnt understood
     }
			}
			else if (s.split("\\s+")[0].equals("REMOVE")) // if the user types remove it spluts the trailing and leading whitespaces
			{
			if (s.split("\\s+").length>1)// if the length is greater than 1 then move to the subroutine
			{
			s=Remove(s.split("\\s+")[1]); // remove the user given
			}
 else
 		{
	 		s="400: BAD REQUEST ";// this is stating that the message wasnt understood
 		}
			}
  else if (s.split("\\s+")[0].equals("GET")) // same as above
             {
    if ((s.split("\\s+").length>=2)) // length is greather then 2
    {
      s=Get(s.split("\\s+")[1]); // get the user
    }
    else 
    {
      s="400: BAD REQUEST"; // this is stating that the message wasnt understood
    }
  }

			  out.println( "SERVER > "+ s);     // output everything that was said                 
		  }
	  }
	  
	  socket.close(); // closes the socket, so its not always opens
	  System.exit(0);
	}


	@Override
	public void run() { // this extends from the clientthread
		try {
			handleConnection(); // using the runnable in the clientthread creates multithreading
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
