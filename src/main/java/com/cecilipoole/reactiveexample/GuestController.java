package com.cecilipoole.reactiveexample;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("guests")
@AllArgsConstructor
public class GuestController {
    private final EventEmitter eventEmitter;
    private final GuestRepository guestRepository;

    @PostMapping("/subscribe")
    public void mockSubscribe() {
        eventEmitter.changes().log().subscribe();
    }

    @PostMapping
    public void createGuest(@RequestBody Guest guest) {
        guest.setGuestStatus(GuestStatus.REGISTERED);
        guestRepository.save(guest);
    }

    @PutMapping
    public Guest updateGuest(@RequestParam Long id, @RequestParam GuestStatus guestStatus) {
        Optional<Guest> optGuest = guestRepository.findById(id);
        if (optGuest.isPresent()) {
            Guest guest = optGuest.get();
            guest.setGuestStatus(guestStatus);
            guestRepository.save(guest);
        }
        return null;
    }

    @GetMapping
    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }

}
