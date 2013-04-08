//TODO sistemare il tipo Date col tipo Calendar
//TODO Nomefonte--->ID_Fonte
package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.*;

public class Post{
	private int ID;
	private String Link;
	private Date Data;
	private double Polarity;
	private int ID_Fonte;
	private String Text;
	
	public Post(int id, String link, Date data, double pol, int ID_Fonte, String testo){
		this.ID = id;
		this.Link = link;
		this.Data = data;
		this.Polarity = pol;
		this.ID_Fonte = ID_Fonte;
		this.Text = testo;
	}//end costruttore base
	
	public Post(ResultSet resultSet){
		try{
		this.ID = resultSet.getInt("ID");
		this.Link = resultSet.getString("Link");
		this.Data = resultSet.getDate("Data");
		this.Polarity = resultSet.getDouble("Polarity");
		this.ID_Fonte = resultSet.getInt("ID_Fonte");
		this.Text = resultSet.getString("Text");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}//end costruttore da resultSet
	
	
	public Post(String json){
		//TODO controllare il try/catch	
			JSONObject obj = new JSONObject(json);
			try{
				this.ID = obj.getInt("ID");
				this.Link = obj.getString("Link");

				this.Data = new Date(obj.getString("Data"));
				this.Polarity = obj.getDouble("Polarity");
				this.ID_Fonte = obj.getInt("ID_Fonte");
				this.Text = obj.getString("Text");
			}
			catch(Exception e){e.getStackTrace();}
			
			
		}//end costruttore da stringa
		
	
	
	public String toJson(){
		return "{\"ID\":\""+this.ID+"\","+
				"\"Link\":\""+this.Link+"\","+
				"\"Data\":\""+this.Data+"\","+
				"\"Polarity\":\""+this.Polarity+"\","+
				"\"ID_Fonte\":\""+this.ID_Fonte+"\","+
				"\"Text\":\""+this.Text+"\",}";
	}//end toJson
	
	
	/*
	public String toXML(){
		return "<Post>\n"+
		"<ID>"+this.ID+"</ID>\n"+
		"<Link>"+ this.Link + "</Link>\n"+
		"<Data>" + this.Data + "</Data>\n"+
		"<Polarity>" + this.Polarity + "</Polarity>\n"+
		"<ID_Fonte>" + this.ID_Fonte + "</ID_Fonte>\n"+
		"<Text>" + this.Text + "</Text>\n"+
		"</Post>\n";
	}//end toXML
	*/
	
	
	public int getID(){return this.ID;}
	public void setID(int id){this.ID = id;}
	
	public String getLink(){return this.Link;}
	public void setLink(String link){this.Link = link;}
	
	public Date getData(){return this.Data;}
	public void setDate(Date data){this.Data = data;}
	
	public double getPolarity(){return this.Polarity;}
	public void setPolarity(double pol){this.Polarity = pol;}
	
	public int getID_Fonte(){return this.ID_Fonte;}
	public void setID_Fonte(int idfonte){this.ID_Fonte = idfonte;}
	
	public String getText(){return this.Text;}
	public void setText(String testo){this.Text = testo;}
	
	
	
}//end class