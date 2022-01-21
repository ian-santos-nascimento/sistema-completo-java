package com.example.java_udemy.services;

import com.example.java_udemy.domain.Cidade;
import com.example.java_udemy.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findAllByEstado(Integer estado_id){
        return cidadeRepository.findCidades(estado_id);
    }
}
