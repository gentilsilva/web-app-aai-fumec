package br.com.back_end.aii.gerenciador_escolar.domain.usuario.email;

import br.com.back_end.aii.gerenciador_escolar.domain.usuario.Usuario;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender enviadorEmail;
    private static final String EMAIL_ORIGEM = "scgest@email.com";
    private static final String NOME_ENVIADOR = "Gestão Escolar ScGest";

    public static final String URL_SITE = "http://localhost:8080";

    public EmailService(JavaMailSender enviadorEmail) {
        this.enviadorEmail = enviadorEmail;
    }

    @Async
    private void enviarEmail(String assunto, String conteudo, String email) {
        MimeMessage message = enviadorEmail.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(EMAIL_ORIGEM, NOME_ENVIADOR);
            helper.setTo(email);
            helper.setSubject(assunto);
            helper.setText(conteudo, true);
            enviadorEmail.send(message);
        } catch(MessagingException | UnsupportedEncodingException e){
            throw new RegraDeNegocioException("Erro ao enviar email");
        }
    }

    public void enviarEmailSenha(Usuario usuario, String email) {
        String assunto = "Aqui está seu link para alterar a senha";
        String conteudo = gerarConteudoEmail("Olá [[name]],<br>"
                + "Por favor clique no link abaixo para alterar a senha:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ALTERAR</a></h3>"
                + "Obrigado,<br>"
                + "ScGest.", usuario.getNome(), URL_SITE + "/recuperar-conta?codigo=" + usuario.getToken());

        enviarEmail(assunto, conteudo, email);
    }

    public void enviarEmailUsuario(Usuario usuario, String senha, String email) {
        String assunto = "Aqui estão as informações de login";
        String conteudo = gerarConteudoEmail("Olá [[name]],<br>"
                + "Segue as informações de login: <br>"
                + "Login: " + usuario.getUsername() +"<br>"
                + "Senha: " + senha + "<br>"
                + "Obrigado,<br>"
                + "ScGest.", usuario.getNome(), URL_SITE + "/recuperar-conta?codigo=" + usuario.getToken());

        enviarEmail(assunto, conteudo, email);
    }

    private String gerarConteudoEmail(String template, String nome, String url) {
        return template.replace("[[name]]", nome).replace("[[URL]]", url);
    }

}
