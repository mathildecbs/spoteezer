package fr.cytech.projet.JEE.services;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

public class ImageUploadService {
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath))
			Files.createDirectories(uploadPath);

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload failed");
		}
	}
	
	public static List<String> directoryFiles(String dir) {
		if(!Files.exists(Paths.get(dir)))
			return new ArrayList<String>();
		try (Stream<Path> walk = Files.walk(Paths.get(dir))) {
		      return walk
		              .filter(p -> !Files.isDirectory(p))  
		              .map(p -> p.toString().split("/")[p.toString().split("/").length-1]) 
		              .filter(f -> f.endsWith("png")||f.endsWith("jpg")||f.endsWith("jpeg"))       
		              .collect(Collectors.toList());       
		  } catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Directory read failed");
		}
	}
}
