package ru.teamscore.java23.springdata.storage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoredItem {
    private String code;
    private String title;
    private double quantity;
}
