package bean;

public class Nodo {
    private String dato;
    private Nodo Liga;
    private int Distancia;
    private String Arista;
    

    public Nodo(String dato, int distancia,String arista) {
        this.dato = dato;
        Distancia = distancia;
        Arista = arista;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Nodo getLiga() {
        return Liga;
    }

    public void setLi(Nodo li) {
        Liga = li;
    }

    public int getDistancia() {
        return Distancia;
    }

    public void setDistancia(int distancia) {
        Distancia = distancia;
    }

    public String getArista() {
        return Arista;
    }

    public void setArista(String arista) {
        Arista = arista;
    }


    
}
