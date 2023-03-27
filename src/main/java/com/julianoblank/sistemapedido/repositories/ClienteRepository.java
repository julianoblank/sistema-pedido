package com.julianoblank.sistemapedido.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.julianoblank.sistemapedido.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Transactional(readOnly=true)
	Cliente findByEmail(String email); // Isso o Spring Data entende que precisa fazer uma pesquisa por e-mail. Para validar emails repetidos.
}
