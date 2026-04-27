package org.example.baitap_27_04.dto;

import jakarta.validation.constraints.NotBlank;

public class OwnerForm {

    @NotBlank(message = "{owner.name.notBlank}")
    private String ownerName;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}

