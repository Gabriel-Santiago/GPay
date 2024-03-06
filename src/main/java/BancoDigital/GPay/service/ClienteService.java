package BancoDigital.GPay.service;

import BancoDigital.GPay.exception.ClienteInvalidoException;
import BancoDigital.GPay.exception.EmailInvalidoException;
import BancoDigital.GPay.model.Cliente;
import BancoDigital.GPay.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    ClienteRepository clienteRepository;

    void salvarPeloCpf(Cliente entity, long cpf){
        validarCpf(cpf);
        clienteRepository.save(entity);
    }

    private void validarCpf(long value){
        String newValue = String.valueOf(value);
        if(newValue.length() != 9){
            throw new IllegalArgumentException("O CPF deve ter 9 dígitos.");
        }
    }

    void salvarPeloEmail(Cliente entity, String email){
        validarEmail(email);
        clienteRepository.save(entity);
    }

    private static final String REGEX_EMAIL =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private void validarEmail(String email) throws EmailInvalidoException {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new EmailInvalidoException("Email inválido!");
        }
    }

    Cliente findByCpf(long cpf){
        validarCpf(cpf);

        Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);

        return cliente.orElse(null);
    }

    Cliente findByEmail(String email){
        validarEmail(email);

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
            throw new ClienteInvalidoException("Cliente com CPF " + cpf + " não encontrado");
        }
    }

    void deleteByEmail(String email){
        Cliente cliente = findByEmail(email);

        if(cliente != null){
            clienteRepository.delete(cliente);
        }else {
            throw new ClienteInvalidoException("Cliente com Email " + email + " não encontrado");
        }
    }

    void uptadeByCpf(Cliente entity, long cpf){
        Cliente cliente = findByCpf(cpf);

        if(cliente != null){
            cliente.setCpf(entity.getCpf());
            clienteRepository.save(cliente);
        }else {
            throw new ClienteInvalidoException("Cliente com CPF " + cpf + " não encontrado");
        }
    }

    void updateByEmail(Cliente entity, String email){
        Cliente cliente = findByEmail(email);

        if(cliente != null){
            cliente.setEmail(entity.getEmail());
            clienteRepository.save(cliente);
        }else {
            throw new ClienteInvalidoException("Cliente com Email " + email + " não encontrado");
        }
    }

    List<Cliente> findByNome(String nome){
        return clienteRepository.findByNome(nome);
    }
}
