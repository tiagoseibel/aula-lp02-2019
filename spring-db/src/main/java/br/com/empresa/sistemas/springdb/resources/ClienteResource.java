package br.com.empresa.sistemas.springdb.resources;

import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.empresa.sistemas.springdb.model.Cliente;
import br.com.empresa.sistemas.springdb.repository.ClienteRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("clientes")
public class ClienteResource {

   @Autowired
   private ClienteRepository clienteRepository;

   @Autowired
   private DataSource localDataSource;

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
      return clienteRepository.pesquisaPorNome("%" + nome + "%");
   }

   @PostMapping
   public ResponseEntity<Cliente> salvar(@Valid @RequestBody Cliente cliente) {

      Cliente clienteSalvo = clienteRepository.save(cliente);

      URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
            .buildAndExpand(clienteSalvo.getCodigo()).toUri();

      return ResponseEntity.created(location).body(clienteSalvo);
   }

   @PutMapping("/{codigo}")
   public ResponseEntity<Cliente> atualizar(@PathVariable Integer codigo, @Valid @RequestBody Cliente cliente) {

      try {

         Cliente clienteSalvo = clienteRepository.findById(codigo).get();

         BeanUtils.copyProperties(cliente, clienteSalvo, "codigo");
         clienteRepository.save(clienteSalvo);

         return ResponseEntity.ok(clienteSalvo);

      } catch (NoSuchElementException e) {
         return ResponseEntity.notFound().build();
      }
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

   @GetMapping("/report")
   public ResponseEntity<byte[]> relatorioClientes() {
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

      byte[] reportBytes = null;

      try {
         InputStream io = this.getClass().getResourceAsStream("/reports/clientes_report.jasper");
         JasperPrint jasperPrint = JasperFillManager.fillReport(io, parametros, localDataSource.getConnection());

         reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
      } catch (JRException | SQLException ex) {
         return ResponseEntity.noContent().build();
      }

      return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
            // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;
            // filename=\"report.pdf\"")
            .body(reportBytes);

   }

}