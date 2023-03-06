package ru.netology.diplombackend.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplombackend.entity.NewFileName;
import ru.netology.diplombackend.service.AuthenticationService;
import ru.netology.diplombackend.service.FileService;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public void uploadFile(
            @RequestHeader("auth-token")
            @NotBlank String authToken,
            @NotBlank String filename,
            @RequestBody MultipartFile file) throws IOException {
        if (authenticationService.isTokenExist(authToken)) {
            fileService.addFile(filename, file.getBytes(), file.getSize());
        } else {
            throw new AuthenticationException("Регистрация недействительна. Пожалуйста, пройдите регистрацию заново.");
        }
    }

    @SneakyThrows
    @DeleteMapping
    public void deleteFile(
            @RequestHeader("auth-token")
            @NotBlank String token,
            @RequestParam String filename) {

        if (authenticationService.isTokenExist(token)) {
            fileService.deleteFile(filename);
        } else {
            throw new AuthenticationException("Регистрация недействительна. Пожалуйста, пройдите регистрацию заново.");
        }
    }

    @SneakyThrows
    @GetMapping
    public ResponseEntity<byte[]> downloadFile(
            @RequestHeader("auth-token") String token,
            @RequestParam String filename
    ) {
        if (authenticationService.isTokenExist(token)) {
            return ResponseEntity.ok(fileService.downloadFile(filename));
        } else {
            throw new AuthenticationException("Регистрация недействительна. Пожалуйста, пройдите регистрацию заново.");
        }

    }

    @SneakyThrows
    @PutMapping
    public ResponseEntity<String> renameFile(
            @RequestHeader("auth-token") String token,
            @RequestParam String filename,
            @RequestBody NewFileName newFileName
    ) {
        if (authenticationService.isTokenExist(token)) {
            fileService.renameFile(filename, newFileName);
            return ResponseEntity.ok("Succesfull rename file");
        } else {
            throw new AuthenticationException("Регистрация недействительна. Пожалуйста, пройдите регистрацию заново.");
        }
    }
}
