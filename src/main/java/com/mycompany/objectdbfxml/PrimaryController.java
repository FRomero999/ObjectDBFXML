package com.mycompany.objectdbfxml;

import dao.UsuarioDAO;
import dao.ObjectDBUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import models.Usuario;

public class PrimaryController implements Initializable {

    @FXML
    private TableColumn<Usuario, Integer> cId;
    @FXML
    private TableColumn<Usuario, String> cAlias;
    @FXML
    private TableColumn<Usuario, String> cPassword;
    @FXML
    private TableView<Usuario> tabla;
    @FXML
    private TextField txtAlias;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnGuardar;  
    
    private UsuarioDAO usuarios;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;
    
    Usuario usuarioActual = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cId.setCellValueFactory(new PropertyValueFactory("id"));
        cAlias.setCellValueFactory(new PropertyValueFactory("alias"));
        cPassword.setCellValueFactory(new PropertyValueFactory("password"));
        
        
        usuarios = new UsuarioDAO();
        
        actualizarTabla();

    }

    private void actualizarTabla() {       
        tabla.getItems().clear();
        for( Usuario u : usuarios.getAll()){
            tabla.getItems().add(u);
        }
    }
    
    @FXML
    private void guardar(ActionEvent event) {
        
        Usuario u = new Usuario();
        u.setAlias( txtAlias.getText());
        u.setPassword( txtPassword.getText() );

        usuarios.add(u);
        actualizarTabla();
        
        limpiarFormulario();

    }

    private void limpiarFormulario() {
        txtAlias.setText("");
        txtPassword.setText("");
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        
        usuarioActual = tabla.getSelectionModel().getSelectedItem();
        txtAlias.setText(usuarioActual.getAlias());
        txtPassword.setText(usuarioActual.getPassword());
        
    }

    @FXML
    private void actualizar(ActionEvent event) {
        usuarioActual.setAlias( txtAlias.getText());
        usuarioActual.setPassword( txtPassword.getText() );        
        usuarios.update(usuarioActual);
        actualizarTabla();
    }

    @FXML
    private void borrar(ActionEvent event) {
        usuarios.delete(usuarioActual);
        usuarioActual = null;
        actualizarTabla();
    }
}
