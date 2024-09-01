package br.dmkm.sistema_cadastro.Repositories;

import br.dmkm.sistema_cadastro.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);


    @Query(value = "SELECT * FROM tb_cliente WHERE id = :id", nativeQuery = true)
    public List<Cliente> buscarId(Integer id);


}
