package br.dmkm.sistema_cadastro.Repositories;

import br.dmkm.sistema_cadastro.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
