package dao;


import dao.ObjectDBUtil;
import java.util.ArrayList;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Usuario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author FranciscoRomeroGuill
 */
public class UsuarioDAO {
    
    public void add(Usuario u){       
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.close();        
    }
    
    public void update(Usuario u){       
        
        /* primera opción: leo el usuario y lo modifico directamente */
/*        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        Usuario user = em.find(Usuario.class, u.getId());
        em.getTransaction().begin();
        user.setAlias( u.getAlias() );
        user.setPassword( u.getPassword() );
        em.getTransaction().commit();
        em.close();     
  */          
        /*segunda opción: utilizo una query con parámetros directamente */
        
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();     
        em.getTransaction().begin();
        TypedQuery<Usuario> query = em.createQuery(
            "UPDATE Usuario SET alias = :alias, password = :password WHERE id = :id", Usuario.class);
        query.setParameter("alias",u.getAlias());
        query.setParameter("password",u.getPassword());
        query.setParameter("id",u.getId());
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();        

    }
    
    public ArrayList<Usuario> getAll(){
        ArrayList<Usuario> salida;
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Usuario> q = em.createQuery("select u from Usuario u",Usuario.class);
        salida = (ArrayList<Usuario>) q.getResultList();
        em.close();
        return salida;
    }
    
    public void delete(Usuario u){
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Usuario user = em.find(Usuario.class, u.getId());
        em.remove(user);
        em.getTransaction().commit();
        em.close();        
        
    }
            
    
}
