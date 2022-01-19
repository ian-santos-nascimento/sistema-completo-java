package com.example.java_udemy.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.java_udemy.domain.Cidade;
import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.domain.Endereco;
import com.example.java_udemy.domain.enums.Perfil;
import com.example.java_udemy.domain.enums.TipoCliente;
import com.example.java_udemy.dto.ClienteDTO;
import com.example.java_udemy.dto.ClienteNewDTO;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.repositories.EnderecoRepository;
import com.example.java_udemy.security.UserSS;
import com.example.java_udemy.services.exception.AuthorizationException;
import com.example.java_udemy.services.exception.DataIntegrityException;
import com.example.java_udemy.services.exception.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String clientPrefix;

    public Cliente find(Integer id) {

        //Verifica se o usuário está tendando buscar algum cliente além dele mesmo
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado para buscar outros clientes");

        }
        Optional<Cliente> cliente = clienteRepo.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException(
                "Não foi possível encontrar esse cliente. Id: " + id + "Cliente" + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);  //Fazer isso pois se não for nulo, ele irá pensar que é uma atualização e não novo elemento
        obj = clienteRepo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }


    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);

        return clienteRepo.save(newObj);
    }


    public void delete(Integer id) {
        find(id);
        try {
            clienteRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não foi possível realizar essa operação de exclusão");
        }
    }


    public List<Cliente> findAll() {
        return clienteRepo.findAll();
    }

    public Page<Cliente> findPage(Integer page_id, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page_id, linesPerPage, Direction.valueOf(direction), orderBy);
        return clienteRepo.findAll(pageRequest);
    }


    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDTO) {
        Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpf_Ou_Cnpj(), TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode(objDTO.getSenha()));
        Cidade cidade = new Cidade(null, null, objDTO.getCidadeId());
        Endereco endereco = new Endereco(null, objDTO.getNome(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(objDTO.getNumero());
        if (objDTO.getTelefone() != null) {
            cliente.getTelefones().add(objDTO.getTelefone());
        }

        return cliente;
    }


    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = UserService.authenticated();
        if(user == null ){
            throw new AuthorizationException("Não é permitido inserir foto de perfil sem fazer login");
        }
        BufferedImage image = imageService.geJpgImageFromFile(multipartFile);
        String fileName = clientPrefix + user.getId() + ".jpg";
		return s3Service.uploadFile(imageService.getInputStream(image, "jpg"), fileName, "image");
    }

}
