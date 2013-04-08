package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

public class Topics{
	private int ID;
	private String Topic;
	
	public Topics(int id, String topic){
		this.ID = id;
		this.Topic = topic;
	} //end costruttore base
	
	public Topics(ResultSet resultSet){
		try{
		this.ID = resultSet.getInt("ID");
		this.Topic = resultSet.getString("Topic");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}//end costruttore da resultSet
	
	public Topics(String json){
		//TODO controllare il try/catch	
			JSONObject obj = new JSONObject(json);
			try{
				this.ID = obj.getInt("ID");
				this.Topic = obj.getString("Topic");
				
			}
			catch(Exception e){e.getStackTrace();}
			
			
		}//end costruttore da stringa
		
	
	
	public String toJson(){
		return "{\"ID\":\""+this.ID+"\","+
				"\"Topic\":\""+this.Topic+"\"}";
	} //end toJson
	
	/*
	 public String toXML(){
		return "<Topics>\n"+
				"<ID>"+this.ID+"</ID>\n"+
				"<Topic>"+this.Topic+"</Topic>\n"+
				"</Topics>\n";
	}//end toXML
*/
	
	
	public int getID(){return this.ID;}
	public void setID(int id){ this.ID = id;}
	
	public String getTopic(){return this.Topic;}
	public void setTopic(String topic){ this.Topic = topic;}
}