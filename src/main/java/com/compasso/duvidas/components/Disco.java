package com.compasso.duvidas.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Disco {
	
	@Value("./storage/files")
	private String root;
	
	public ResponseEntity<?> saveFile(MultipartFile arquivo) {
		Path diretorioPath = Paths.get(this.root);
		
		try {
			Files.createDirectories(diretorioPath);
			File convertFile = new File(diretorioPath + "/" + arquivo.getOriginalFilename());
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(arquivo.getBytes());
		} catch (IOException e) {
			System.out.println("Problema ao tentar salvar arquivo '" + arquivo.getOriginalFilename() + "'\n" + e);
			return ResponseEntity.badRequest().body("'Problema ao tentar salvar arquivo '" + arquivo.getOriginalFilename() + "'");
		}
		return ResponseEntity.ok().body("Arquivo salvo com sucesso!");
	}
}
