package vista;
import java.util.Scanner;
import logica.Graficar;
import logica.LGrafo;

public class Menu {

    public Menu() {
    }

    public void MenuPrincipal() {
        Scanner escaner = new Scanner(System.in);
        System.out.println("Ingrese el grafo");
        LGrafo grafo = new LGrafo(escaner.next());
        int opc;
        do {
            System.out.println("---------------Menu-------------");
            System.out.println("1.Matriz de Adyacencia");//ya
            System.out.println("2.Matriz de Incidencia");//ya
            System.out.println("3.Lista de Adyacencia");//ya
            System.out.println("4.Mostrar el grafo");//ya
            System.out.println("5.BFS");//ya
            System.out.println("6.DFS");//ya
            System.out.println("7.Distancia Minima");//ya
            System.out.println("8.Otro algoritmo de busqueda");//falta, Yo
            System.out.println("0.Salir");
            try {
                opc = escaner.nextInt();
            } catch (Exception e) {
                // TODO: handle exception
                opc = -1;
            }
            switch (opc) {
                case 1:
                    System.out.println(grafo.MatrizAdyacencia());
                    break;
                case 2:
                    System.out.println(grafo.MatrizIncidencia());
                    break;
                case 3:
                    System.out.println("*** Lista de Adyacencia: ***");
                    grafo.ListaDeAdyacencia();
                case 4:
                    Graficar x = new Graficar(grafo);
                    x.pintarGrafo(grafo.MatrizAdyacenciaint());
                    break;
                case 5:
                    System.out.println("Ingrese el punto de inicio");
                    String Comienz = escaner.next();
                    System.out.println("Busqueda BFS: ");
                    grafo.BFS(Comienz);
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Ingrese el punto de inicio");
                    String Comienzo = escaner.next();
                    System.out.println("Busqueda DFS: ");
                    grafo.DFS(Comienzo);
                    System.out.println();
                    break;
                case 7:
                    System.out.println("Ingrese primero el inicio, y luego el final");
                    String inicio = escaner.next(), Final = escaner.next();
                    if (grafo.Existe(Final) && grafo.Existe(inicio)) {
                        System.out.println(grafo.DistanciaMinima(inicio, Final));
                    } else {
                        System.out.println("No existe el inicio o el final");
                    }
                    break;
                case 8:
                System.out.println("Ingrese el punto de inicio");
                String Czo = escaner.next();
                System.out.println("Ingrese el punto final");
                String fin = escaner.next();
                System.out.println("Limite de pasos");
                int pasos = escaner.nextInt();
        
                boolean SeEncontro = grafo.BusquedaLimitada(Czo, fin, pasos);
                if (SeEncontro) {
                    System.out.println("Se encontró el nodo objetivo: "+fin);
                }else{
                    System.out.println("No se llego al nodo " + fin);
                }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion Incorrecta");
                    break;
            }
        } while (opc != 0);
        escaner.close();
    }
}
