package com.dineth.debateTracker.room;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRoom() {
        return roomRepository.findAll();
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }


}
