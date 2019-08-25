package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;

public class Asignacion {

  GrafoMatriz g;
  resultadoSlot resultado;
 
	
	Asignacion(GrafoMatriz g, resultadoSlot resultado ){
		
		this.g = g;
		this.resultado = resultado;
		
	}
	
	int p;
	int m;
	public void marcarSlotUtilizados(Integer id) {

		System.out.println(resultado);
		String camino = resultado.camino.toString();
		String nuevo_camino;
		int p1;
		//vamos a guardar el camino formateado
		p1 = camino.indexOf("]");
		System.out.println(p1);
		nuevo_camino = camino.substring(1,p1);
		nuevo_camino = nuevo_camino.replaceAll("\\s", "");
		System.out.println("Se imprime el resultado");
		System.out.println(id.toString()+","+nuevo_camino);


		
		   int mitad = lugarInicialAsignacion(resultado);
		
		for (int i=0; i < resultado.camino.get_vertex_list().size()-1; i++) {
			
			   BaseVertex id1 = resultado.camino.get_vertex_list().get(i);
			   BaseVertex id2 = resultado.camino.get_vertex_list().get(i+1);	
			
			   int k = id1.get_id();
			   int l = id2.get_id();
			   
			   int n1 = g.posicionNodo(k);
			   int n2 = g.posicionNodo(l);
			   
			   p = n1;
			   m = n2;
			   
			   int mitadderecha = mitad;
			   int mitadizquierda = mitad;
			   int contador=0;
			   for (int x = 0; x < resultado.cantidadfs; x++) {
				 
				   if (x == 0) {
				     g.grafo[n1][n2].listafs[mitad].libreOcupado = 1;
				     g.grafo[n1][n2].listafs[mitad].tiempo = g.grafo[n1][n2].tiempo;
				     g.grafo[n2][n1].listafs[mitad].libreOcupado = 1;
				     g.grafo[n2][n1].listafs[mitad].tiempo = g.grafo[n2][n1].tiempo;
				     g.grafo[n1][n2].listafs[mitad].id = id;
				     g.grafo[n2][n1].listafs[mitad].id = id;
                       if(contador == 0) {
					 	//concatenar y guardar el id de la conexion
					 	g.grafo[n1][n2].enlace.add(id.toString() + ","+ nuevo_camino);
					 	g.grafo[n1][n2].ids.add(id);
					 	contador=1;
					 }

				   }
				   else if (x !=0 && (x%2)==0) {
					   mitadizquierda--;
				       g.grafo[n1][n2].listafs[mitadizquierda].libreOcupado = 1;
					   g.grafo[n1][n2].listafs[mitadizquierda].tiempo = g.grafo[n1][n2].tiempo;
				       g.grafo[n2][n1].listafs[mitadizquierda].libreOcupado = 1;
					   g.grafo[n2][n1].listafs[mitadizquierda].tiempo = g.grafo[n2][n1].tiempo;
                       g.grafo[n1][n2].listafs[mitad].id = id;
                       g.grafo[n2][n1].listafs[mitad].id = id;
				   }  
			       else if (x !=0 && (x%2)!=0) {
			    	   mitadderecha++;
			    	   g.grafo[n1][n2].listafs[mitadderecha].libreOcupado = 1;
					   g.grafo[n1][n2].listafs[mitadderecha].tiempo = g.grafo[n1][n2].tiempo;
					   g.grafo[n2][n1].listafs[mitadderecha].libreOcupado = 1;
					   g.grafo[n2][n1].listafs[mitadderecha].tiempo = g.grafo[n2][n1].tiempo;
                       g.grafo[n1][n2].listafs[mitad].id = id;
                       g.grafo[n2][n1].listafs[mitad].id = id;
				   }
			   }		   
	
			   for (int x = 0; x < g.grafo[0][0].listafs.length; x++) {
			   	System.out.println(g.grafo[p][m].listafs[x].libreOcupado);
				}		
			   System.out.println("######");
		} 
				
			
	}

	public int lugarInicialAsignacion(resultadoSlot resultado) {
		int indiceInicio = (resultado.indice - resultado.contador) + 1;
		int mitad = (indiceInicio + resultado.indice) / 2;
		return mitad;
	}
	

}
