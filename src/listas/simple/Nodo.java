package listas.simple;


public class Nodo<type> {
	public type valor;
	public Nodo siguiente; //puntero enlace
	// constructor para insertar al final
	public Nodo(type valor) {
		this.valor = valor;
	}
	// constructor para insertar al inicio
	public Nodo(type valor, Nodo sig) {
		this.valor = valor;
		this.siguiente = sig;
	}
	

	
	

}

