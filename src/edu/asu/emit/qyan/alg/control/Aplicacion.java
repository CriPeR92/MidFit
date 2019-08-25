package edu.asu.emit.qyan.alg.control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import edu.asu.emit.qyan.alg.control.YenTopKShortestPathsAlg;
import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.VariableGraph;

public class Aplicacion {

	public static void main(String[] args) throws InterruptedException, IOException {	
		
		// Matriz que representa la red igual al archivo test_16 que se va a utilar al tener los caminos.
		int []vertices = {0,1,2,3,4,5};
		GrafoMatriz g = new GrafoMatriz(vertices);
		g.InicializarGrafo(g.grafo);
		g.agregarRuta(0, 1, 1, 3, 5);
		g.agregarRuta(0, 4, 1, 3, 5);
		g.agregarRuta(0, 5, 1, 3, 5);
		g.agregarRuta(1, 2, 1, 3, 5);
		g.agregarRuta(2, 3, 1, 3, 5);
		g.agregarRuta(2, 4, 1, 3, 5);
		g.agregarRuta(3, 5, 1, 3, 5);
		g.agregarRuta(4, 5, 1, 3, 5);

		System.out.println("Testing top-k shortest paths!");
		VariableGraph graph = new VariableGraph("data/test_16");
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);

		boolean bandera = true;
		String name_file = "conexiones";
		String name_file2 = "conexiones2";
		int contador = 0;

		while (bandera) {
			if(contador > 0) {
				name_file = name_file2;
				g.restar();
				bandera = false;
			}
				contador++;
				FileReader input = new FileReader("data/" + name_file);
				BufferedReader bufRead = new BufferedReader(input);

				String linea = bufRead.readLine();


				while (linea != null) {

					if (linea.trim().equals("")) {
						linea = bufRead.readLine();
						continue;
					}

					String[] str_list = linea.trim().split("\\s*,\\s*");

					int origen = Integer.parseInt(str_list[0]);
					int destino = Integer.parseInt(str_list[1]);
					int fs = Integer.parseInt(str_list[2]);
					int tiempo = Integer.parseInt(str_list[3]);
					int id = Integer.parseInt(str_list[4]);

					int inicio = origen;
					int fin = destino;

					//funcion para verificar si la conexion es nueva o sera modificada
					boolean flag = false;
					while (!flag) {
						flag = true;
						g.verificar_conexion(origen, id, fs);
					}

					List<Path> shortest_paths_list = yenAlg.get_shortest_paths(
							graph.get_vertex(inicio), graph.get_vertex(fin), 4);

					BuscarSlot r = new BuscarSlot(g, shortest_paths_list);
					resultadoSlot res = r.concatenarCaminos(fs);
					if (res != null) {
						Asignacion asignar = new Asignacion(g, res);
						//pasar el id de la conexion
						asignar.marcarSlotUtilizados(id);

					} else {
						System.out.println("No se encontró camino posible.");
					}

					linea = bufRead.readLine();
				}
			bufRead.close();
			}

	}
}
	
	
	

