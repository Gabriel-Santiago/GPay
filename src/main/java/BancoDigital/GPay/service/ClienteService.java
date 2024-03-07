package BancoDigital.GPay.service;

import BancoDigital.GPay.exception.CpfInvalidoException;
import BancoDigital.GPay.exception.EmailInvalidoException;
import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.repository.ClienteRepository;
import BancoDigital.GPay.validacoes.ValidarCpf;
import BancoDigital.GPay.validacoes.ValidarEmail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    ClienteRepository clienteRepository;

    ValidarCpf validarCpf = new ValidarCpf();
    ValidarEmail validarEmail = new ValidarEmail();

    void salvarPeloCpf(Cliente entity, long cpf){
        validarCpf.validacaoCpf(cpf);
        clienteRepository.save(entity);
    }

    void salvarPeloEmail(Cliente entity, String email){
        validarEmail.validacaoEmail(email);
        clienteRepository.save(entity);
    }

    Cliente findByCpf(long cpf){
        validarCpf.validacaoCpf(cpf);

        Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);

        return cliente.orElse(null);
    }

    Cliente findByEmail(String email){
        validarEmail.validacaoEmail(email);

        Optional<Cliente> cliente = clienteRepository.findByEmail(email);

        return cliente.orElse(null);
    }

    List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    void deleteByCpf(long cpf){
        Cliente cliente = findByCpf(cpf);

        if(cliente != null){
            clienteRepository.delete(cliente);
        } else {
            throw new CpfInvalidoException("Cliente com CPF " + cpf + " não encontrado");
        }
    }

    void deleteByEmail(String email){
        Cliente cliente = findByEmail(email);

        if(cliente != null){
            clienteRepository.delete(cliente);
        }else {
            throw new EmailInvalidoException("Cliente com Email " + email + " não encontrado");
        }
    }

    void uptadeByCpf(Cliente entity, long cpf){
        Cliente cliente = findByCpf(cpf);

        if(cliente != null){
            cliente.setCpf(entity.getCpf());
            validarCpf.validacaoCpf(cliente.getCpf());
            clienteRepository.save(cliente);
        }else {
            throw new CpfInvalidoException("Cliente com CPF " + cpf + " não encontrado");
        }
    }

    void updateByEmail(Cliente entity, String email){
        Cliente cliente = findByEmail(email);

        if(cliente != null){
            cliente.setEmail(entity.getEmail());
            validarEmail.validacaoEmail(cliente.getEmail());
            clienteRepository.save(cliente);
        }else {
            throw new EmailInvalidoException("Cliente com Email " + email + " não encontrado");
        }
    }

    List<Cliente> findByNome(String nome){
        return clienteRepository.findByNome(nome);
    }
}
