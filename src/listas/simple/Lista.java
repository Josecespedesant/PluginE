package listas.simple;



public class Lista<type> {
	protected Nodo<type> inicio, fin;//punteros al inicio y final
	protected Lista() {
		this.inicio = null;
		this.fin = null;
	}
	
	//agregar nodo asl inicio de la lista
	public void agregarAlInicio(type valor) {
		this.inicio = new Nodo(valor, inicio);
		if (fin==null) {
			fin=inicio;
		}
			
	}
	
	//agrega un nodo al final de la lista
		public void agregarAlFinal(type valor) {
			if(!estaraVacia()) {
				fin.siguiente=new Nodo(valor);
				fin=fin.siguiente;
			}else {
				inicio=fin=new Nodo(valor);
			}
		}
		
	//Borra un elemento del inicio de la Lista
	public void eliminarDelInicio() {
			if (inicio==fin) {
				inicio=fin=null;
			}else {
				inicio=inicio.siguiente;
			}
		} 
	
	//Borra elemento del final
	public void eliminarDelFinal() {
		if (inicio==fin) {
			inicio=fin=null;
		}else {
			Nodo<type> buscafinal = inicio;
			while(buscafinal.siguiente != fin) {
				buscafinal=buscafinal.siguiente;
			}
			fin=buscafinal;
			fin.siguiente=null;
		}
	}
	
	//metodo para eliminar elemento
	public void eliminarNodoPorElemento(type elemento) {
		Nodo<type> parseador = inicio; 
		if(inicio.valor==elemento) {
			eliminarDelInicio();
		}else if(fin.valor==elemento) {
			eliminarDelFinal();
		}else {
			while(parseador.siguiente != null) {
				if(parseador.siguiente.valor == elemento) {
					parseador.siguiente=parseador.siguiente.siguiente;
				} 
				parseador = parseador.siguiente;
			}
		} 
		} 

	
	//metodo para mostrar los datos
	public void mostrarLista() {
			Nodo recorrer = inicio;
			while (recorrer!=null) {
				System.out.println("["+recorrer.valor+"]---->");
				recorrer = recorrer.siguiente;
			}
	}
	
	
	//se fija si la lista esa vacia 
	public boolean estaraVacia() {
		if (inicio == null) {
			return true;
		}
		return false;
	}
	
	}
	









