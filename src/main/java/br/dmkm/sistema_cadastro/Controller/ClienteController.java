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
import java.text.DecimalFormat;
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
        if (cliente.getPeso() == null || cliente.getPeso() <= 0 || cliente.getAltura() == null || cliente.getAltura() <= 0) {
            model.addAttribute("errorMessage", "Altura e Peso precisam ser diferentes de zero(0).");
            return "cliente-cadastro";
        }
        if(cliente.getRendaPessoal() == null || cliente.getRendaPessoal() <=0){
            model.addAttribute("errorMessage", "Renda precisa ser diferente de zero(0)");
            return "cliente-cadastro";
        }

        try {
            // Remove espaços extras do nome
            cliente.setNome(cliente.getNome().trim().replaceAll("\\s+", " "));
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

    @GetMapping("/clientes/calcularIMC")
    public String calcularIMC(
            @RequestParam(value = "clienteId", required = false) Long clienteId,
            @RequestParam(value = "peso", required = false) Double peso,
            @RequestParam(value = "altura", required = false) Double altura,
            Model model) {

        if (clienteId == null) {
            model.addAttribute("errorMessage", "Código do cliente é obrigatório.");
            return "cliente-imc";
        }

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            model.addAttribute("errorMessage", "Cliente não encontrado.");
            return "cliente-imc";
        }

        if (peso == null) {
            peso = cliente.getPeso();
        }
        if (altura == null) {
            altura = cliente.getAltura();
        }

        if (peso == null || altura == null || altura == 0) {
            model.addAttribute("errorMessage", "Peso ou altura inválidos.");
            return "cliente-imc";
        }
        double alturaEmMetros = altura / 100.0;
        double imc = peso / (alturaEmMetros * alturaEmMetros); // Cálculo do IMC

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String imcFormatado = decimalFormat.format(imc);

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
        model.addAttribute("imc", imcFormatado);
        model.addAttribute("classificacao", classificacao);

        return "cliente-imc";
    }
}
