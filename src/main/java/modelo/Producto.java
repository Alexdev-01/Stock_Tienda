package modelo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;

import java.io.File;

/**veremos dos clases nuevas SimpleStringProperty y SimpleObjectProperty */
public class Producto {

	/*quitado private final********/
    private  StringProperty identificador;
    private  StringProperty nombre;
    private  StringProperty stock;
    
    //constructor all
    public Producto(String identificador, String nombre, String stock) {
        this.identificador = new SimpleStringProperty(identificador);
        this.nombre = new SimpleStringProperty(nombre);
        this.stock = new SimpleStringProperty(stock);
    }
    
    //constructor modificar
    public Producto(String nombre, String stock) {
        this.nombre = new SimpleStringProperty(nombre);
        this.stock = new SimpleStringProperty(stock);
    }
    
    //constructor2
    public  Producto() {
    	identificador= null;
    	nombre = null;
    	stock = null;
    }
    

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getIdentificador() {
        return identificador.get();
    }

    public void setIdentificador(String identificador) {
        this.identificador.set(identificador);
    }

    public StringProperty identificadorProperty() {
        return identificador;
    }

    public String getStock() {
        return stock.get();
    }

    public void setStock(String stock) {
        this.stock.set(stock);
    }

    public StringProperty stockProperty() {
        return stock;
    }

	@Override
	public String toString() {
		return "Producto [identificador=" + identificador + ", nombre=" + nombre + ", stock=" + stock + "]";
	}


}
