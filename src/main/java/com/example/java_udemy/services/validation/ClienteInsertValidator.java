package com.example.java_udemy.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.domain.enums.TipoCliente;
import com.example.java_udemy.dto.ClienteNewDTO;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.resources.exception.FieldMessage;
import com.example.java_udemy.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpf_Ou_Cnpj() )) {
			list.add(new FieldMessage("cpf_Ou_Cnpj", "CPF inválido"));
		}
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpf_Ou_Cnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email", "Este email já existe"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
