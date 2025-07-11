package com.Backend.Login.Controller;

import com.Backend.Login.DTO.NGODTO;
import com.Backend.Login.Security.JwtUtil;
import com.Backend.Login.Model.NGO;
import com.Backend.Login.Repository.NGORepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/ngos")
public class NGOController {

    @Autowired
    private NGORepository ngoRepository;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/{id}")
    public ResponseEntity<NGODTO> getNGO(@PathVariable String id) {
        return ngoRepository.findById(id)
                .map(ngo -> ResponseEntity.ok(convertToDTO(ngo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value="/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNGO(
            @RequestPart("ngo") NGODTO dto,
            @RequestPart("file") MultipartFile file) {
        try {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");

            NGO ngo = new NGO(
                    dto.getName(),
                    dto.getDescription(),
                    dto.getLocation(),
                    dto.getContact(),
                    dto.getLink(),
                    userEmail
            );
            ngo.setImageUrl(imageUrl);
            ngo.setApproved(false);

            NGO saved = ngoRepository.save(ngo);
            return ResponseEntity.ok(convertToDTO(saved));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed");
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateNGO(
            @PathVariable String id,
            @RequestPart("ngo") NGODTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return ngoRepository.findById(id).map(existing -> {
            try {
                existing.setName(dto.getName());
                existing.setDescription(dto.getDescription());
                existing.setContact(dto.getContact());
                existing.setLink(dto.getLink());
                existing.setLocation(dto.getLocation());
                existing.setApproved(false);

                if (file != null && !file.isEmpty()) {
                    Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    existing.setImageUrl((String) uploadResult.get("secure_url"));
                }

                NGO updated = ngoRepository.save(existing);
                return ResponseEntity.ok(convertToDTO(updated));
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Image upload failed");
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNGO(@PathVariable String id) {
        return ngoRepository.findById(id)
                .<ResponseEntity<Void>>map(ngo -> {
                    ngoRepository.delete(ngo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public List<NGODTO> getUserNGOs() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ngoRepository.findByUserEmail(email)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private NGODTO convertToDTO(NGO ngo) {
        return new NGODTO(
                ngo.getId(),
                ngo.getImageUrl(),
                ngo.getName(),
                ngo.getDescription(),
                ngo.getLocation(),
                ngo.getContact(),
                ngo.getLink(),
                ngo.isApproved()
        );
    }

}



