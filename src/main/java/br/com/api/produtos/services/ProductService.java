package br.com.api.produtos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
        }try{
            if (acao.equals("cadastrar")) {
                pm.setId(null);
                pm.setVersion(null);
                Product novoProduct = pr.save(pm);
                return new ResponseEntity<Product>(novoProduct, HttpStatus.CREATED);
            }else{
                if (pm.getId() == null || pm.getId() <=0) {
                    rm.setMensagem("Id do produto inválido para atualizações.");
                    return new ResponseEntity<Response>(rm, HttpStatus.BAD_REQUEST);
                }
                Optional<Product> produtoExistente = pr.findById(pm.getId());
                if (produtoExistente.isEmpty()) {
                    rm.setMensagem("Produto não encontrado para atualização.");
                    return new ResponseEntity<Response>(rm, HttpStatus.NOT_FOUND);
                }

                Product produtoAtualizado = pr.save(pm);
                return new ResponseEntity<Product>(produtoAtualizado, HttpStatus.OK);
            }
        }catch (ObjectOptimisticLockingFailureException e){
            rm.setMensagem("O produto foi alterado ou excluido por outra transação. Atualize os dados e tente novamente.");
            return new ResponseEntity<Response>(rm, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //remover produto por id
    public ResponseEntity<Response> remover(long id){
        pr.deleteById(id);

        rm.setMensagem("Produto removido com sucesso!");
        return new ResponseEntity<Response>(rm, HttpStatus.OK);
    }

    
}
