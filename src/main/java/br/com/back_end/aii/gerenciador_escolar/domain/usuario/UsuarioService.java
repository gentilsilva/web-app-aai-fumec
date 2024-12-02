package br.com.back_end.aii.gerenciador_escolar.domain.usuario;

import br.com.back_end.aii.gerenciador_escolar.domain.professor.Professor;
import br.com.back_end.aii.gerenciador_escolar.domain.professor.ProfessorRepository;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.Universitario;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.UniversitarioRepository;
import br.com.back_end.aii.gerenciador_escolar.domain.universitario.UniversitarioService;
import br.com.back_end.aii.gerenciador_escolar.domain.usuario.email.EmailService;
import br.com.back_end.aii.gerenciador_escolar.infra.exception.RegraDeNegocioException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final ProfessorRepository professorRepository;
    private final UniversitarioRepository universitarioRepository;


    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, EmailService emailService, ProfessorRepository professorRepository, UniversitarioRepository universitarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.professorRepository = professorRepository;
        this.universitarioRepository = universitarioRepository;
    }

    public Long salvarUsuario(String nome, String email, String emailPessoal, String senha, Perfil perfil) {
        String senhaCriptografada = passwordEncoder.encode(senha);
        Usuario usuario = usuarioRepository.save(new Usuario(nome, email, senhaCriptografada, perfil));

        if(usuario.getPerfil() == Perfil.PROFESSOR) {
            emailService.enviarEmailUsuario(usuario, senha, emailPessoal);
        } else if(usuario.getPerfil() == Perfil.UNIVERSITARIO) {
            emailService.enviarEmailUsuario(usuario, senha, emailPessoal);
        }

        return usuario.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void alterarSenha(DadosAlteracaoSenha dados, Usuario logado){
        if(!passwordEncoder.matches(dados.senhaAtual(), logado.getPassword())){
            throw new RegraDeNegocioException("Senha digitada não confere com senha atual!");
        }

        if(!dados.novaSenha().equals(dados.novaSenhaConfirmacao())){
            throw new RegraDeNegocioException("Senha e confirmação não conferem!");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.novaSenha());
        logado.alterarSenha(senhaCriptografada);

        usuarioRepository.save(logado);
    }

    public void enviarToken(String email){
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new RegraDeNegocioException("Usuário não encontrado!")
        );
        String token = UUID.randomUUID().toString();
        usuario.setToken(token);
        usuario.setExpiracaoToken(LocalDateTime.now().plusMinutes(15));

        usuarioRepository.save(usuario);

        if(usuario.getPerfil() == Perfil.PROFESSOR) {
            Professor professor = professorRepository.findByIdAndAtivoTrue(usuario.getId()).orElseThrow(
                    () -> new RegraDeNegocioException("Usuário não encotrado"));
            emailService.enviarEmailSenha(usuario, professor.getEmail());
        } else if(usuario.getPerfil() == Perfil.UNIVERSITARIO) {
            Universitario universitario = universitarioRepository.findByIdAndAtivoTrue(usuario.getId()).orElseThrow(
                    () -> new RegraDeNegocioException("Usuário não encontrado"));
            emailService.enviarEmailSenha(usuario, universitario.getEmail());
        }
    }

    public void recuperarConta(String codigo, DadosRecuperacaoConta dados) {
        Usuario usuario = usuarioRepository.findByTokenIgnoreCase(codigo).orElseThrow(
                () -> new RegraDeNegocioException("Usuário não encontrado")
        );

        if(usuario.getExpiracaoToken().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Token expirado!");
        }

        if(!dados.novaSenha().equals(dados.novaSenhaConfirmacao())) {
            throw new RegraDeNegocioException("Senhas não coicidem.");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.novaSenha());
        usuario.alterarSenha(senhaCriptografada);
        usuario.setToken(null);
        usuario.setExpiracaoToken(null);

        usuarioRepository.save(usuario);
    }
}
