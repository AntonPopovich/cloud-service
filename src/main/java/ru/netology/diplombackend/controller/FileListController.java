package ru.netology.diplombackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.netology.diplombackend.entity.FileEntity;
import ru.netology.diplombackend.service.AuthenticationService;
import ru.netology.diplombackend.service.FileService;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class FileListController {

    public final FileService fileService;
    private final AuthenticationService authenticationService;

    @SneakyThrows
    @GetMapping
    public List<FileEntity> listOfAllFiles (
            @RequestHeader("auth-token") String token,
            @RequestParam int limit
    ) {
        if(authenticationService.isTokenExist(token)) {
            return fileService.filesList(limit);
        } else {
            throw new AuthenticationException();
        }

    }
}
