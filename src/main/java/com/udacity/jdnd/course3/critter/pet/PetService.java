package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.pet.data.PetData;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepositoryDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepositoryDAO petRepositoryDAO;

    //Save a pet
    public PetDTO savePet(PetDTO petDTO) {
        PetData petData = convertPetDTOToPetData(petDTO);
        Long id = petRepositoryDAO.addAPet(petData);
        List<PetData> storedPet = petRepositoryDAO.findPetById(id);
        return convertPetDataToPetDTO(storedPet.get(0));
    }

    //Get a pet by id
    public PetDTO getPet(long petId) {
        List<PetData> storedPet = petRepositoryDAO.findPetById(petId);
        return convertPetDataToPetDTO(storedPet.get(0));
    }

    //Get all pets
    public List<PetDTO> getPets() {
        List<PetData> storedPet = petRepositoryDAO.findAllPets();
        List<PetDTO> petDTO = new ArrayList<>();
        for (PetData data : storedPet) {
            petDTO.add(convertPetDataToPetDTO(data));
        }
        return petDTO;
    }


    //Get pets by owner id
    public List<PetDTO> getPetsByOwner(long ownerId) {
        List<PetData> storedPet = petRepositoryDAO.findPetsByOwner(ownerId);
        List<PetDTO> petDTO = new ArrayList<>();
        for (PetData data : storedPet) {
            petDTO.add(convertPetDataToPetDTO(data));
        }

        return petDTO;
    }

    //Utility to convert the data
    private PetDTO convertPetDataToPetDTO(PetData petData) {
        System.out.println(petData.toString());
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petData, petDTO);
        petDTO.setId(petData.getPetId());
        petDTO.setType(Enum.valueOf(PetType.class, petData.getType()));
        return petDTO;
    }

    //Utility to convert the data
    private PetData convertPetDTOToPetData(PetDTO petDTO) {
        PetData petData = new PetData();
        BeanUtils.copyProperties(petDTO, petData);
        petData.setType(petDTO.getType().name());
        return petData;
    }

}
