//TODO sistemare il tipo Date col tipo Calendar
//TODO Nomefonte--->ID_Fonte
package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.*;

public class Contenuto{
	private int IDPost;
	private int IDTopic;
	
	
	public Contenuto(int idpost, int idtopic){
		this.IDPost = idpost;
		this.IDTopic = idtopic;
	}//end costruttore base
	
	public Contenuto(ResultSet resultSet){
		try{
		this.IDPost = resultSet.getInt("IDPost");
		this.IDTopic = resultSet.getInt("IDTopic");
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}//end costruttore da resultSet
	
	
	public Contenuto(String json){
		//TODO controllare il try/catch	
			JSONObject obj = new JSONObject(json);
			try{
				this.IDPost = obj.getInt("IDPost");
				this.IDTopic = obj.getInt("IDTopic");

			}
			catch(Exception e){e.getStackTrace();}
			
			
		}//end costruttore da stringa
		
	
	
	public String toJson(){
		return "{\"IDPost\":\""+this.IDPost+"\","+
				"\"IDTopic\":\""+this.IDTopic+"\",}";
	}//end toJson
	
	/*
	 public String toXML(){
	 
		return "<Contenuto>\n"+
		"<IDPost>"+this.IDPost+"</IDPost>\n"+
		"<IDTopic>"+ this.IDTopic + "</IDTopic>\n"+
		
		"</Contenuto>\n";
	}//end toXML
	*/
	
	
	public int getIDPost(){return this.IDPost;}
	public void setIDPost(int id){this.IDPost = id;}

	public int getIDTopic(){return this.IDTopic;}
	public void setIDTopic(int id){this.IDTopic = id;}
	
	
	
}//end class