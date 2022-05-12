package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.SearchTaskResponseDTO;
import org.example.dto.SearchTaskResultsResponseDTO;
import org.example.entity.SearchResultEntity;
import org.example.entity.SearchTaskEntity;
import org.example.exception.MyException;
import org.example.repository.ResultRepository;
import org.example.repository.TaskRepository;
import org.example.security.Authentication;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchRFCService {
   private final Path path = Paths.get("RFC");
   private final TaskRepository taskRepository;
   private final ResultRepository resultRepository;
   private final ExecutorService executorService = Executors.newFixedThreadPool(8);

   public SearchTaskResponseDTO search(Authentication auth, String q) throws IOException {
      // TODO:
      // save in db - task
      final SearchTaskEntity saved = taskRepository.save(new SearchTaskEntity(
         0L,
         auth.getName(),
         q,
         false
      ));

      executorService.execute(() -> {
         try {
            final List<Path> files = Files.list(path).toList();
            final String search = q;

            for (Path file : files) {
               //final String content = Files.readString(file);
               // BufferedReader
               try (final BufferedReader reader = Files.newBufferedReader(file)) {
                  String line;
                  int lineNumber = 0;
                  while ((line = reader.readLine()) != null) {
                     lineNumber++;
                     if (line.contains(q)) {
                        resultRepository.saveResult(new SearchResultEntity(0L, saved.getId(), file.getFileName().toString(), line, lineNumber));
                     }
                  }
               } catch (IOException e) {
                  e.printStackTrace();
               }
//            final List<String> lines = Files.readAllLines(file);
//            int lineNumber = 0;
//            for (String line : lines) {
//               lineNumber++;
//            }
//            if (content.contains(q)) {
//               repository.saveResult(new SearchResultEntity(0L, saved.getId(), file.getFileName().toString(), ));
//            }
            }
            taskRepository.markDone(saved); //status = TRUE
         } catch (IOException e) {
            e.printStackTrace();
         }
      });

      return new SearchTaskResponseDTO(saved.getId(), saved.getUserLogin(), saved.getQ(), saved.isStatus());
   }

   public SearchTaskResultsResponseDTO status(Authentication auth, long taskId) throws MyException {
      final SearchTaskEntity saved = taskRepository.getStatus(taskId);
      if (!Objects.equals(saved.getUserLogin(), auth.getName())) {
         throw new RuntimeException("message"); // not your task
      }

      return new SearchTaskResultsResponseDTO(
         saved.getId(),
         saved.getUserLogin(),
         saved.getQ(),
         saved.isStatus(),
         resultRepository.getByTaskId(taskId).stream()
            .map(o -> new SearchTaskResultsResponseDTO.SearchResult(
               o.getTaskId(),
               o.getFile(),
               o.getLine(),
               o.getLineNumber()
            ))
            .collect(Collectors.toList())
      );
   }
}

