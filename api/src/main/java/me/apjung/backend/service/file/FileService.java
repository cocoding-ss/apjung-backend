package me.apjung.backend.service.file;

import me.apjung.backend.service.file.dto.SavedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    SavedFile upload(MultipartFile file) throws IOException;
}