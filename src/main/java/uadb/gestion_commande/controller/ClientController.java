package uadb.gestion_commande.controller;

import uadb.gestion_commande.entity.Client;
import uadb.gestion_commande.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        client.setNumClient(id);
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
}