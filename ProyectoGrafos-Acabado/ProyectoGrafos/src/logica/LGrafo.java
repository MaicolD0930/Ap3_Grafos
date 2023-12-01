package logica;

import bean.BGrafo;
import bean.Nodo;

public class LGrafo extends BGrafo {

    public LGrafo(String cadena) {

        cadena = cadena.replace("(", "");
        cadena = cadena.replace(")", "");
        String[] b = cadena.split(",");
        Id = new String[(b.length * 2) / 3];
        int k = 0;
        for (int i = 0; i < b.length; i += 3) {
            if (!Esta(b[i], Id)) {
                Id[k] = b[i];
                k++;
            }
            if (!Esta(b[i + 1], Id)) {
                Id[k] = b[i + 1];
                k++;
            }
        }
        Id = Redimensionar(Id);
        List = new Nodo[Id.length];
        Arista = new String[(b.length * 2) / 3];
        for (int i = 0; i < b.length; i += 3) {
            Insertar(List, b[i + 1], Integer.parseInt(b[i + 2]), BuscarIndex(b[i], Id), b[i] + b[i + 1]);
            if (!b[i].equals(b[i + 1])) {
                Insertar(List, b[i], Integer.parseInt(b[i + 2]), BuscarIndex(b[i + 1], Id), b[i] + b[i + 1]);
            }
            Arista[i / 3] = b[i] + b[i + 1];
        }
        Arista = Redimensionar(Arista);

    }

    public Nodo[] getList() {
        return List;
    }

    public void setList(Nodo[] list) {
        List = list;
    }

    public boolean Esta(String recibe, String[] verifica) {
        boolean x = false;
        for (int i = 0; i < verifica.length && verifica[i] != null; i++) {
            if (verifica[i].equals(recibe)) {
                x = true;
            }
        }
        return x;
    }

    public int BuscarIndex(String dato, String[] vector) {
        int i = 0;
        while (!vector[i].equals(dato)) {
            i++;
        }
        return i;
    }

    public String[] Redimensionar(String[] vector) {
        int i = 0;
        for (; vector[i] != null; i++) {
        }
        String[] nuevo = new String[i];
        for (int j = 0; j < i; j++) {
            nuevo[j] = vector[j];
        }
        return nuevo;
    }

    public void Insertar(Nodo[] List, String dato, int Dist, int id, String arista) {
        if (List[id] == null) {
            List[id] = new Nodo(dato, Dist, arista);
        } else {
            Nodo p = List[id];
            while (p.getLiga() != null) {
                p = p.getLiga();
            }
            p.setLi(new Nodo(dato, Dist, arista));
        }
    }

    public String MatrizAdyacencia() {
        String cadena = "";
        int[][] Matriz = new int[Id.length][Id.length];
        for (int i = 0; i < List.length; i++) {
            Nodo p = List[i];
            while (p != null) {
                if (i == BuscarIndex(p.getDato(), Id)) {
                    Matriz[i][BuscarIndex(p.getDato(), Id)]++;
                }
                Matriz[i][BuscarIndex(p.getDato(), Id)]++;
                p = p.getLiga();
            }
        }
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz[i].length; j++) {
                cadena += "[" + Matriz[i][j] + "]";
            }
            cadena += "\n";
        }
        return cadena;
    }

    public int[][] MatrizAdyacenciaint(){
        int[][] Matriz = new int[Id.length][Id.length];
        for (int i = 0; i < List.length; i++) {
            Nodo p = List[i];
            while (p != null) {
                if (i == BuscarIndex(p.getDato(), Id)) {
                    Matriz[i][BuscarIndex(p.getDato(), Id)]++;
                }
                Matriz[i][BuscarIndex(p.getDato(), Id)]++;
                p = p.getLiga();
            }
        }
        return Matriz;
    }

    public char[] Idchar(){
        char[] x = new char[Id.length];
        for (int i = 0; i < x.length; i++) {
            x[i] = Id[i].charAt(0);
        }
        return x;
    }

    public String MatrizIncidencia() {
        String cadena = "";
        int[][] Matriz = new int[Id.length][Arista.length];
        for (int i = 0; i < List.length; i++) {
            Nodo p = List[i];
            while (p != null) {
                if (i == BuscarIndex(p.getDato(), Id)) {
                    Matriz[i][BuscarIndex(p.getArista(), Arista)] = 2;
                } else {
                    Matriz[i][BuscarIndex(p.getArista(), Arista)]++;
                }
                p = p.getLiga();
            }
        }
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz[i].length; j++) {
                cadena += "[" + Matriz[i][j] + "]";
            }
            cadena += "\n";
        }
        return cadena;
    }

    public String DistanciaMinima(String Inicio, String Final) {// validacion de si existe inicio y final en el menu
        String[][] Matriz = new String[Id.length][Arista.length];
        boolean[] switches = new boolean[Id.length];
        String cadena = Inicio;
        int dist = 0, aux = 0;
        int[] Cmenor = { BuscarIndex(Inicio, Id), BuscarIndex(Inicio, Id) };
        for (int j = 0; !Id[Cmenor[1]].equals(Final); j++) {
            int i = 0;
            for (; i < Matriz.length; i++) {
                if (i == Cmenor[1]) {
                    if (j == 0) {
                        Matriz[i][j] = "(&,0)";
                    } else {
                        Matriz[i][j] = "(" + Id[Cmenor[0]] + "," + dist + ")";
                    }
                } else if (ExisteCamino(Id[Cmenor[1]], Id[i]) && !switches[i]) {
                    Matriz[i][j] = "(" + Id[Cmenor[1]] + "," + (DistanciaCamino(Id[Cmenor[1]], Id[i]) + dist) + ")";
                    // if(CaminosAnteriores(Matriz, Matriz[i][j], j, i)){
                    //     Matriz[i][j] = MenorCaminosAnteriores(Matriz, i, j);
                    // }
                } else {
                    Matriz[i][j] = "     ";
                }
            }
            aux++;
            switches[Cmenor[1]] = true;
            Cmenor = CaminoMenor(Matriz, j, switches, Cmenor, Final);
            cadena += "-"+Id[Cmenor[1]];
            dist += DistanciaCamino(Id[Cmenor[1]], Id[Cmenor[0]]);
        }
        for (int i = 0; i < Matriz.length; i++) {
            Matriz[i][aux] = "     ";
        }
        Matriz[Cmenor[1]][aux] = Matriz[Cmenor[1]][aux - 1];
        Matriz = RedimensionarMatriz(Matriz, aux);
        Matriz = ColocarSwitches(switches, Matriz);
        String d = "";
        for (int i = 0; i < Matriz.length; i++) {
            d += Id[i] + " ";
            for (int j = 0; j < Matriz[i].length; j++) {
                d += Matriz[i][j];
            }
            d += "\n";
        }
        d += cadena;
        return d;
    }

    public boolean ExisteCamino(String v1, String v2) {
        Nodo p = List[BuscarIndex(v1, Id)];
        while (p != null && !p.getDato().equals(v2)) {
            p = p.getLiga();
        }
        if (p == null) {
            return false;
        } else {
            return true;
        }
    }

    public int DistanciaCamino(String v1, String v2) {
        Nodo p = List[BuscarIndex(v1, Id)];
        while (!p.getDato().equals(v2)) {
            p = p.getLiga();
        }
        return p.getDistancia();
    }

    public int[] CaminoMenor(String[][] Matriz, int columna, boolean[] switches, int[] Cmenor, String Final) {
        String menor = "";
        for (int i = 0; i < Matriz.length; i++) {
            if (Matriz[i][columna] != null && Matriz[i][columna] != "     ") {
                if (!switches[i]) {
                    if (menor.equals("")) {
                        menor = Matriz[i][columna];
                        Cmenor[0] = Cmenor[1];
                        Cmenor[1] = i;
                    } else if (NumeroDistancia(menor) == 0) {
                        menor = Matriz[i][columna];
                        Cmenor[1] = i;
                    } else if (NumeroDistancia(Matriz[i][columna]) < NumeroDistancia(menor)
                            || Id[i].equals(Final) && NumeroDistancia(Matriz[i][columna]) <= NumeroDistancia(menor)) {
                        menor = Matriz[i][columna];
                        Cmenor[1] = i;
                    }
                }

            }
        }
        return Cmenor;
    }

    public int NumeroDistancia(String camino) {
        String n = "";
        int index = 3;
        while (isNumeric(camino.charAt(index) + "")) {
            n += camino.charAt(index);
            index++;
        }
        return Integer.parseInt(n);
    }

    public boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    public boolean CaminosAnteriores(String[][] Matriz, String camino, int columna, int fila) {
        for (int i = 0; i < columna; i++) {
            if (Matriz[fila][i] != null && Matriz[fila][i] != "     "
                    && NumeroDistancia(camino) > NumeroDistancia(Matriz[fila][i])) {
                return true;
            }
        }
        return false;
    }

    public String[][] RedimensionarMatriz(String[][] Matriz, int columna) {
        for (int i = 0; i < Matriz.length; i++) {
            String[] vector = new String[columna + 1];
            for (int j = 0; j < vector.length; j++) {
                vector[j] = Matriz[i][j];
            }
            Matriz[i] = vector;
        }
        return Matriz;
    }

    public String[][] ColocarSwitches(boolean[] switches, String[][] Matriz) {
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = Matriz[i].length - 1; j >= 0 && switches[i]; j--) {
                if (Matriz[i][j].equals("     ")) {
                    Matriz[i][j] = "  X  ";
                } else {
                    switches[i] = !switches[i];
                }
            }
        }
        return Matriz;
    }

    public String MenorCaminosAnteriores(String[][] Matriz, int fila, int columna) {
        String n = null;
        for (int i = columna-1; i >= 0 && n == null; i--) {
            if (!Matriz[fila][i].equals("     ")) {
                n = Matriz[fila][i];
            }
        }
        return n;
    }

    public boolean Existe(String vertice){
        try {
            return Id[BuscarIndex(vertice, Id)].equals(vertice);
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public void ListaDeAdyacencia() {
        for (int i = 0; i < List.length; i++) {
            System.out.print(Id[i] + " --> ");
            Nodo p = List[i];
            while (p != null) {
                System.out.print("[" + p.getDato() + ", " + p.getDistancia() + "] ");
                p = p.getLiga();
                if (p != null) {
                    System.out.print(" --> ");
                }
            }
            System.out.println();
        }
    }

    public void DFS(String PuntoDeInicio) {
        boolean[] Visto = new boolean[Id.length];
        int Punto = BuscarIndex(PuntoDeInicio, Id);
        DFSUBusqueda(Punto, Visto);
    }
    private void DFSUBusqueda(int Punto, boolean[] Visto) {
        Visto[Punto] = true;
        System.out.print("("+Id[Punto]+")" + " --> ");
        Nodo p = List[Punto];
        while (p != null) {
            int vecino = BuscarIndex(p.getDato(), Id);
            if (!Visto[vecino]) {
                DFSUBusqueda(vecino, Visto);
            }
            p = p.getLiga();
        }
    }

    public void BFS(String PuntoDeInicio) {
        boolean[] visitado = new boolean[Id.length];
        int indiceInicio = BuscarIndex(PuntoDeInicio, Id);
        int[] porVisitar = new int[Id.length];
        int Falta = 0;
        porVisitar[Falta++] = indiceInicio;
        visitado[indiceInicio] = true;
        int Actual;
        while (Falta > 0) {
            Actual = porVisitar[--Falta];
            System.out.print("(" + Id[Actual] + ") --> ");
            Nodo p = List[Actual];
            while (p != null) {
                int indiceVecino = BuscarIndex(p.getDato(), Id);
                if (!visitado[indiceVecino]) {
                    porVisitar[Falta++] = indiceVecino;
                    visitado[indiceVecino] = true;
                }
                p = p.getLiga();
            }
        }
    }

    public boolean BusquedaLimitada(String Inicio, String Meta, int LimitePasos) {
        boolean[] visitado = new boolean[Id.length];
        return BusquedaDLS(BuscarIndex(Inicio, Id), BuscarIndex(Meta, Id), LimitePasos, visitado);
    }
    
    private boolean BusquedaDLS(int Actual, int Meta, int LimitePasos, boolean[] visitado) {
        if (Actual == Meta) return true;
        if (LimitePasos == 0) return false;
    
        visitado[Actual] = true;
        Nodo InfActual = List[Actual];
        while (InfActual != null) {
            int neighbor = BuscarIndex(InfActual.getDato(), Id);
            if (!visitado[neighbor]) {
                if (BusquedaDLS(neighbor, Meta, LimitePasos - 1, visitado)) {
                    return true;
                }
            }
            InfActual = InfActual.getLiga();
        }
        return false;
    }

}
