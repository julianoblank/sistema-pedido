package com.julianoblank.sistemapedido.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>(); // tem essa nome, porque o springboot utiliza ela para mostrar quando tem erro.
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors() { // tem essa assinatura, porque o springboot utiliza ela para mostrar quando tem erro.
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName,message));
	}
	

}
