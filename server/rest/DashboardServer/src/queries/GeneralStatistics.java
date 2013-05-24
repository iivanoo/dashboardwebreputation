//restituisce una stringa json contenente il numero dei post e l'indice di gradimento della determinata fonte in iun intervallo di date per la fonte cliccata a prescindere dal topic
package queries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


//import entities.Sorgenti;

import db.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/Sorgenti/grafico/general/nomefonte={Nome_fonte}/data1={Data1}/data2={Data2}/data3={Data3}/data4={Data4}/data5={Data5}/data6={Data6}/data7={Data7}")  //Data1 < Data2 & formato: AAAA-MM-GG
public class GeneralStatistics{

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGeneralStatistics(@PathParam("Nome_fonte") String nome, @PathParam("Data1") String d1, @PathParam("Data2") String d2, @PathParam("Data3") String d3, @PathParam("Data4") String d4, @PathParam("Data5") String d5, @PathParam("Data6") String d6, @PathParam("Data7") String d7) {
		//String result = "[";
		String result = "";
		try {
			
			result = this.getSorgenti(nome,d1,d2, d3,d4,d5,d6,d7);
			
			
		}
		catch (Exception exc) {
			result = "{\"error\":{\"text\":" + exc.getMessage() + "}}";
			System.out.println("Error: "+ exc.getMessage());
		}
		return result;
	}

	
	private String getSorgenti(String nome, String d1, String d2, String d3, String d4, String d5, String d6, String d7) throws Exception {
		int count;
		Connection conn;
		Statement stmt;
		ResultSet rset;
		Connection conn_pos;
		Statement stmt_pos;
		ResultSet rset_pos;
		Connection conn_neg;
		Statement stmt_neg;
		ResultSet rset_neg; 
		
		
		//prima settimana----------------------------------------------------------------------
		conn = DbManager.getConnection();
		stmt = conn.createStatement();
		rset = stmt.executeQuery("SELECT Post.ID AS label FROM Post INNER JOIN Sorgenti WHERE Post.Data BETWEEN '"+d1+"' AND '"+d2+"' AND Post.ID_Fonte = Sorgenti.ID AND Sorgenti.Nome = '"+nome+"';");
		count = 0;
		while (rset.next()) {
			count++;
		
		}
		int numeropost1=count;
		rset.close();
		stmt.close();
		conn.close();
	
		List<Integer> neg1 = new ArrayList<Integer>();
		conn_neg = DbManager.getConnection();
		stmt_neg = conn_neg.createStatement();
		rset_neg = stmt_neg.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '-1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d1+"' AND '"+d2+"';");
		int count_neg = 0;
		while (rset_neg.next()) {
			count_neg++;
			neg1.add(count_neg);
			/*
			String st = rset.getObject("label").toString();
			sorgenti.add(Integer.parseInt(st));*/
		}
		
		rset_neg.close();
		stmt_neg.close();
		conn_neg.close();
		
		List<Integer> pos1 = new ArrayList<Integer>();
		conn_pos = DbManager.getConnection();
		stmt_pos = conn_pos.createStatement();
		rset_pos = stmt_pos.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '+1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d1+"' AND '"+d2+"';");
		int count_pos = 0;
		while (rset_pos.next()) {
			count_pos++;
			pos1.add(count_pos);
			/*
			String st = rset.getObject("label").toString();
			sorgenti.add(Integer.parseInt(st));*/
		}
		
		rset_pos.close();
		stmt_pos.close();
		conn_pos.close();	
		int gradimento1 = 0;
		if(numeropost1!=0){
			if(pos1.size()+neg1.size()==numeropost1 && (neg1.size()<pos1.size())){
				gradimento1 = 100*pos1.size()/numeropost1;
			}
			else{gradimento1 = 100*(pos1.size()-neg1.size())/numeropost1;};
		};
	
	
	//seconda settimana----------------------------------------------------------------------
	conn = DbManager.getConnection();
	stmt = conn.createStatement();
	rset = stmt.executeQuery("SELECT Post.ID AS label FROM Post INNER JOIN Sorgenti WHERE Post.Data BETWEEN '"+d2+"' AND '"+d3+"' AND Post.ID_Fonte = Sorgenti.ID AND Sorgenti.Nome = '"+nome+"';");
	count = 0;
	
	while (rset.next()) {
		count++;
	
	}
	
	int numeropost2=count;
	rset.close();
	stmt.close();
	conn.close();

	List<Integer> neg2 = new ArrayList<Integer>();
	conn_neg = DbManager.getConnection();
	stmt_neg = conn_neg.createStatement();
	rset_neg = stmt_neg.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p  WHERE s.ID = p.ID_Fonte AND p.Polarity = '-1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d2+"' AND '"+d3+"';");
	 count_neg = 0;
	while (rset_neg.next()) {
		count_neg++;
		neg2.add(count_neg);
		/*
		String st = rset.getObject("label").toString();
		sorgenti.add(Integer.parseInt(st));*/
	}
	
	rset_neg.close();
	stmt_neg.close();
	conn_neg.close();
	
	List<Integer> pos2 = new ArrayList<Integer>();
	conn_pos = DbManager.getConnection();
	stmt_pos = conn_pos.createStatement();
	rset_pos = stmt_pos.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '+1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d2+"' AND '"+d3+"';");
	count_pos = 0;
	while (rset_pos.next()) {
		count_pos++;
		pos2.add(count_pos);
		/*
		String st = rset.getObject("label").toString();
		sorgenti.add(Integer.parseInt(st));*/
	}
	
	rset_pos.close();
	stmt_pos.close();
	conn_pos.close();	
	
	
	int gradimento2=0;
	if(numeropost2!=0){
		if(pos2.size()+neg2.size()==numeropost2 && (neg2.size()<pos2.size())){
			gradimento2 = 100*pos2.size()/numeropost2;
		}
		else{gradimento2 = 100*(pos2.size()-neg2.size())/numeropost2;};
	};

//terza settimana----------------------------------------------------------------------
conn = DbManager.getConnection();
stmt = conn.createStatement();
rset = stmt.executeQuery("SELECT Post.ID AS label FROM Post INNER JOIN Sorgenti WHERE Post.Data BETWEEN '"+d3+"' AND '"+d4+"' AND Post.ID_Fonte = Sorgenti.ID AND Sorgenti.Nome = '"+nome+"';");
count = 0;

while (rset.next()) {
	count++;

}

int numeropost3=count;
rset.close();
stmt.close();
conn.close();

List<Integer> neg3 = new ArrayList<Integer>();
conn_neg = DbManager.getConnection();
stmt_neg = conn_neg.createStatement();
rset_neg = stmt_neg.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '-1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d3+"' AND '"+d4+"';");
 count_neg = 0;
while (rset_neg.next()) {
	count_neg++;
	neg3.add(count_neg);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_neg.close();
stmt_neg.close();
conn_neg.close();

List<Integer> pos3 = new ArrayList<Integer>();
conn_pos = DbManager.getConnection();
stmt_pos = conn_pos.createStatement();
rset_pos = stmt_pos.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '+1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d3+"' AND '"+d4+"';");
count_pos = 0;
while (rset_pos.next()) {
	count_pos++;
	pos3.add(count_pos);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_pos.close();
stmt_pos.close();
conn_pos.close();	

int gradimento3 =0;
if(numeropost3!=0){
	if(pos3.size()+neg3.size()==numeropost3 && (neg3.size()<pos3.size())){
		gradimento3 = 100*pos3.size()/numeropost3;
	}
	else{gradimento3 = 100*(pos3.size()-neg3.size())/numeropost3;};
};


//quarta settimana----------------------------------------------------------------------
conn = DbManager.getConnection();
stmt = conn.createStatement();
rset = stmt.executeQuery("SELECT Post.ID AS label FROM Post INNER JOIN Sorgenti WHERE Post.Data BETWEEN '"+d4+"' AND '"+d5+"' AND Post.ID_Fonte = Sorgenti.ID AND Sorgenti.Nome = '"+nome+"';");
count = 0;

while (rset.next()) {
	count++;

}

int numeropost4=count;
rset.close();
stmt.close();
conn.close();

List<Integer> neg4 = new ArrayList<Integer>();
conn_neg = DbManager.getConnection();
stmt_neg = conn_neg.createStatement();
rset_neg = stmt_neg.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '-1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d4+"' AND '"+d5+"';");
count_neg = 0;
while (rset_neg.next()) {
	count_neg++;
	neg4.add(count_neg);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_neg.close();
stmt_neg.close();
conn_neg.close();

List<Integer> pos4 = new ArrayList<Integer>();
conn_pos = DbManager.getConnection();
stmt_pos = conn_pos.createStatement();
rset_pos = stmt_pos.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p  WHERE s.ID = p.ID_Fonte AND p.Polarity = '+1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d4+"' AND '"+d5+"';");
count_pos = 0;
while (rset_pos.next()) {
	count_pos++;
	pos4.add(count_pos);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_pos.close();
stmt_pos.close();
conn_pos.close();	

int gradimento4=0;
if(numeropost4!=0){
	if(pos4.size()+neg4.size()==numeropost4 && (neg4.size()<pos4.size())){
		gradimento4 = 100*pos4.size()/numeropost4;
	}
	else{gradimento4 = 100*(pos4.size()-neg4.size())/numeropost4;};
};




//quinta settimana----------------------------------------------------------------------
conn = DbManager.getConnection();
stmt = conn.createStatement();
rset = stmt.executeQuery("SELECT Post.ID AS label FROM Post INNER JOIN Sorgenti WHERE Post.Data BETWEEN '"+d5+"' AND '"+d6+"' AND Post.ID_Fonte = Sorgenti.ID AND Sorgenti.Nome = '"+nome+"';");
count = 0;

while (rset.next()) {
	count++;

}

int numeropost5=count;
rset.close();
stmt.close();
conn.close();

List<Integer> neg5 = new ArrayList<Integer>();
conn_neg = DbManager.getConnection();
stmt_neg = conn_neg.createStatement();
rset_neg = stmt_neg.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '-1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d5+"' AND '"+d6+"';");
count_neg = 0;
while (rset_neg.next()) {
	count_neg++;
	neg5.add(count_neg);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_neg.close();
stmt_neg.close();
conn_neg.close();

List<Integer> pos5 = new ArrayList<Integer>();
conn_pos = DbManager.getConnection();
stmt_pos = conn_pos.createStatement();
rset_pos = stmt_pos.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '+1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d5+"' AND '"+d6+"';");
count_pos = 0;
while (rset_pos.next()) {
	count_pos++;
	pos5.add(count_pos);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_pos.close();
stmt_pos.close();
conn_pos.close();	
int gradimento5=0;
if(numeropost5!=0){
	if(pos5.size()+neg5.size()==numeropost5 && (neg5.size()<pos5.size())){
		gradimento5 = 100*pos5.size()/numeropost5;
	}
	else{gradimento5 = 100*(pos5.size()-neg5.size())/numeropost5;};
};
//sesta settimana----------------------------------------------------------------------
conn = DbManager.getConnection();
stmt = conn.createStatement();
rset = stmt.executeQuery("SELECT Post.ID AS label FROM Post INNER JOIN Sorgenti WHERE Post.Data BETWEEN '"+d6+"' AND '"+d7+"' AND Post.ID_Fonte = Sorgenti.ID AND Sorgenti.Nome = '"+nome+"';");
count = 0;

while (rset.next()) {
	count++;

}

int numeropost6=count;
rset.close();
stmt.close();
conn.close();

List<Integer> neg6 = new ArrayList<Integer>();
conn_neg = DbManager.getConnection();
stmt_neg = conn_neg.createStatement();
rset_neg = stmt_neg.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '-1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d6+"' AND '"+d7+"';");
count_neg = 0;
while (rset_neg.next()) {
	count_neg++;
	neg6.add(count_neg);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_neg.close();
stmt_neg.close();
conn_neg.close();

List<Integer> pos6 = new ArrayList<Integer>();
conn_pos = DbManager.getConnection();
stmt_pos = conn_pos.createStatement();
rset_pos = stmt_pos.executeQuery("SELECT p.Polarity FROM Sorgenti AS s INNER JOIN Post AS p WHERE s.ID = p.ID_Fonte AND p.Polarity = '+1' AND s.Nome = '"+nome+"' AND p.Data BETWEEN '"+d6+"' AND '"+d7+"';");
count_pos = 0;
while (rset_pos.next()) {
	count_pos++;
	pos6.add(count_pos);
	/*
	String st = rset.getObject("label").toString();
	sorgenti.add(Integer.parseInt(st));*/
}

rset_pos.close();
stmt_pos.close();
conn_pos.close();	
int gradimento6=0;
if(numeropost6!=0){
	if(pos6.size()+neg6.size()==numeropost6 && (neg6.size()<pos6.size())){
		gradimento6 = 100*pos6.size()/numeropost6;
	}
	else{gradimento6= 100*(pos6.size()-neg6.size())/numeropost6;};
};
	

	
	String resu ="{\"nomefonte\":\""+nome+"\",\"settimana1\":{\"numeropost\":\""+numeropost1+"\",\"gradimento\":\""+gradimento1+"\"},\"settimana2\":{\"numeropost\":\""+numeropost2+"\",\"gradimento\":\""+gradimento2+"\"},\"settimana3\":{\"numeropost\":\""+numeropost3+"\",\"gradimento\":\""+gradimento3+"\"},\"settimana4\":{\"numeropost\":\""+numeropost4+"\",\"gradimento\":\""+gradimento4+"\"},\"settimana5\":{\"numeropost\":\""+numeropost5+"\",\"gradimento\":\""+gradimento5+"\"},\"settimana6\":{\"numeropost\":\""+numeropost6+"\",\"gradimento\":\""+gradimento6+"\"}}  ";
	//{"nomefonte" : " ", "settimana0" : {"numeropost" : "  ","gradimento":"   "},"settimana1" : {"numeropost" : "  ","gradimento":"   "},"settimana2" : {"numeropost" : "  ","gradimento":"   "},"settimana3" : {"numeropost" : "  ","gradimento":"   "},"settimana4" : {"numeropost" : "  ","gradimento":"   "},"settimana5" : {"numeropost" : "  ","gradimento":"   "}};
	
		
		
		
		

		
		return resu;
	}
	
}