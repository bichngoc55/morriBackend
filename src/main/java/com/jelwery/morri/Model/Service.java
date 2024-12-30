package com.jelwery.morri.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
