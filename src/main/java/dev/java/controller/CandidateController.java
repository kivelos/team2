package dev.java.controller;

import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Attachment;
import dev.java.db.model.Candidate;
import dev.java.db.model.Vacancy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
public class CandidateController extends AbstractController<Candidate> {

    @PostConstruct
    @Override
    public void initialize() {
        super.initialize();
        setSortedField("surname");
        setUrl("/candidate/");
        setAbstractDao(new CandidateDao(getSession()));
    }

    @Override
    @GetMapping("/candidates")
    public ResponseEntity getAllEntities(HttpServletRequest request) {
        return super.getAllEntities(request);
    }

    @Override
    @PostMapping("/candidates")
    public ResponseEntity createEntity(@RequestBody Candidate candidate, HttpServletRequest request) {
        return super.createEntity(candidate, request);
    }

    @Override
    @GetMapping("/candidate/{id:\\d+}")
    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        return super.getEntity(id, request);
    }

    @Override
    @PutMapping("/candidate/{id:\\d+}")
    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody Candidate candidate,
                                       HttpServletRequest request) {
        return super.updateEntity(id, candidate, request);
    }

    @Override
    @DeleteMapping("/candidate/{id:\\d+}")
    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        return super.deleteEntity(id, request);
    }

    @GetMapping("/candidate/{id:\\d+}/vacancies")
    public ResponseEntity getCorrespondVacancies(@PathVariable long id, HttpServletRequest request) {
        getLogging().runMe(request);
        try {
            Candidate entity = getAbstractDao().getEntityById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(entity.getVacancies());
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PutMapping("/candidate/{id:\\d+}/vacancies")
    public ResponseEntity updateCorrespondVacancies(@PathVariable long id, @RequestBody List<Vacancy> vacancies,
                                                 HttpServletRequest request) {
        getLogging().runMe(request);
        try {
            Candidate entity = getAbstractDao().getEntityById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            entity.setVacancies(vacancies);
            getAbstractDao().updateEntity(entity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    @PostMapping(value = "/candidate/{id:\\d+}/uploadAttachment", consumes = "multipart/form-data")
    public ResponseEntity uploadAttachment(@PathVariable long id, @RequestParam("file") MultipartFile file,
                                           @RequestParam("type") String type) {
        /*if (file.isEmpty()) {
            return new ResponseEntity<>("please select a file!", HttpStatus.OK);
        }*/

        try {
            String filePath = saveUploadedFiles(Arrays.asList(file));
            Candidate candidate = getAbstractDao().getEntityById(id);
            if (candidate == null) {
                return ResponseEntity.notFound().build();
            }
            Attachment attachment = new Attachment();
            attachment.setAttachmentType(Attachment.AttachmentType.valueOf(type));
            attachment.setFilePath(filePath);
            candidate.getAttachments().add(attachment);
            getAbstractDao().updateEntity(candidate);
            return ResponseEntity.created(new URI(getUrl() + candidate.getId())).build();
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    private String saveUploadedFiles(List<MultipartFile> files) throws IOException {
        Path path = null;
        for (MultipartFile file : files) {

            /*if (file.isEmpty()) {
                continue; //next pls
            }*/

            byte[] bytes = file.getBytes();
            path = Paths.get(GeneralConstant.UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
        return path.toAbsolutePath().toString();
    }
}
