package BancoDigital.GPay.service;

import BancoDigital.GPay.exception.CnpjInvalidoException;
import BancoDigital.GPay.exception.EmailInvalidoException;
import BancoDigital.GPay.model.Lojista;
import BancoDigital.GPay.repository.LojistaRepository;
import BancoDigital.GPay.validacoes.ValidarCnpj;
import BancoDigital.GPay.validacoes.ValidarEmail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LojistaService {

    LojistaRepository lojistaRepository;

    ValidarCnpj validarCnpj = new ValidarCnpj();
    ValidarEmail validarEmail = new ValidarEmail();

    void salvarPeloCnpj(Lojista entity, long cnpj){
        validarCnpj.validacaoCnpj(cnpj);
        lojistaRepository.save(entity);
    }

    void salvarPeloEmail(Lojista entity, String email){
        validarEmail.validacaoEmail(email);
        lojistaRepository.save(entity);
    }

    Lojista findByCnpj(long cnpj){
        validarCnpj.validacaoCnpj(cnpj);

        Optional<Lojista> lojista = lojistaRepository.findByCnpj(cnpj);

        return lojista.orElse(null);
    }

    Lojista findByEmail(String email){
        validarEmail.validacaoEmail(email);

        Optional<Lojista> lojista = lojistaRepository.findByEmail(email);

        return lojista.orElse(null);
    }

    List<Lojista> findAll(){
        return lojistaRepository.findAll();
    }

    void deleteByCnpj(long cnpj){
        Lojista lojista = findByCnpj(cnpj);

        if(lojista != null){
            lojistaRepository.delete(lojista);
        }else {
            throw new CnpjInvalidoException("Lojista com CNPJ " + cnpj + " não encontrado");
        }
    }

    void deleteByEmail(String email){
        Lojista lojista = findByEmail(email);

        if(lojista != null){
            lojistaRepository.delete(lojista);
        }else {
            throw new EmailInvalidoException("Lojista com Email " + email + " não encontrado");
        }
    }

    void updateByCnpj(Lojista entity, long cnpj){
        Lojista lojista = findByCnpj(cnpj);

        if(lojista != null){
            lojista.setCnpj(entity.getCnpj());
            validarCnpj.validacaoCnpj(lojista.getCnpj());
            lojistaRepository.save(lojista);
        }else {
            throw new CnpjInvalidoException("Lojista com CNPJ " + cnpj + " não encontrado");
        }
    }

    void updateByEmail(Lojista entity, String email){
        Lojista lojista = findByEmail(email);

        if(lojista != null){
            lojista.setEmail(entity.getEmail());
            validarEmail.validacaoEmail(lojista.getEmail());
            lojistaRepository.save(lojista);
        }else {
            throw new EmailInvalidoException("Lojista com Email " + email + " não encontrado");
        }
    }

    List<Lojista> findByNome(String nome){
        return lojistaRepository.findByNome(nome);
    }
}
