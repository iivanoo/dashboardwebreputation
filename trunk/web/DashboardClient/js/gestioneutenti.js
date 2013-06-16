var dati;
var ex;
$(document).ready(function(){
    fill();
    fill_tabella();
});    // end ready function 
       


function fill_tabella(){
	
	
	
	$.get("http://localhost:8080/DashboardServer/api/gestioneutenti",function(dati_json){
		dati = $.parseJSON(dati_json);

		 
	    var struttura = document.getElementById("el_sorg").innerHTML;
	    
	    var template = Handlebars.compile(struttura);
	    var html = template(dati);
	    document.getElementById("fonti_tab").innerHTML = html;
	    $("#save").click(function(){
	        salva_modifiche();
	        });// end click save

	      $(".icon-chevron-down").click(function(){
	        	$(this).toggleClass("icon-chevron-down");
	        	$(this).toggleClass("icon-chevron-up");
	        	
	        	var point = $(this).attr("id");
	        	
	        	//$(document.getElementById(point)).slideToggle("slow");
	        	var point2 =point.split('-h-')[0]+'-k-'+point.split('-h-')[1];
	        	var str = $(document.getElementById(point)).html();
	        	
	        	
	        	$(document.getElementById(point2)).slideToggle("slow");
	        	
	        }); //end click chevron_down

	      
	      $("#tabella_permessi").ready(function(){
	      	
	      	var arr = new Array(dati);
	      	
	      	var ex = "#"+arr[0].selezione[0].utente+"$"+arr[0].selezione[0].fonti[0].nome+"$"+arr[0].selezione[0].fonti[0].pagine[0].pagina;
	      	
	      	try{
	      		ex ='i#'+arr[0].selezione[0].utente+'-h-'+arr[0].selezione[0].fonti[0].nome;
	      		
	      		for (var i=0; i<arr.length;i++){
	      			for(var k=0; k<arr[i].selezione.length;k++){
	      				for (var j=0; j<arr[i].selezione[k].fonti.length;j++){
	      					for(var x=0; x<arr[i].selezione[k].fonti[j].pagine.length;x++){
	      						if(arr[i].selezione[k].fonti[j].pagine[x].pagina){
	      							ex ='i#'+arr[0].selezione[0].utente+'-h-'+arr[0].selezione[0].fonti[0].nome;
	      							$(ex).css("display","inline");
	      						};
	      					}//end for x
	      				}//end for j
	      			}//end for k
	      		}//end for i
	      		
	      		
	      		
	      		
	      		if(arr[0].selezione[0].fonti[0].pagine[0].pagina){$(ex).css("display","inline");};
	      		
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
		var item = document.getElementsByTagName("input");
		for(var i=0; i<item.length; i++){  //parte da 1 xke item[0] coincide con l'input id=typehead
			var email = item[i].id.split("$")[0];
			
			if(item[i].checked==true){
			var fonte = item[i].id.split("$")[1];
			
			if(fonte == "admin"){
				
				var url="http://localhost:8080/DashboardServer/api/admin/action=add/email="+email;
				$.get(url);
				console.log();
			}else{var pagina;

			if(item[i].id.split("$")[2] == "Pagina pubblica"){pagina="public";}
			else if(item[i].id.split("$")[2] == ""){pagina="null";}
			else{ pagina = item[i].id.split("$")[2];};
			
			$.get("http://localhost:8080/DashboardServer/api/Accesso/mod/add/utente="+email+"/nomefonte="+fonte+"/pagina="+pagina);
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
			if(item[i].id.split("$")[2] == "Pagina pubblica"){pagina="public";}
			else if(item[i].id.split("$")[2] == ""){pagina="null";}
			else{ pagina = item[i].id.split("$")[2];};
			$.get("http://localhost:8080/DashboardServer/api/Accesso/mod/del/utente="+email+"/nomefonte="+fonte+"/pagina="+pagina);
			};
			
			};
		
		}//end for
		console.log("cambiamenti salvati");
		//TODO inserire div al posto dell'alert
		alert("qui ci va il div di reindirizzamento");
	}	

    
    
}//end function fill_tabella


