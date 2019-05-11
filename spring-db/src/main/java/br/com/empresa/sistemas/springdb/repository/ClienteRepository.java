package br.com.empresa.sistemas.springdb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.empresa.sistemas.springdb.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
   public List<Cliente> findByNome(String nome);

   @Query("select c from Cliente as c where upper(c.nome) like upper(?1)")
   public List<Cliente> pesquisaPorNome(String nome);
}