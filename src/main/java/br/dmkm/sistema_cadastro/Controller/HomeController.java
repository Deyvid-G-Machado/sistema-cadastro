package br.dmkm.sistema_cadastro.Controller;

import br.dmkm.sistema_cadastro.Models.Cliente;
import br.dmkm.sistema_cadastro.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
