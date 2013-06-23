/*
 * Riempimento dell'index
 */
var temp = new Array();

var ut = readCookie("idutente");
if(readCookie("ad") == "0"){

	$("#g_utenti").css("display","none");
	$("#g_sorgenti").css("display","none");

}else{
	$("#g_utenti").css("display","inline");
	$("#g_sorgenti").css("display","inline");
};

$(document).ready(function(){
	
	
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


$("#tabella_sorgenti").ready(function(){



	$.get("http://localhost:8080/DashboardServer/api/Sorgenti/search/nome/nome="+readCookie("fontecliccata"),function(dati){
		var stringa="{\"fonti\":[ ";
		for(var i=0; i<dati.length;i++){
			if(dati[i].Pagina != "null"){stringa += "{\"ID\":\""+dati[i].ID+"\",\"Pagina\":\""+dati[i].Pagina+"\"},"};
		};
		stringa = stringa.substring(0,stringa.length-1);
		stringa+="]}"
		stringa = stringa.replace(/\$/gi,"/").replace(/%23/gi,"#");
		sorgenti = $.parseJSON(stringa);
		
		var struttura = document.getElementById("elenco_gestione_sorg").innerHTML;
			    var template = Handlebars.compile(struttura);
			    var html = template(sorgenti);
			    document.getElementById("fonti_tab").innerHTML = html;
			    document.getElementById("title").innerHTML = readCookie("fontecliccata");




		$(".icon-trash").on("click",function(){
		//this.id
		$("#tabella_sorgenti").css("display","none");
		$("#btn-s").css("display","inline");
		$("#btn-s").css("left","50%");
		var id= this.id.substring(4,this.id.length);
		$("#ok_confirm").click(function(){
			$.get("http://localhost:8080/DashboardServer/api/Sorgenti/gestione/action=delete/id="+id+"/nomefonte=awa/pagina=awa/link=awa/tipo=awa/autore=1/icona=awa",function(){window.location = "gestione_sorgenti.html"});
		});
		$("#no_confirm").click(function(){
			window.location = "gestione_sorgenti.html";
		});

			}); //end icon-trash click


		$(".icon-pencil").on("click",function(){
			$("#tabella_sorgenti").css("display","none");
			var id= this.id.substring(4,this.id.length);
			$.get("http://localhost:8080/DashboardServer/api/Sorgenti/search/id/id="+id,function(editable){
				
			
				

				var struttura = document.getElementById("editable-tab").innerHTML;
			    var template = Handlebars.compile(struttura);
			   	var stringa = JSON.stringify(editable);
			   	var context = JSON.parse(stringa.replace(/\$/gi,"/").replace(/%23/gi,"#"));

			    var html = template(context);
			    document.getElementById("edit-tab").innerHTML = html;
			    document.getElementById("pagina_edit").innerHTML = editable.Pagina.replace(/\$/gi,"/").replace(/%23/gi,"#");

			   document.getElementById("input-tipo").value = editable.Tipo;

			     $("#tabella_edit").css("display","inline");
			     $("#conferma-edit").click(function(){
			     	 $("#tabella_edit").css("display","none");
			     	 $("#button-edit").css("display","none");
			     	$("#btn-edit").css("display","inline");
			     	$("#btn-edit").css("left","50%");


			     });
			     $("#annulla-edit").click(function(){window.location = "gestione_sorgenti.html";});
				$("#no-confirm-edit").click(function(){
					window.location = "gestione_sorgenti.html";
				}); //end no-confirm.click
				$("#ok-confirm-edit").click(function(){
					var pagina;
					var link;
					var tipo = document.getElementById("input-tipo").value;
					if(document.getElementById("input-pagina").value != ""){pagina=document.getElementById("input-pagina").value.replace(/\//gi,"$").replace(/\#/gi,"%23");}
					else{pagina = document.getElementById("input-pagina").placeholder.replace(/\//gi,"$").replace(/\#/gi,"%23");};
					if(document.getElementById("input-link").value != ""){link=document.getElementById("input-link").value.replace(/\//gi,"$").replace(/\#/gi,"%23");}
					else{link = document.getElementById("input-link").placeholder.replace(/\//gi,"$").replace(/\#/gi,"%23");};
					if((tipo == "Hashtag") && ((document.getElementById("input-link").value == "")||(pagina.substring(0,3) == "%23"))){link = "https:$$twitter.com$search%3Fq%3D%23"+pagina.substring(3,pagina.length)+"%26src%3Dhash";};
				
					var url = "http://localhost:8080/DashboardServer/api/Sorgenti/gestione/action=mod/id="+editable.ID+"/nomefonte="+editable.Nome+"/pagina="+pagina+"/link="+link+"/tipo="+tipo+"/autore="+readCookie("idutente")+"/icona="+editable.Icona;
					$.get(url,function(){window.location = "gestione_sorgenti.html";});

					
					
				});
			});


		}); //end icon-pencil click





	});

});
