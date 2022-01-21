package com.example.java_udemy.resources;

import com.example.java_udemy.domain.Cidade;
import com.example.java_udemy.domain.Estado;
import com.example.java_udemy.dto.CidadeDTO;
import com.example.java_udemy.dto.EstadoDTO;
import com.example.java_udemy.services.CidadeService;
import com.example.java_udemy.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> estadoList = estadoService.findAll();
        List<EstadoDTO> estadoDTOList = estadoList.stream().map(x ->  new EstadoDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadoDTOList);
    }
    
    @RequestMapping(value = "{estado_id}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estado_id){
        List<Cidade> cidadeList = cidadeService.findAllByEstado(estado_id);
        List<CidadeDTO> cidadeDTOList = cidadeList.stream().map(x -> new CidadeDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadeDTOList);
    }
}
