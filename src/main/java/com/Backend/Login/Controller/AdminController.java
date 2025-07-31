package com.Backend.Login.Controller;

import com.Backend.Login.DTO.ApiResponseDTO;
import com.Backend.Login.Model.NGO;
import com.Backend.Login.Repository.NGORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private NGORepository ngoRepository;

    @GetMapping("/pending-ngos")
    public ResponseEntity<?> getPendingNGOs() {
        List<NGO> pending = ngoRepository.findByApprovedFalse();
        return ResponseEntity.ok(new ApiResponseDTO(true, "Pending NGOs", pending));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveNGO(@PathVariable String id) {
        Optional<NGO> optionalNGO = ngoRepository.findById(id);
        if (optionalNGO.isPresent()) {
            NGO ngo = optionalNGO.get();
            ngo.setApproved(true);
            ngoRepository.save(ngo);
            return ResponseEntity.ok(new ApiResponseDTO(true, "NGO approved", ngo));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO(false, "NGO not found", null));
    }

    @DeleteMapping("/reject/{id}")
    public ResponseEntity<?> rejectNGO(@PathVariable String id) {
        if (ngoRepository.existsById(id)) {
            ngoRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponseDTO(true, "NGO rejected and deleted", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO(false, "NGO not found", null));
    }
}


