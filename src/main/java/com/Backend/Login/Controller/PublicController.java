package com.Backend.Login.Controller;

import com.Backend.Login.DTO.NGODTO;
import com.Backend.Login.Model.NGO;
import com.Backend.Login.Repository.NGORepository;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/NGOPage")
public class PublicController {

    @Autowired
    private NGORepository ngoRepository;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/public")
    public List<NGODTO> getAllNGOs(@RequestParam(required = false) String city) {
        return ngoRepository.findAll()
                .stream()
                .filter(NGO::isApproved)
                .filter(ngo -> city == null || ngo.getLocation().equalsIgnoreCase(city))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/cities")
    public List<String> getCities() {
        return ngoRepository.findAll()
                .stream()
                .filter(NGO::isApproved) // Only for approved NGOs
                .map(NGO::getLocation)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
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

