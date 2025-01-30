package com.coderhouse.services;

import com.coderhouse.models.*;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Cliente> getAllClientes(){
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encuentra cliente con ID: " + id));
    }

    @Transactional
    public Cliente saveCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente updateClienteById(Long id, Cliente clienteDetails){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encuentra cliente con ID: " + id));

        cliente.setNombre(clienteDetails.getNombre());
        cliente.setApellido(clienteDetails.getApellido());
        

        if(clienteDetails.getDni() != 0){
            cliente.setDni(clienteDetails.getDni());
        }

                return clienteRepository.save(cliente);
    }

    public void deleteClienteById(Long id){
        if(!clienteRepository.existsById(id)){
            throw new IllegalArgumentException("No se encuentra cliente con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}