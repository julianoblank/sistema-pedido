package com.julianoblank.sistemapedido.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.julianoblank.sistemapedido.domain.Cliente;
import com.julianoblank.sistemapedido.domain.enums.TipoCliente;
import com.julianoblank.sistemapedido.dto.ClienteDTO;
import com.julianoblank.sistemapedido.repositories.ClienteRepository;
import com.julianoblank.sistemapedido.resources.exception.FieldMessage;
import com.julianoblank.sistemapedido.services.validation.utils.BR;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		// Adicionar chave e valor em um MAP, para pegar o ID da URI da requisição.
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); 
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		Cliente aux = repo.findByEmail(objDto.getEmail());
		// No Update, verifica se o email existe e se ele não pertence ao proprio cliente que esta fazendo update.
		if(aux != null && !aux.getId().equals(uriId) ) {
			list.add(new FieldMessage("email", "Email já utilizado"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
