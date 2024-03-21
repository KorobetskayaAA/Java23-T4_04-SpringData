package ru.teamscore.java23.springdata.storage.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Movement {
    @NonNull
    private String number;
    @NonNull
    private LocalDateTime documentDate;
    @NonNull
    private String sourceStorageCode;
    @NonNull
    private String destinationStorageCode;
    private List<OperationItem> items = new ArrayList<>();
}
