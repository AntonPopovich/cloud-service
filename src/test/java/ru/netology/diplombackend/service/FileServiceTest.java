package ru.netology.diplombackend.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.diplombackend.entity.FileEntity;
import ru.netology.diplombackend.entity.NewFileName;
import ru.netology.diplombackend.repository.FileRepository;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileRepository fileRepository;
    private FileService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FileService(fileRepository);
    }

    @Test
    void filesList() {
        underTest.filesList(2);
        Mockito.verify(fileRepository).getFiles(2);
    }

    @Test
    void canAddFile() {
        FileEntity file = new FileEntity(
                "myfile.png", new byte[10], 123
        );
        underTest.addFile("myfile.png", new byte[10], 123);

        ArgumentCaptor<FileEntity> fileEntityArgumentCaptor = ArgumentCaptor.forClass(FileEntity.class);

        Mockito.verify(fileRepository).save(fileEntityArgumentCaptor.capture());

        FileEntity captureFile = fileEntityArgumentCaptor.getValue();
        AssertionsForClassTypes.assertThat(captureFile).isEqualTo(file);
    }

    @Test
    void deleteFile() {
        String filename = "myfile.png";
        underTest.deleteFile(filename);
        verify(fileRepository).deleteByFilename(filename);
    }

    @Test

    void downloadFile() {
        String filename = "myfile.png";
        FileEntity file = new FileEntity(
                "myfile.png", new byte[10], 123
        );

        given(fileRepository.findByFilename(filename)).willReturn(Optional.of(file));
        underTest.downloadFile(filename);

        AssertionsForClassTypes.assertThat(file.getFilename()).isEqualTo(filename);

    }

    @Test
    void renameFile() {
        FileEntity file = new FileEntity(
                "myfile.png", new byte[10], 123
        );
        NewFileName newFilename = new NewFileName("myfile2.jpg");
        given(fileRepository.findByFilename(file.getFilename())).willReturn(Optional.of(file));
        underTest.renameFile(file.getFilename(), newFilename);

        AssertionsForClassTypes.assertThat(file.getFilename()).isEqualTo(newFilename.getFilename());
    }
}