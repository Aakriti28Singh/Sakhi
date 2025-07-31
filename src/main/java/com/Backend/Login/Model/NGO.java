package com.Backend.Login.Model;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ngos")
public class NGO {
    @Id
    private String id;
    private String name;
    private String description;
    private String contact;
    private String link;
    private String userEmail;
    private String location;
    private String imageUrl;
    private boolean approved=false;

    public NGO(String name, String description, String location, String contact, String link, String userEmail) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.link = link;
        this.userEmail = userEmail;
    }

}

