/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.services;


import java.util.List;
import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.entities.Seller;


public class SellerService {
    
   private SellerDao dao = DaoFactory.createSellerDao();
    
    public List<Seller> findAll(){
        return dao.findAll();
    }
    //estamos inserindo ou atualizando, caso ID estiver preenchido, ele entra no else, 
    //chamando o metodo de atualização
    public void  saveOrUpdate(Seller obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else{
            dao.update(obj);
        }
    }
    
    public void remove(Seller obj){
        dao.deleteById(obj.getId());
    }
    
}
