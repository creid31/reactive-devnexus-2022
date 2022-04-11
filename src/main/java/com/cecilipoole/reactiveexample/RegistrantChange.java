package com.cecilipoole.reactiveexample;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrantChange {
    private RegistrantStatus status;
    private Long guestId;
}
