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

function dettaglio(){//foamtrees
	dettaglio_fill()
	f2(0,10);
	f3(0,6);
	f4(0,6);
	  };   // end ready dettaglio 
       
       
      

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

function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css( {
                       position: 'absolute',
                       display: 'none',
                       top: y + 5,
			left: x + 5,
			border: '1px solid #fdd',
			padding: '2px',
			'background-color': '#dfeffc',
			opacity: 0.80
		}).appendTo("body").fadeIn(200);
	}




       
       
       
function multichart(sorgenti){
    
var plot;
                //array delle ultime date
		var data_post=new Array();
		var myDate = new Date();
		myDate.setDate(myDate.getDate()-42);
					
		for(i=0;i<6;i++){
			data_post.push(myDate.setDate(myDate.getDate()+7));
			
		}
                
		var max = 0;
		for (var i=0; i<sorgenti.length;i++){
			for(var k=0; k<sorgenti[i].numeroPost.length;k++){
				if(sorgenti[i].numeroPost[k]>max){max = sorgenti[i].numeroPost[k];}
			}
		};
		
		
		
		
		
                //creazione array di fonti  --->sarÃ  un array di array(data,numeropost)
                var fonti=new Array(); 
                
                
               // var source = fonte(); 
                for(i=0;i<sorgenti.length;i++){
                   var dt = new Array();  //array(data, numeropost)
                   for(var k=0;k<6;k++){
                       dt.push([data_post[k],sorgenti[i].numeroPost[k]]);
                       
                   }// end for k
                   fonti.push(dt);
                    
                }//end for 
                
                
                var dats='['; 
                for(i=0;i<sorgenti.length;i++){
                dats =dats+'{data:fonti['+i+'], label: sorgenti['+i+'].nome},';
                }
                
                dats=dats+']';
              
                if($("#plotmultiplo").length)
                {
                        
                    for(i=0;i<sorgenti.length;i++){
            plot = $.plot($("#plotmultiplo"),
			 eval(dats), {
				   series: {
					   lines: { show: true,
                                                    lineWidth: 2
							 },
					   points: { show: true },
					   shadowSize: 2
				   },
				   grid: { hoverable: true, 
						   clickable: true, 
						   tickColor: "#dddddd",
						   borderWidth: 0 
						 },
					 xaxis: { mode: "time",
							ticks: [data_post[0],data_post[1],data_post[2],data_post[3],data_post[4],data_post[5]]
					 		},
				   yaxis: { min: 0, max: max },  //il primo  l'etichetta del paramentro, il secondo max  il numero max di post esistenti
				   colors: ["blue","red"]
				 });

		

		var previousPoint = null;
		$("#plotmultiplo").bind("plothover", function (event, pos, item) {
			$("#x").text(pos.x.toFixed(2));
			$("#y").text(pos.y.toFixed(2));

				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;

						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(2),
							y = item.datapoint[1].toFixed(2);

						showTooltip(item.pageX, item.pageY,

							y+" post da "+item.series.label

							);
									//item.series.label + " of " + x + " = " + y);
					}
				}
				else {
					$("#tooltip").remove();
					previousPoint = null;
				}
		});
	
            
        }//endif_plot
           }
   }//end function multichart       
                

    //settaggio dati per i bobble
    function Sorgente(nomeS, post0, post1, post2, post3, post4, post5,pol0,pol1,pol2,pol3,pol4,pol5){ //post0 = numero post 6 sett fa. post5 numeo post odierni, analogamente le polaritÃ 
             this.numeroPost=new Array();
            this.numeroPost[0]=post0;
            this.numeroPost[1]=post1;
            this.numeroPost[2]=post2;
            this.numeroPost[3]=post3;
            this.numeroPost[4]=post4;
            this.numeroPost[5]=post5;
             //this.growth=Math.round((this.numeroPost[5]-this.numeroPost[4])*100/this.numeroPost[4]);
            if(this.numeroPost[4] == 0 && this.numeroPost[5]){this.growth = 0;}
           else  if(this.numeroPost[4] == 0){this.growth = 100;}
           else if(this.numeroPost[4] != 0 && this.numeroPost[5]==0){this.growth = -100;}
           else
           {
            	this.growth = Math.round(100*this.numeroPost[5]/this.numeroPost[4]);
            };
            
            if (this.growth<-1){this.notification="red"}
            else if (this.growth>1){this.notification="green"}
            else {this.notification="yellow"};
            this.nome = nomeS;
            this.polarity = new Array();
            this.polarity[0] = pol0;
            this.polarity[1] = pol1;
            this.polarity[2] = pol2;
            this.polarity[3] = pol3;
            this.polarity[4] = pol4;
            this.polarity[5] = pol5;
            
           
            return this;
    }
     
           
        
  //settaggio dati per il grafico singolo
  function setSorgente(s){
    
      var ck = readCookie("fontecliccata");
      this.fonte= ck;
      this.etichetta = ck;
      this.post = new Array();
      for(i=0;i<6;i++){    //da fare con query per il conteggio dei post
        this.post[i]=i*11000;  //post[0] = numero post di 6 sett fa, post[6]= numero post di ad oggi
		}//end for
    
      return this;
      
      
  }//end setSorgente
  
  
  
		
    function singlechart(sorgenti){
         
        //alert(ck[0].toString());
	if($("#sincos").length)
	{   
            
            //array delle ultime date
		var data_post=new Array();
		var myDate = new Date();
		myDate.setDate(myDate.getDate()-42);
					
		for(i=0;i<6;i++){
			data_post.push(myDate.setDate(myDate.getDate()+7));
			
		}
             
		 var source;
                 var ck = readCookie("fontecliccata");
              
		 		for (var i=0;i<sorgenti.length;i++){
                     if (ck == sorgenti[i].nome.trim())
                  {source = sorgenti[i];}
                     
                   
                //endif
            
                };//
               
                
		
		
		//inserimento dati
var max_s = 0; 
			var dt = new Array();

			for(i=0;i<6;i++){
                            dt.push([data_post[i], source.numeroPost[i]]);
                            if(source.numeroPost[i]>max_s){max_s=source.numeroPost[i];};
			}

    


		//tracciamento del grafico singolo

		
                var plot = $.plot($("#sincos"),
			   [ { data: dt, label:source.nome} ], {  //label :param
				   series: {
					   lines: { show: true,
								lineWidth: 2
							 },
					   points: { show: true },
					   shadowSize: 2
				   },
				   grid: { hoverable: true, 
						   clickable: true, 
						   tickColor: "#dddddd",
						   borderWidth: 0 
						 },
					 xaxis: { mode: "time",
							ticks: [data_post[0],data_post[1],data_post[2],data_post[3],data_post[4],data_post[5]]
					 		},
				   yaxis: { min: 0, max: max_s },
				   colors: ["blue"]
				 });


        

		var previousPoint = null;
		$("#sincos").bind("plothover", function (event, pos, item) {
			$("#x").text(pos.x.toFixed(2));
			$("#y").text(pos.y.toFixed(2));

				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;

						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(2),
							y = item.datapoint[1].toFixed(2);
						var d = new Date(item.datapoint[0]);
						showTooltip(item.pageX, item.pageY,

							y+" post del "+d.toString().substring(8,10)+" "+d.toString().substring(4,7)+" da "+item.series.label

							);
									//item.series.label + " of " + x + " = " + y);
					}
				}
				else {
					$("#tooltip").remove();
					previousPoint = null;
				}
		});
		







	}

        
        
        
        
        
        
        
    } //end singlechart
    
    
    
    
    function polaritychart(sorgenti){
        
	
	/* ---------- Flot chart ---------- */
	if($("#flotchart").length)
	{//array delle ultime date
           
		var data_post=new Array();
		var myDate = new Date();
		myDate.setDate(myDate.getDate()-42);
					
		for(i=0;i<6;i++){
			data_post.push(myDate.setDate(myDate.getDate()+7));
			
		}
             
		 var source;
		  
                var ck = readCookie("fontecliccata"); 
		 		for (var i=0;i<sorgenti.length;i++){
                     if (ck == sorgenti[i].nome.trim())
                  {source = sorgenti[i];}
                
                //endif
            
                };//
               
                
		
		//inserimento dati

			var d1 = new Array();

			for(i=0;i<6;i++){
			
			//myDate.setDate(myDate.getDate()+7);
     
				d1.push([data_post[i], source.polarity[i]]);
				}

	
		
		$.plot($("#flotchart"), 
			[
			 	{ label: "gradimento",  data: d1},
			 
			], 
			{
			series: {
				lines: { show: true },
				points: { show: true }
			},
			xaxis: {
				  mode: "time",
				ticks: [data_post[0],data_post[1],data_post[2],data_post[3],data_post[4],data_post[5]]
			},
			yaxis: {
				min: -100,
				max: 100
			},
			grid: {	tickColor: "#dddddd",
					borderWidth: 0,
                                        hoverable: true
			},
			colors: ["#FA5833", "#2FABE9", "#FABB3D"]
		});
                var previousPoint = null;
		$("#flotchart").bind("plothover", function (event, pos, item) {
			$("#x").text(pos.x.toFixed(2));
			$("#y").text(pos.y.toFixed(2));

				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;

						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(2),
							y = item.datapoint[1].toFixed(2);
						var d = new Date(item.datapoint[0]);
						showTooltip(item.pageX, item.pageY,

							//y+"% di gradimento da "+source.nome+" aggiornato al "+Date(x).toString().substring(8,10)+" "+Date(x).toString().substring(4,7)
								y+"% di gradimento da "+source.nome+" aggiornato al "+d.toString().substring(8,10)+" "+d.toString().substring(4,7)
							);
									//item.series.label + " of " + x + " = " + y)
					}
				}
				else {
					$("#tooltip").remove();
					previousPoint = null;
				}
		});
		

    }
	
        
        
    }
    
    
   
							
function f(info,titoli,div,sup,url) { //new Links(div,titolo, link)

    var links = new Array();        
		var temp_l;
//    $.get("http://localhost:8080/DashboardServer/api/links/general/all/idutente="+readCookie("idutente")+"/nomefonte="+readCookie("fontecliccata")+"/limit=0-30",function(titoli){
    	
    	temp_l= $.parseJSON(titoli);
    	
    	for(var q=0;q<temp_l.length;q++){
    		for(var r=0; r<temp_l[q].links.length;r++){
    			links.push(new Links(div,temp_l[q].nome,temp_l[q].links[r].link.replace("$","/"),temp_l[q].links[r].testo));
    			
    		}
    	} 
    	
    	
  //  		});//end get links
    

    	
	
          $("#"+div).html('');
          var dim;
          
          var count=0;
          if(div=="elenco_link_carrot"){dim=10;}else{dim=6;};
          //var myLink = new Array();
          
          for(var x=0;x<links.length;x++){
        	  
           if((info.groups[0].label == links[x].sorg)&&(count<dim)){
             
            
               if($("#"+div).append(links[x].link)){count++;};
            
           } 
          }//end for
         if (count+1 > dim){$("#"+div).append("<a id='altro' class='links2_elenco_link_carrot' style='display:block'>Altro...</a>");};
      
         $("#altro").click(function(){otherlinks(div,url,sup);});
          
          
          
          

} //end function Info           
           
function otherlinks(div,url,sup){
	var sup1;

	 if(div=="elenco_link_carrot"){sup1 = sup+10;}else{sup1 = sup+6;};
	
	 url = url.split("limit");
	 url = url[0]+"limit="+sup+"-"+sup1;
	 console.log(url);
    var links = new Array();        
		var temp_l;
    $.get(url,function(titoli){

    	temp_l= $.parseJSON(titoli);
    	
    	for(var q=0;q<temp_l.length;q++){
    		for(var r=0; r<temp_l[q].links.length;r++){
    			links.push(new Links(div,temp_l[q].nome,temp_l[q].links[r].link.replace("$","/"),temp_l[q].links[r].testo));
    			
    		}
    	} 
    	
          $("#"+div).html('');
          if(div=="elenco_link_carrot"){dim=10;}else{dim=6;};
          
          var count=0;
        
          //var myLink = new Array();
        
          for(var x=0;x<links.length;x++){
        	 console.log(count);
        	  if(count<dim-1){
              count++;
            
               $("#"+div).append(links[x].link);
             
           
          }
        }//end for;   
       
         if (count+1 > dim){$("#"+div).append("<a id='altro' class='links2_elenco_link_carrot' style='display:block'>Altro...</a>");};
         
         
         $("#altro").click(function(){otherlinks(div,url,sup1);});

    });//end get links      
};

function Links(div, Sorg,lnk,lb){
    this.sorg = Sorg;
    this.link ="<a class='links2_"+div+"' href='"+lnk.replace("$","/")+"' style='display:block'>&#9732 "+lb+"</a>";
    return this;
}


function f2(inf,sup){var foamtree2, supported2;
$.get("http://localhost:8080/DashboardServer/api/links/general/all/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit="+inf+"-"+sup,function(titoli){
var url = "http://localhost:8080/DashboardServer/api/links/general/all/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit=";
      // Check if the browser supports FoamTree
      supported2 = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported2) {
        foamtree2 = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization2",
          backgroundColor: "rgba(0, 0, 0, 7)",

          // Hide the loading indicator once the animation starts
         // onRolloutStart: function() {
           // CarrotSearchDemoHelper.loading(false);
          //},
          onGroupSelectionChanged: function(info){f(info,titoli,"elenco_link_carrot",sup,url);}
        });
        
   
	
        
        	var sor = $.parseJSON(titoli);
        	
        	var dats = "{\"groups\":[ ";
        	for(var i=0; i<sor.length;i++){
        	dats+="{\"label\":\""+sor[i].nome+"\", \"weight\":1,\"id\":\""+i+"\"},";
        	};
        	
        	dats = dats.substring(0,dats.length-1);
        	dats+="]}";
        	
        	
        	foamtree2.set("dataObject", $.parseJSON(dats));
        
}//endifsupported
        	if (foamtree2) {
        	          foamtree2.set("dataObject", $.parseJSON(dats));
        	        }
        
       
        //foamtree2.set("dataObject", dat);
      
      
    

      });

  }


function f3(inf,sup){var foamtree3, supported3;
$.get("http://localhost:8080/DashboardServer/api/links/general/negative/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit="+inf+"-"+sup,function(titoli){
var url = "http://localhost:8080/DashboardServer/api/links/general/negative/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit=";
      // Check if the browser supports FoamTree
      supported3 = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported3) {
        foamtree3 = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization3",
          backgroundColor: "rgba(0, 0, 0, 7)",

          // Hide the loading indicator once the animation starts
         // onRolloutStart: function() {
           // CarrotSearchDemoHelper.loading(false);
          //},
          onGroupSelectionChanged: function(info){f(info,titoli,"elenco_link_minus",sup,url);}
        });
        
   
	
        
        	var sor = $.parseJSON(titoli);
        	
        	var dats = "{\"groups\":[ ";
        	for(var i=0; i<sor.length;i++){
        	dats+="{\"label\":\""+sor[i].nome+"\", \"weight\":1,\"id\":\""+i+"\"},";
        	};
        	
        	dats = dats.substring(0,dats.length-1);
        	dats+="]}";
        	
        	
        	foamtree3.set("dataObject", $.parseJSON(dats));
        
}//endifsupported
        	if (foamtree3) {
        	          foamtree3.set("dataObject", $.parseJSON(dats));
        	        }
     
      });

  }


function f4(inf,sup){var foamtree, supported;
$.get("http://localhost:8080/DashboardServer/api/links/general/positive/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit="+inf+"-"+sup,function(titoli){
var url = "http://localhost:8080/DashboardServer/api/links/general/positive/idutente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/limit=";
      // Check if the browser supports FoamTree
      supported = CarrotSearchFoamTree.supported;

      // If supported, embed
      if (supported) {
        foamtree = new CarrotSearchFoamTree({
          // Identifier of the DOM element into which to embed FoamTree
          id: "visualization",
          backgroundColor: "rgba(0, 0, 0, 7)",

          // Hide the loading indicator once the animation starts
         // onRolloutStart: function() {
           // CarrotSearchDemoHelper.loading(false);
          //},
          onGroupSelectionChanged: function(info){f(info,titoli,"elenco_link_plus",sup,url);}
        });
        
   
	
        
        	var sor = $.parseJSON(titoli);
        	
        	var dats = "{\"groups\":[ ";
        	for(var i=0; i<sor.length;i++){
        	dats+="{\"label\":\""+sor[i].nome+"\", \"weight\":1,\"id\":\""+i+"\"},";
        	};
        	
        	dats = dats.substring(0,dats.length-1);
        	dats+="]}";
        	
        	
        	foamtree.set("dataObject", $.parseJSON(dats));
        
}//endifsupported
        	if (foamtree) {
        	          foamtree.set("dataObject", $.parseJSON(dats));
        	        }
     
      });

  }


function multifill(){
	
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
	var s = new Array();
	
	
	
	$.get("http://localhost:8080/DashboardServer/api/Accesso/search/utente="+ut,function(nomi){
	 for(var x=0; x<nomi.length; x++){
		//TODO controllare query
		 $.get("http://localhost:8080/DashboardServer/api/Sorgenti/grafico/general/utente="+ut+"/nomefonte="+nomi[x]+"/data1="+dat_strings[0]+"/data2="+dat_strings[1]+"/data3="+dat_strings[2]+"/data4="+dat_strings[3]+"/data5="+dat_strings[4]+"/data6="+dat_strings[5]+"/data7="+dat_strings[6],function(data){
			 var par = $.parseJSON(data);
			 s.push(new Sorgente(par.nomefonte, par.settimana1.numeropost,par.settimana2.numeropost,par.settimana3.numeropost,par.settimana4.numeropost,par.settimana5.numeropost,par.settimana6.numeropost, par.settimana1.gradimento, par.settimana2.gradimento,par.settimana3.gradimento, par.settimana4.gradimento, par.settimana5.gradimento, par.settimana6.gradimento));
			
			 multichart(s);
			 
			 var dati2="{\"bollefill\": [ ";
		
			 for (var t=0; t<nomi.length; t++){
				
			 dati2 += "{\"nome\":\""+s[t].nome+"\",\"growth\":"+s[t].growth+",\"ultimi\":"+s[t].numeroPost[5]+",\"notification\":\""+s[t].notification+"\" },";
			
			 };       
			 console.log("l'errore qui sopra? non lo so, credo dipenda dal get interno al for x");
			 dati2 = dati2.substring(0,dati2.length-1);
			 
		        dati2 +="]}";
		       
			    var struttura2 = document.getElementById("bolle").innerHTML;
			    var template2 = Handlebars.compile(struttura2);
			    var html2 = template2($.parseJSON(dati2));
			    document.getElementById("bobbles").innerHTML = html2;

			  
			    
			 
			 
			 
		});//end get

	 }//end forx
	 });//end get fonti
	


}




function dettaglio_fill(){
	
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
	
		 $.get("http://localhost:8080/DashboardServer/api/Sorgenti/grafico/general/utente="+ut+"/nomefonte="+readCookie("fontecliccata")+"/data1="+dat_strings[0]+"/data2="+dat_strings[1]+"/data3="+dat_strings[2]+"/data4="+dat_strings[3]+"/data5="+dat_strings[4]+"/data6="+dat_strings[5]+"/data7="+dat_strings[6],function(data){
			 var par = $.parseJSON(data);
			 s1.push(new Sorgente(par.nomefonte, par.settimana1.numeropost,par.settimana2.numeropost,par.settimana3.numeropost,par.settimana4.numeropost,par.settimana5.numeropost,par.settimana6.numeropost, par.settimana1.gradimento, par.settimana2.gradimento,par.settimana3.gradimento, par.settimana4.gradimento, par.settimana5.gradimento, par.settimana6.gradimento));
			
			 singlechart(s1);
			 polaritychart(s1);
			 
			 
			 
		});//end get

	
	 });//end get fonti
	


}



function switch_data(month){
	switch(month){
	case "Jan": mese = 1;break;
	case "Feb": mese = 2;break;
	case "Mar": mese = 3;break;
	case "Apr": mese = 4;break;
	case "May": mese = 5;break;
	case "Jun": mese = 6;break;
	case "Jul": mese = 7;break;
	case "Aug": mese = 8;break;
	case "Sep": mese = 9;break;
	case "Oct": mese = 10;break;
	case "Nov": mese = 11;break;
	default: mese=12;
	};
	return mese;
		
}



