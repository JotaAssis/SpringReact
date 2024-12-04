package br.com.api.produtos.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.api.produtos.entities.Product;
import br.com.api.produtos.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ProcuctController {

    @Autowired
    private ProductService ps;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Product pm){
        return ps.cadastrarAlterar(pm, "cadastrar");
    }


    //lista de produtos
    @GetMapping("/listar")
    public Iterable<Product>listar(){
        return ps.Listar();
    }
    

    @GetMapping("/")
    public String rota(){
        return "API produtos";
    }
}
