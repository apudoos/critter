package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.pet.data.PetData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetData petData = convertPetDTOToPetData(petDTO);
        PetData savedData = petService.savePet(petData);
        return convertPetDataToPetDTO(savedData);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetData returnedData = petService.getPet(petId);
        return convertPetDataToPetDTO(returnedData);
    }

    @GetMapping
    public List<PetDTO> getPets(){

        List<PetData> storedPet = petService.getPets();
        List<PetDTO> petDTO = new ArrayList<>();
        for (PetData data : storedPet) {
            petDTO.add(convertPetDataToPetDTO(data));
        }
        return petDTO;

    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetData> storedPet = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTO = new ArrayList<>();
        for (PetData data : storedPet) {
            petDTO.add(convertPetDataToPetDTO(data));
        }
        return petDTO;
    }

    //Utility to convert the data to DTO
    private PetDTO convertPetDataToPetDTO(PetData petData) {
        System.out.println(petData.toString());
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petData, petDTO);
        petDTO.setId(petData.getPetId());
        petDTO.setType(Enum.valueOf(PetType.class, petData.getType()));
        return petDTO;
    }

    //Utility to convert the DTO to data
    private PetData convertPetDTOToPetData(PetDTO petDTO) {
        PetData petData = new PetData();
        BeanUtils.copyProperties(petDTO, petData);
        petData.setType(petDTO.getType().name());
        return petData;
    }
}
