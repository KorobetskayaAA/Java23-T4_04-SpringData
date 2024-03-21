package ru.teamscore.java23.springdata.storage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    private String code;
    private List<StoredItem> items = new ArrayList<>();
}
