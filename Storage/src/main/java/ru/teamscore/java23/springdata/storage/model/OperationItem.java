package ru.teamscore.java23.springdata.storage.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperationItem {
    private String itemCode;
    private double quantity;
}
