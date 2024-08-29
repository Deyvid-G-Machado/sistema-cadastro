package br.dmkm.sistema_cadastro.Controller;

import br.dmkm.sistema_cadastro.Models.Cliente;
import br.dmkm.sistema_cadastro.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;


    @GetMapping("/cadastrar-cliente")
    public String cadastro() {
        return "cliente-cadastro";
    }
    @PostMapping("/cadastrar-cliente/cadastrar")
    public String criar(Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/";
    }

    /*
        @PostMapping("/usuario/criar")
    public String criar(Usuario usuario){
        usuarioRepository.save(usuario);
        return "redirect:/";
    }
     */
}
