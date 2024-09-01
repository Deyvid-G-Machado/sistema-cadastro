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

import java.text.AttributedString;
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

    @GetMapping("/clientes/cliente-calcularIMC")
    public String calcularIMC(
            @RequestParam("clienteId") Long clienteId,
            Model model) {

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            model.addAttribute("errorMessage", "Cliente não encontrado.");
            return "cliente-calcularIMC";
        }

        double peso = cliente.getPeso();
        double altura = cliente.getAltura();

        double imc = peso / (altura * altura); // Cálculo do IMC

        String classificacao;
        if (imc < 18.5) {
            classificacao = "Abaixo do peso";
        } else if (imc < 24.9) {
            classificacao = "Peso normal";
        } else if (imc < 29.9) {
            classificacao = "Sobrepeso";
        } else {
            classificacao = "Obesidade";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("imc", imc);
        model.addAttribute("classificacao", classificacao);

        return "cliente-calcularIMC";
    }


}