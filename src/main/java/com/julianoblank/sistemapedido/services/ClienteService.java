package com.julianoblank.sistemapedido.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.julianoblank.sistemapedido.domain.Cliente;
import com.julianoblank.sistemapedido.repositories.ClienteRepository;
import com.julianoblank.sistemapedido.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException
				("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> listar() throws ObjectNotFoundException{
		try{
			List<Cliente> obj = repo.findAll();
			
			if(obj.isEmpty() == false) {
				return obj;
			}
		} catch(ObjectNotFoundException e) {
			 
		}
		throw new ObjectNotFoundException("Não há clientes cadastrados");
	}
	
}
