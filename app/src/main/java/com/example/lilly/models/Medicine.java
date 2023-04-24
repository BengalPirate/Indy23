package com.example.lilly.models;

import java.util.List;
import java.util.Map;


public class Medicine {
    private Data data;

    // getters and setters
}

class Data {
    private HealthProfessionalInformation health_professional_information;
    private ScientificInformation scientific_information;

    // getters and setters
}

class HealthProfessionalInformation {
    private Indications indications;
    private Contraindications contraindications;

    // getters and setters
}

class Indications {
    private String name;
    private String data;

    // getters and setters
}

class Contraindications {
    private Map<String, List<String>> contraindicationMap;

    // getters and setters
}

class ScientificInformation {
    private PharmaceuticalInformation pharmaceutical_information;

    // getters and setters
}

class PharmaceuticalInformation {
    private String drug_substance;
    private String product_characteristics;

}

