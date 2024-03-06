package BancoDigital.GPay.service;

import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.repository.LojistaRepository;
import BancoDigital.GPay.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferenciaService {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LojistaService lojistaService;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    public void realizarTransferencia(Long remetenteId, Long destinatarioId, BigDecimal valor){

    }
}
