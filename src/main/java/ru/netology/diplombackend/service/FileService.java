package ru.netology.diplombackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.diplombackend.entity.FileEntity;
import ru.netology.diplombackend.entity.NewFileName;
import ru.netology.diplombackend.repository.FileRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public List<FileEntity> filesList(int limit) {
        return fileRepository.getFiles(limit);
    }

    public void addFile(String fileName, byte[] data, long size) {
        fileRepository.save(new FileEntity(fileName, data, size));
    }

    public void deleteFile(String filename) {
        fileRepository.deleteByFilename(filename);
    }

    public byte[] downloadFile(String filename) {
        Optional<FileEntity> fileOpt = fileRepository.findByFilename(filename);
        FileEntity file = null;
        if (fileOpt.isPresent()) {
            file = fileOpt.get();
        }
        assert file != null;
        return file.getFile();
    }

    public void renameFile(String name, NewFileName newFileName) {
        Optional<FileEntity> findFile = fileRepository.findByFilename(name);
        FileEntity fileEntity = null;

        if (findFile.isPresent()) {
            fileEntity = findFile.get();
        }

        assert fileEntity != null;
        fileEntity.setFilename(newFileName.getFilename());
        fileRepository.save(fileEntity);

    }
}
