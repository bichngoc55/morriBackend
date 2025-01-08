package com.jelwery.morri.Model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="termAndConditions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermCondition {
     @Id
    private String id;
    private String greeting;
    private ArrayList<Description> description;
}
