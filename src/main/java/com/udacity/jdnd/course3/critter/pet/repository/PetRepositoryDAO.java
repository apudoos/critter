package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.data.PetData;

import java.util.List;

public interface PetRepositoryDAO {
    List<PetData> findAllPets();
    Long addAPet(PetData petData);
    List<PetData> findPetsByOwner(Long ownerId);
    List<PetData> findPetById(Long id);
}
