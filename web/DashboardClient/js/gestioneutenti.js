var dati;
var ex;
$(document).ready(function(){
    fill();
    fill_tabella();
    $("#save").click(function(){
    	salva_modifiche();
    	});// end click save

  $(".icon-chevron-down").click(function(){
    	$(this).toggleClass("icon-chevron-down");
    	$(this).toggleClass("icon-chevron-up");
    	
    	var point = $(this).attr("id");
    	console.log(point);
    	//$(document.getElementById(point)).slideToggle("slow");
    	var point2 =point.split('-h-')[0]+'-k-'+point.split('-h-')[1];
    	var str = $(document.getElementById(point)).html();
    	console.log(point2);
    	$(document.getElementById(point2)).slideToggle("slow");
    	//if($(document.getElementById(point)).html() =="" ){$(document.getElementById(point)).remove()};
    	
    }); //end click chevron_down
  /* 
     $(".icon-chevron-down").click(function(){
    	$(this).toggleClass("icon-chevron-down");
    	$(this).toggleClass("icon-chevron-up");
    	
    	//console.log($(this).attr("id"));
    	var point = $(this).attr("class").split("$")[0];
    	console.log(point);
    	$(document.getElementById(point)).slideToggle("slow");
    	var str = $(document.getElementById(point)).html();
    	if($(document.getElementById(point)).html() =="" ){$(document.getElementById(point)).remove()};
    	//$("#"+point).slideToggle("slow");
    	
    	
    }); 
      */
    
    
    $("#tabella_permessi").ready(function(){
    	//console.log($(".icon-chevron-down").attr("class").split("$")[0]);
    	var arr = new Array(dati);
    	console.log(arr[0].selezione[0]);
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
    	}
    	catch(err){};
    });//end ready tabella_permessi
    
   

    click_check();
   
});    // end ready function 
       
   
function fill(){

    var dati ={fontifill : ["Web","WebPA","Facebook","Twitter","Blog","News","Press Agency","Images"]};
    var struttura = document.getElementById("el_fonti").innerHTML;
    var template = Handlebars.compile(struttura);
    var html = template(dati);
    document.getElementById("ulbo").innerHTML = html;


}

function fill_tabella(){
     dati ={selezione :[
    		            {utente:"utente1",
             		    fonti:[{nome:"Web", pagine: [{pagina:"pagina1"},{pagina:"pagina2"},{pagina:"pagina3"}]},{nome:"WebPA"},{nome:"Facebook"},{nome:"Twitter"},{nome:"Blog"},{nome:"News"},{nome:"Press Agency"},{nome:"Images"},{nome:"Web1"},{nome:"WebPA1"},{nome:"Facebook1"},{nome:"Twitter1"},{nome:"Blog1"},{nome:"News1"},{nome:"Press Agency1"},{nome:"Images1"}]
    		            },
    		            {utente:"utente2",
        		            fonti:[{nome:"Web"},{nome:"WebPA"},{nome:"Facebook"},{nome:"Twitter"},{nome:"Blog"},{nome:"News"},{nome:"Press Agency"},{nome:"Images"},{nome:"Web1"},{nome:"WebPA1"},{nome:"Facebook1"},{nome:"Twitter1"},{nome:"Blog1"},{nome:"News1"},{nome:"Press Agency1"},{nome:"Images1"}]
        		            },
        		        {utente:"utente3",
            		       fonti:[{nome:"Web"},{nome:"WebPA"},{nome:"Facebook"},{nome:"Twitter"},{nome:"Blog"},{nome:"News"},{nome:"Press Agency"},{nome:"Images"},{nome:"Web1"},{nome:"WebPA1"},{nome:"Facebook1"},{nome:"Twitter1"},{nome:"Blog1"},{nome:"News1"},{nome:"Press Agency1"},{nome:"Images1"}]
            		        }
    		
    		]};
    var struttura = document.getElementById("el_sorg").innerHTML;
    
    var template = Handlebars.compile(struttura);
    var html = template(dati);
    document.getElementById("fonti_tab").innerHTML = html;
	
   // console.log(document.getElementsByTagName("input"));
    
    
}//end function fill_tabella

/*
var dati2 = {utenti_sel : ["utente1","utente2","utente3","utente4","utente5"]};
var struttura2 = document.getElementById("el_ut").innerHTML;
console.log("log-->"+document.getElementById("el_ut"))
var template2 = Handlebars.compile(struttura2);
var html2 = template(dati2);
document.getElementById("utenti_tab").innerHTML = html2;
*/

/*
function click_check(){
	  $("input").click(function(){
		  var thisinput = $(this)[0];
		  thisinput.setAttribute("checked","checked");
		  //console.log($(this)[0]);
		    console.log($(this)[0].checked);
		    if($(this)[0].checked==true){alert("ok");};
		  });
}
*/

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
	for(var i=1; i<item.length; i++){  //parte da 1 xke item[0] coincide con l'input id=typehead
	if(item[i].checked==true){i+"--"+console.log(item[i].id+" checked\n");}
	else{console.log(i+"---"+item[i].id +" not checked\n");};
	}
	
}