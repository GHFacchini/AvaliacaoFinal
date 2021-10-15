package com.compasso.duvidas.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.compasso.duvidas.services.RespostaService;

@Component
public class Disco {

	@Autowired
	private RespostaService respostaService;
	
	@Value("./storage/files")
	private String root;

	public ResponseEntity<?> saveFile(Long id, MultipartFile arquivo) {
		Path diretorioPath = Paths.get(this.root);
		try {
			Files.createDirectories(diretorioPath);
			File convertFile = new File(diretorioPath + "/" + arquivo.getOriginalFilename());
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(arquivo.getBytes());
		} catch (IOException e) {
			return ResponseEntity.badRequest()
					.body("'Problema ao tentar salvar arquivo '" + arquivo.getOriginalFilename() + "'");
		}
		if(respostaService.bindArquivoResposta(id, arquivo)) {
			return ResponseEntity.badRequest()
					.body("'Problema ao tentar salvar arquivo '" + arquivo.getOriginalFilename() + "'");
		}
		return ResponseEntity.ok().body("Arquivo salvo com sucesso!");
	}

	public ResponseEntity<?> downloadFile(String nome) {
		Path diretorioPath = Paths.get(this.root);
		File arquivo = new File(diretorioPath + "/" + nome);
		try {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(arquivo));

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", arquivo.getName()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			return ResponseEntity.ok().headers(headers).contentLength(arquivo.length())
					.contentType(MediaType.parseMediaType("application/txt")).body(resource);
		} catch (Exception e) {
			System.out.println("Problema ao tentar fazer o download do arquivo '" + nome + "'\n" + e);
			return ResponseEntity.badRequest().body("Problema ao tentar fazer o download do arquivo '" + nome + "'");
		}
	}
}
