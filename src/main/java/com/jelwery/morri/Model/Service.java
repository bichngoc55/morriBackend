package com.jelwery.morri.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    private String id;
    // @Indexed(unique=true)
    private String serviceName;
    private String serviceDescription;
    private String serviceUrl;
    private Double price;

}
