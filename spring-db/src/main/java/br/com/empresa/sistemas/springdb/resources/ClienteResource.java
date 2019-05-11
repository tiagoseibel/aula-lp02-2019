package br.com.empresa.sistemas.springdb.resources;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.empresa.sistemas.springdb.model.Cliente;
import br.com.empresa.sistemas.springdb.repository.ClienteRepository;

@RestController
@RequestMapping("clientes")
public class ClienteResource {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @GetMapping("/search")
    public List<Cliente> findByNome(@RequestParam("nome") String nome) {
       return clienteRepository.findByNome(nome);
    }

    @GetMapping("/search2")
    public List<Cliente> findByNomeLike(@RequestParam("nome") String nome) {
       return clienteRepository.pesquisaPorNome("%"+nome+"%");
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {

        Cliente clienteSalvo = clienteRepository.save(cliente);

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                        .buildAndExpand(clienteSalvo.getCodigo()).toUri();

        return ResponseEntity.created(location).body(clienteSalvo);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cliente> findByCodigo(@PathVariable Integer codigo) {
        try {
            Cliente cliente = clienteRepository.findById(codigo).get();
            return ResponseEntity.ok(cliente);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Integer codigo) {
        clienteRepository.deleteById(codigo);
    }
}