package controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Producto;

public class MainController implements Initializable{

    @FXML
    private TableColumn<Producto, String> identificadorColumn;	//se <indica la Class y tipo de dato>

    @FXML
    private TextField identificadorField;

    @FXML
    private TableColumn<Producto, String> nombreColumn;	//se <indica la Class y tipo de dato>

    @FXML
    private TextField nombreField;

    @FXML
    private TableColumn<Producto, String> stockColumn;	//se <indica la Class y tipo de dato>

    @FXML
    private TextField stockField;
    
    @FXML
    private TableView<Producto> tablaProductos;
    
    
    //indicara la posicon del producto al marcar su identificador en la tabla
    private int posicionProductoTabla; 
    
    //nuevo objeto Producto, para agrega a la tabla
    private Producto nuevoProducto;
    
    //ObservableList es una lista q se va actualizar automaticamente de los productos
    private ObservableList<Producto> productList = FXCollections.observableArrayList();

    
    /**metodo agregar producto del boton "Agregar"**/
    @FXML
    void handleAgregar(ActionEvent event) {
    	System.out.println("marcado Boton Agregar");
    	
    	//si esta vacio un campo de TextField no se crea producto
    	if (identificadorField.getText().isEmpty() || nombreField.getText().isEmpty() || stockField.getText().isEmpty()) {
        	System.out.println("Falta por completar datos");
        	
        	//mensaje de aviso de campo sin rellenar 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Faltan campos por rellenar");
            alert.showAndWait();
        
        //si estan rellenos los TextField se crea un producto
		} else if (!identificadorField.getText().isEmpty() || !nombreField.getText().isEmpty() || !stockField.getText().isEmpty()) {
        	//se crea el nuevo producto		
        	String identificador = identificadorField.getText();
        	String nombre=nombreField.getText();
        	String stock=stockField.getText();
        	nuevoProducto=new Producto(identificador,nombre,stock);
        	System.out.println(nuevoProducto);
        	
        	//creamos un identificador boolean con el resultado que de el forEach que comprueba si el "indicador" del nuevo producto coincide con uno ya existente en la tableView
        	boolean identificadorRepe = false;
        	for (Producto producto : productList) {
				if (producto.getIdentificador().equals(identificador)) {
					identificadorRepe = true;
					System.out.println("Ya existe el identificador: " + identificador);
					break;
				}
			}
        	/*//otra forma de comprobar si existe el mismo identificador, expresion lambda
        	if (productList.stream().anyMatch(producto -> producto.getIdentificador().equals(identificador))) {
				System.out.println("Ya existe el identificador: " + identificador);
			}*/
        	
        	//si el identidficador existe salta alerta
        	if (identificadorRepe) {
            	//mensaje de aviso de identificador ya existe
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Ya existe el identificador:  " + identificador);
                alert.showAndWait();
            //si el identificador no exite se añade el nuevo producto a la tableView
			}else if (!identificadorRepe) {
	        	//carga el nuevo producto con la class ObservableList actualiza automaticamente, tenemos q asociar esta con la TableView<Producto>
	        	productList.add(nuevoProducto);
	        	
	           	//al dar al boton "Agregar" se borra los campos de TextField
	        	identificadorField.clear();
	        	nombreField.clear();
	        	stockField.clear();
			}
		}
    }
  
    
    
    /**metodo para seleccionar una celda de la tableView */
    public Producto getTablaProductosSeleccionada() {
        if (tablaProductos != null) {
            List<Producto> tabla = tablaProductos.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Producto productoSelect = tabla.get(0);
                return productoSelect;
            }
        }
        return null;
    }

    /**Método para poner en los textFields los datos que seleccionemos*/
    private void ponerProductoSeleccionado() {
        final Producto producto = getTablaProductosSeleccionada();
        posicionProductoTabla = productList.indexOf(producto);

        if (producto != null) {

            // Pongo los textFields con los datos correspondientes
            identificadorField.setText(producto.getIdentificador());
            nombreField.setText(producto.getNombre());
            stockField.setText(producto.getStock());
        }
    }

    
    
    
    /**metodo modificar producto del boton "Modificar" **/
    @FXML
    void handleModificar(ActionEvent event) {
    	System.out.println("marcado Boton Modificar");
    	
    	//Creamos un objeto Producto q recoge los valores del producto seleccionado en la TableView
    	Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
    	
        if (productoSeleccionado != null) {
            //recoge los valores de los TextFields de nombre y stock
            String nuevoNombre = nombreField.getText();
            String nuevoStock = stockField.getText();

            //Modifica el nombre y el stock del producto seleccionado
            productoSeleccionado.setNombre(nuevoNombre);
            productoSeleccionado.setStock(nuevoStock);

            //Actualiza la TableView con los nuevos valores
            tablaProductos.refresh();

            System.out.println("Producto modificado: " + productoSeleccionado);

            // Limpiar los campos de texto
            identificadorField.clear();
            nombreField.clear();
            stockField.clear();
            
        } else {
            //mensaje de aviso al no selecionar ningún producto
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un producto primero para modificar");
            alert.showAndWait();
        }
    }

    
    /**Listener de la tabla productos*/
    private final ListChangeListener<Producto> selectorTablaProducto =
            new ListChangeListener<Producto>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Producto> c) {
                	ponerProductoSeleccionado();
                }
            };
    
    /*Inicializable
     * Inicializamos la TableView y asociar la lista ObservableList*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		identificadorColumn.setCellValueFactory(cellData -> cellData.getValue().identificadorProperty());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        stockColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty());

        tablaProductos.setItems(productList);	//asociamos la TableView con el ObservableList        
        
        //Seleccionar las filas de la tableView
        final ObservableList<Producto> tablaProductoSeleccionado = tablaProductos.getSelectionModel().getSelectedItems();
        tablaProductoSeleccionado.addListener(selectorTablaProducto);

	}
}
	
