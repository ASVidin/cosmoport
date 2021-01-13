package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.awt.print.Pageable;
import java.util.List;

public interface ShipService {
    List<Ship> getAll(String name, String planet, ShipType type, Long after, Long before,
                      Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minSize,
                      Integer maxSize, Double minRating, Double maxRating);

    List<Ship> sort(List<Ship> ships, ShipOrder order, Integer pageNumber, Integer pageSize);

    Ship getById(Long id);

    void delete(Long id);

    Ship create(Ship ship);

    Ship update(Long id, Ship ship);
}
