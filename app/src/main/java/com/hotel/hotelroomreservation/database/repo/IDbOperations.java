package com.hotel.hotelroomreservation.database.repo;

import java.util.List;

public interface IDbOperations<T> {

    List<T> selectAll();

    void insert(List<T> items);

    void delete();
}
