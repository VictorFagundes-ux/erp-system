package com.erp.erp_system.client.service;

import com.erp.erp_system.client.model.Client;
import com.erp.erp_system.client.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    public Client saveClient(Client client) {
        // Validação simples para evitar duplicidade de documento (CPF/CNPJ)
        clientRepository.findByDocument(client.getDocument()).ifPresent(existing -> {
            if (client.getId() == null || !existing.getId().equals(client.getId())) {
                throw new RuntimeException("Client with document " + client.getDocument() + " already exists.");
            }
        });
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        Client client = getClientById(id);
        clientRepository.delete(client);
    }
}

