package com.Backend.Login.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class NGODTO {
    private String id;
    private String imageUrl;
    private String name;
    private String description;
    private String location;
    private String contact;
    private String link;
    private String userEmail;
    private boolean approved;


    public NGODTO(String id, String imageUrl, String name, String description, String location, String contact, String link,boolean approved) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.link = link;
        this.approved = approved;
    }

}

