package br.dmkm.sistema_cadastro.Controller;

import br.dmkm.sistema_cadastro.Models.Cliente;
import br.dmkm.sistema_cadastro.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String criar(Cliente cliente, Model model) {
        try {
            Cliente savedCliente = clienteRepository.save(cliente);
            model.addAttribute("successMessage", "Cliente " + savedCliente.getNome() + " cadastrado com sucesso!\n Código do cliente: [ " + savedCliente.getId() + " ] (anote o seu código de cliente para consultas futuras).");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao cadastrar o cliente. Por favor, tente novamente.");
        }
        return "cliente-cadastro";
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "cliente-consultar";
    }

    @GetMapping("/clientes/buscar")
    public String buscarClientes(@RequestParam("tipoBusca") String tipoBusca, @RequestParam("valorBusca") String valorBusca, Model model) {
        List<Cliente> clientes;
        if (tipoBusca.equals("id")) {
            clientes = this.clienteRepository.buscarId(Integer.parseInt(valorBusca));
        } else {
            clientes = clienteRepository.findByNomeContainingIgnoreCase(valorBusca);
        }
        model.addAttribute("clientes", clientes);
        return "cliente-consultar";
    }

}