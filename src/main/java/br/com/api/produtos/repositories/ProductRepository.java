package br.com.api.produtos.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.api.produtos.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
 
}