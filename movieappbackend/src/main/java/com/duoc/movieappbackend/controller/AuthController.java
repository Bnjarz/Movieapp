package com.duoc.movieappbackend.controller;

import com.duoc.movieappbackend.model.Usuario;
import com.duoc.movieappbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario nuevoUsuario) {
        System.out.println("¡RECIBIDO! Intentando guardar a: " + nuevoUsuario.getEmail());
        if (usuarioRepository.findByEmail(nuevoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya existe");
        }
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        System.out.println("¡EXITO! Usuario guardado con ID: " + usuarioGuardado.getId());
        return ResponseEntity.ok(usuarioGuardado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(usuario);
            }
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}