package br.com.fluxocaixa.projetotcc.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResposta> handleNotFound(EntityNotFoundException ex){
        ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResposta> handleAccessDenied(AccessDeniedException ex){
        ErroResposta erro = new ErroResposta("Você não tem permissão para realizar esta ação.", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResposta> handleBadCredentials(BadCredentialsException ex){
        ErroResposta erro = new ErroResposta(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Ex.: e-mail/usuário duplicado (constraint UNIQUE) ou FK inválida. Não repassa a mensagem
    // crua do banco pro cliente (evita vazar detalhes internos de SQL).
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResposta> handleDataIntegrity(DataIntegrityViolationException ex){
        ErroResposta erro = new ErroResposta("Dados inválidos ou já cadastrados.", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> handleRuntimeException(RuntimeException ex){
        ErroResposta erro = new ErroResposta("Requisição inválida.", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Qualquer coisa não prevista (ex.: NullPointerException) não deve vazar stacktrace/mensagem interna.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleUnexpected(Exception ex){
        ErroResposta erro = new ErroResposta("Erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
