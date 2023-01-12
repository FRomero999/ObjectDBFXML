package com.mycompany.objectdbfxml;

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

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("data.odb");
    
    
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManager em = emf.createEntityManager();
        System.out.println("Conexión realizada con exito");
        em.close();
        
        cId.setCellValueFactory(new PropertyValueFactory("id"));
        cAlias.setCellValueFactory(new PropertyValueFactory("alias"));
        cPassword.setCellValueFactory(new PropertyValueFactory("password"));
          
        actualizarTabla();
        
    }

    private void actualizarTabla() {
        tabla.getItems().clear();
        EntityManager em=emf.createEntityManager();
        TypedQuery<Usuario> q = em.createQuery("select u from Usuario u",Usuario.class);
        for( Usuario u : q.getResultList()){
            tabla.getItems().add(u);
        }
        em.close();
    }

    private void actualizarTabla(EntityManager em) {       
        tabla.getItems().clear();
        TypedQuery<Usuario> q = em.createQuery("select u from Usuario u",Usuario.class);
        for( Usuario u : q.getResultList()){
            tabla.getItems().add(u);
        }
    }
    
    @FXML
    private void guardar(ActionEvent event) {
        
        Usuario u = new Usuario();
        u.setAlias( txtAlias.getText());
        u.setPassword( txtPassword.getText() );

        var em=emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        actualizarTabla(em);
        em.close();

    }
}
