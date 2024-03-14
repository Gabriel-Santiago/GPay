package BancoDigital.GPay.service;

import BancoDigital.GPay.model.Usuario;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NotificacaoService {
    public String consultaServicoAutorizador() throws IOException {
        try{
            String url = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            return "Serviço: " + response;
        }catch (IOException e){
            return "Serviço Indisponível.\n" + e.getMessage();
        }
    }

    public String enviarNotificacao(Usuario destinatario) {
        try {
            String url = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            return "Notificação enviada para " + destinatario.getNome() + ". Resposta do serviço: " + response;
        } catch (IOException e) {
            return "Erro ao enviar notificação para " + destinatario.getNome() + ": " + e.getMessage();
        }
    }
}
