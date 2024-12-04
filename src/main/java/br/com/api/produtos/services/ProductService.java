package br.com.api.produtos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.produtos.entities.Product;
import br.com.api.produtos.entities.Response;
import br.com.api.produtos.repositories.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository pr;

    @Autowired
    private Response rm;
    
    //Listar todos
    public Iterable<Product> Listar(){
        return pr.findAll();
    }

    // Método para cadastrar ou alterar produtos
    public ResponseEntity<?> cadastrarAlterar(Product pm, String acao){
        if(pm.getNome().equals("")){
            rm.setMensagem("O nome do produto é obrigatório");
            return new ResponseEntity<Response>(rm, HttpStatus.BAD_REQUEST);
        }else if (pm.getMarca().equals("")) {
            rm.setMensagem("O nome da marca é obrigatório");
            return new ResponseEntity<Response>(rm, HttpStatus.BAD_REQUEST);
        }else{
            if (acao.equals("cadastrar")) {
                return new ResponseEntity<Product>(pr.save(pm), HttpStatus.CREATED);
            }else{
                return new ResponseEntity<Product>(pr.save(pm), HttpStatus.OK);
            }
        }
    }

    //remover produto por id
    public ResponseEntity<Response> remover(long id){
        pr.deleteById(id);

        rm.setMensagem("Produto removido com sucesso!");
        return new ResponseEntity<Response>(rm, HttpStatus.OK);
    }

    
}
