document.getElementById("referer").innerHTML=readCookie("fontecliccata");

$("#cerca").click(function(){
	
	var topic = document.getElementById("typeahead").value;

	dettaglio_topic_fill(topic);
	foam1(topic,0,10);
	foam2(topic,0,6);
	foam3(topic,0,6);
});//end click function

$("#typeahead").keypress(function(e) {
    if(e.which == 13) {
       $("#cerca").click();
    }
});


function dettaglio_topic_fill(topic){
	
	var d = new Date();
	 var dat = new Date(d.getTime()-3628800000);
	 var dat_strings = new Array();
	 var data_post = new Array();
	 data_post.push(dat);
	 dat_strings.push(data_post[0].toString().substring(11,15)+"-"+switch_data(data_post[0].toString().substring(4,7))+"-"+data_post[0].toString().substring(8,10));
	 for(var i=1;i<7;i++){
	 data_post.push(new Date(data_post[i-1].getTime()+604800000));
	 dat_strings.push(data_post[i].toString().substring(11,15)+"-"+switch_data(data_post[i].toString().substring(4,7))+"-"+data_post[i].toString().substring(8,10));
	 };

	 //USARE dat_strings[] per le date nel formato 'AAAA-MM-GG'
	var count=0;
	var s1 = new Array();
	 $.get("http://localhost:8080/DashboardServer/api/Accesso/search/utente="+readCookie("idutente"),function(nomi){
	
		 $.get("http://localhost:8080/DashboardServer/api/Sorgenti/grafico/general/nomefonte="+readCookie("fontecliccata")+"/topic="+topic+"/data1="+dat_strings[0]+"/data2="+dat_strings[1]+"/data3="+dat_strings[2]+"/data4="+dat_strings[3]+"/data5="+dat_strings[4]+"/data6="+dat_strings[5]+"/data7="+dat_strings[6],function(data){
			 var par = $.parseJSON(data);
			 s1.push(new Sorgente(par.nomefonte, par.settimana1.numeropost,par.settimana2.numeropost,par.settimana3.numeropost,par.settimana4.numeropost,par.settimana5.numeropost,par.settimana6.numeropost, par.settimana1.gradimento, par.settimana2.gradimento,par.settimana3.gradimento, par.settimana4.gradimento, par.settimana5.gradimento, par.settimana6.gradimento));
		
			 singlechart(s1);
			 polaritychart(s1);
			 
			 
			 
		});//end get

	
	 });//end get fonti
	


}






function foam1(topic,inf,sup){var foamtree, supported;
$.get("http://localhost:8080/DashboardServer/api/links/topic/all/topic="+topic+"/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit="+inf+"-"+sup,function(titoli){
var url = "http://localhost:8080/DashboardServer/api/links/topic/all/topic="+topic+"/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit=";
      // Check if the browser supports FoamTree
      supported = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported) {
        foamtree = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization2",
          backgroundColor: "rgba(0, 0, 0, 7)",

          // Hide the loading indicator once the animation starts
         // onRolloutStart: function() {
           // CarrotSearchDemoHelper.loading(false);
          //},
          onGroupSelectionChanged: function(info){f(info,titoli,"elenco_link_carrot",sup,url);}
        });
        
   
	
        	var dats;
        	var sor = $.parseJSON(titoli);
        	if(sor.length != 0){
        		dats = "{\"groups\":[ ";
            	for(var i=0; i<sor.length;i++){
            	dats+="{\"label\":\""+sor[i].nome+"\", \"weight\":1,\"id\":\""+i+"\"},";
            	};
            	
            	dats = dats.substring(0,dats.length-1);
            	dats+="]}";
            	
        	}
        	else{
        		dats = "{\"groups\":[{\"label\":\"non ci sono post per il topic cercato\",\"weight\":1,\"id\":\"no_post\"}]}";

        	};
        	
        	
        	foamtree.set("dataObject", $.parseJSON(dats));
        
}//endifsupported
        	if (foamtree) {
        	          foamtree.set("dataObject", $.parseJSON(dats));
        	        }

      });

  }


function foam2(topic,inf,sup){var foamtree2, supported2;
$.get("http://localhost:8080/DashboardServer/api/links/topic/negative/topic="+topic+"/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit="+inf+"-"+sup,function(titoli){
var url = "http://localhost:8080/DashboardServer/api/links/topic/negative/topic="+topic+"/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit=";
      // Check if the browser supports FoamTree
      supported2 = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported2) {
        foamtree2 = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization3",
          backgroundColor: "rgba(0, 0, 0, 7)",

          // Hide the loading indicator once the animation starts
         // onRolloutStart: function() {
           // CarrotSearchDemoHelper.loading(false);
          //},
          onGroupSelectionChanged: function(info){f(info,titoli,"elenco_link_minus",sup,url);}
        });
        
   
	
        	var dats;
        	var sor = $.parseJSON(titoli);
        	if(sor.length != 0){
        		dats = "{\"groups\":[ ";
            	for(var i=0; i<sor.length;i++){
            	dats+="{\"label\":\""+sor[i].nome+"\", \"weight\":1,\"id\":\""+i+"\"},";
            	};
            	
            	dats = dats.substring(0,dats.length-1);
            	dats+="]}";
            	
        	}
        	else{
        		dats = "{\"groups\":[{\"label\":\"non ci sono post per il topic cercato\",\"weight\":1,\"id\":\"no_post\"}]}";

        	};
        	
        	
        	foamtree2.set("dataObject", $.parseJSON(dats));
        
}//endifsupported
        	if (foamtree2) {
        	          foamtree2.set("dataObject", $.parseJSON(dats));
        	        }
     
      });

  }


function foam3(topic,inf,sup){var foamtree3, supported3;
$.get("http://localhost:8080/DashboardServer/api/links/topic/positive/topic="+topic+"/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit="+inf+"-"+sup,function(titoli){
var url = "http://localhost:8080/DashboardServer/api/links/topic/positive/topic="+topic+"/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit=";
      // Check if the browser supports FoamTree
      supported3 = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported3) {
        foamtree3 = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization",
          backgroundColor: "rgba(0, 0, 0, 7)",

          onGroupSelectionChanged: function(info){f(info,titoli,"elenco_link_plus",sup,url);}
        });
        
   
	
        var dats;
        	var sor = $.parseJSON(titoli);
        	if(sor.length != 0){
            	dats = "{\"groups\":[ ";
            	for(var i=0; i<sor.length;i++){
            	dats+="{\"label\":\""+sor[i].nome+"\", \"weight\":1,\"id\":\""+i+"\"},";
            	};
            	
            	dats = dats.substring(0,dats.length-1);
            	dats+="]}";
            	}else{
            		dats = "{\"groups\":[{\"label\":\"non ci sono post per il topic cercato\",\"weight\":1,\"id\":\"no_post\"}]}";
            	};
        	
        	
        	foamtree3.set("dataObject", $.parseJSON(dats));
        
}//endifsupported
        	if (foamtree3) {
        	          foamtree3.set("dataObject", $.parseJSON(dats));
        	        }
     
      });

  }
