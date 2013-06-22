var dati;
$("#addutente").click(function(){
	$("#fonti_tab").css("display","none");
	$("#addutente").css("display","none");
	$("#reg_tab").css("display","inline");
});


var ut = readCookie("idutente");
if(readCookie("ad") == "0"){

	$("#g_utenti").css("display","none");
	$("#g_sorgenti").css("display","none");

}else{
	$("#g_utenti").css("display","inline");
	$("#g_sorgenti").css("display","inline");
};

$(document).ready(function(){
	fill_tabella();
	
	if(ut==null){window.location = "login.html";}
	else{
		
		
	    $.get("http://localhost:8080/DashboardServer/api/Utenti/id/id="+ut,function(dati){
	    	var dati = {utente : dati.Email};
	    	var struttura = document.getElementById("utente-template").innerHTML;
		    var template = Handlebars.compile(struttura);
		    var html = template(dati);
		    document.getElementById("etichetta-utente").innerHTML = html;
			});
		

		
	fill();
	};//end else
	}); //end ready index


      

function fill(){

	//var nomi= new Array();
	$.get("http://localhost:8080/DashboardServer/api/Accesso/search/utente="+ut, function(data) {
		
		
		for(var i=0; i<data.length; i++){
			
		
			 var fonti="";
			 for(var i=0; i<data.length;i++){fonti += "\""+data[i]+"\",";};
			 fonti = fonti.substring(0,fonti.length-1);
		
			 	var str = "{\"fontifill\" : ["+fonti+"]}";
			 
		
			 	
			 	dati = $.parseJSON(str); 
			 
			    var struttura = document.getElementById("el_fonti").innerHTML;
			    var template = Handlebars.compile(struttura);
			    var html = template(dati);
			    document.getElementById("ulbo").innerHTML = html;
		}//end for

	});//end get
	
	
}



$("#no").click(function(){
	$("#Pincopallino").css("display","none");
	$("#fonti_tab").css("display","inline");
});

if(readCookie("ad") == "1"){
	$("#g_utenti").css("display","none");
	$("#g_sorgenti").css("display","none");

}else{
	$("#g_utenti").css("display","inline");
	$("#g_sorgenti").css("display","inline");
};




function fill_tabella(){
	
	
	
	$.get("http://localhost:8080/DashboardServer/api/gestioneutenti",function(dati_json){
		dati = $.parseJSON(dati_json);

		 
	    var struttura = document.getElementById("el_sorg").innerHTML;
	    
	    var template = Handlebars.compile(struttura);
	    var html = template(dati);

	    $.get("http://localhost:8080/DashboardServer/api/Utenti",function(utenti_att){
	    	for(var x=0;x<utenti_att.length;x++){

	    		if(utenti_att[x].Attivo == "true"){document.getElementById(utenti_att[x].Email).checked = true;};
	    	};
	    });

	    document.getElementById("fonti_tab").innerHTML = html;
	    $("#ok").click(function(){
	        salva_modifiche();
	        });// end click save

	      
	      $("#tabella_permessi").ready(function(){
	      	
	      	var arr = new Array(dati);
	      	
	      	var ex = "#"+arr[0].selezione[0].utente+"$"+arr[0].selezione[0].fonti[0].nome;
	      	
	      	try{
	      		
	      		$.get("http://localhost:8080/DashboardServer/api/admin",function(bool){
	      			var json = $.parseJSON(bool);
	      			for(var x=0; x<json.length; x++){
	      				if(json[x].admin =="true"){
	      				document.getElementById(json[x].utente +"$admin").setAttribute("checked","checked");
	      				}
	      				};
	      		});//end getadmin

	      	}
	      	catch(err){};
	      });//end ready tabella_permessi
	      
	     

	      click_check();
//vanno all'interno della get altrimenti non sono bottoni interagibili
	      $("#save").click(function(){
	    	  
	    	  $("#Pincopallino").css("display","inline");
	    	  $("#fonti_tab").css("display","none");
	      });
	      $("#annulla").click(function(){window.location="index.html";});
	});
	

	function click_check(){
		$("input").click(function(){
			var thisinput = $(this)[0];
			if (thisinput.checked==true){
				thisinput.setAttribute("checked","");
				}
			else{
			thisinput.setAttribute("checked","checked");
				};
			
			
		});
	}

	function salva_modifiche(){
		var attivi = $(".att");
		for(var k=0;k<attivi.length;k++){
			if(attivi[k].checked){
				$.get("http://localhost:8080/DashboardServer/api/Utenti/attivazione/action=attiva/email="+attivi[k].id);
			}else{
				$.get("http://localhost:8080/DashboardServer/api/Utenti/attivazione/action=disattiva/email="+attivi[k].id);
			};
		};


		var item = $("input.pagina");

		for(var i=0; i<item.length; i++){  
			
			var email = item[i].id.split("$")[0];
			console.log(email);
			if(item[i].checked==true){
			var fonte = item[i].id.split("$")[1];
			
			if(fonte == "admin"){
				
				var url="http://localhost:8080/DashboardServer/api/admin/action=add/email="+email;
				$.get(url);
				console.log(url);
			}else
			{
				var email = item[i].id.split("$")[0];
				var fonte = item[i].id.split("$")[1];
				$.get("http://localhost:8080/DashboardServer/api/Accesso/mod/action=add/utente="+email+"/nomefonte="+fonte);
			};
			}
		else{
			var email = item[i].id.split("$")[0];
			var fonte = item[i].id.split("$")[1];
			if(fonte == "admin"){
				var url="http://localhost:8080/DashboardServer/api/admin/action=del/email="+email;
				$.get(url);
				console.log(url);
				
			}else{var pagina;
				var email = item[i].id.split("$")[0];
				var fonte = item[i].id.split("$")[1];
				$.get("http://localhost:8080/DashboardServer/api/Accesso/mod/action=del/utente="+email+"/nomefonte="+fonte);
			};
			
			};
		
		}//end for
	window.location = "gestione_utenti.html";
	}	

    
    
}//end function fill_tabella




