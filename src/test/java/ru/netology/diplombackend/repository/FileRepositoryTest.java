package ru.netology.diplombackend.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.diplombackend.entity.FileEntity;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class FileRepositoryTest {

    @Autowired
    private FileRepository underTest;

    @Test
    void itShouldGetFiles() {
        FileEntity file = new FileEntity(
                "myfile.png", new byte[10], 123
        );
        underTest.save(file);
        List<FileEntity> files = underTest.getFiles(1);
        AssertionsForClassTypes.assertThat(files.size()).isEqualTo(1);
    }

    @Test
    void itShouldDeleteByFilename() {
        FileEntity file = new FileEntity(
            "myfile.png", new byte[10], 123
        );
        underTest.deleteByFilename("myfile.png");
        Optional<FileEntity> deleteFile = underTest.findByFilename(file.getFilename());
        AssertionsForClassTypes.assertThat(deleteFile).isEmpty();
    }

    @Test
    void itShouldFindByFilename() {
        FileEntity file = new FileEntity(
                "myfile.png", new byte[10], 123
        );
        underTest.save(file);
        Optional<FileEntity> fileEntity = underTest.findByFilename(file.getFilename());
        AssertionsForClassTypes.assertThat(fileEntity).isPresent();
    }
}