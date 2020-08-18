package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.pet.data.PetData;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepositoryDAO petRepositoryDAO;

    //Save a pet
    public PetData savePet(PetData petData) {
        Long id = petRepositoryDAO.addAPet(petData);
        List<PetData> storedPet = petRepositoryDAO.findPetById(id);
        return storedPet.get(0);
    }

    //Get a pet by id
    public PetData getPet(long petId) {
        List<PetData> storedPet = petRepositoryDAO.findPetById(petId);
        return storedPet.get(0);

    }

    //Get all pets
    public List<PetData> getPets() {
        List<PetData> storedPet = petRepositoryDAO.findAllPets();
        return storedPet;
    }


    //Get pets by owner id
    public List<PetData> getPetsByOwner(long ownerId) {
        List<PetData> storedPet = petRepositoryDAO.findPetsByOwner(ownerId);
        return storedPet;
    }

}
