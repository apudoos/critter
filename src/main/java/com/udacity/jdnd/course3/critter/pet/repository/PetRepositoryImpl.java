package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.data.PetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PetRepositoryImpl implements PetRepositoryDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String OWNER_ID = "ownerId";

    private static final String PET_ID = "petId";

    private static final String  SELECT_ALL_PETS = "select * from pets";

    private static final String  SELECT_PETS_BY_OWNER = "select * from pets where owner_id = :" + OWNER_ID;

    private static final String  SELECT_PET_BY_ID = "select * from pets where pet_id = :" + PET_ID;

    private static final String INSERT_PETS = "insert into pets (type, name, owner_id, birth_date, notes) " +
            "values (:type, :name, :ownerId, :birthDate, :notes) " ;

    private static final RowMapper<PetData> petDataRowMapper = new BeanPropertyRowMapper<>(PetData.class);

    @Override
    public List<PetData> findAllPets() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_PETS, petDataRowMapper);
    }

    @Override
    public List<PetData> findPetsByOwner(Long ownerId) {
        return namedParameterJdbcTemplate.query(SELECT_PETS_BY_OWNER,
                new MapSqlParameterSource().addValue(OWNER_ID, ownerId),
                petDataRowMapper);
    }

    @Override
    public List<PetData> findPetById(Long petId) {
        return namedParameterJdbcTemplate.query(SELECT_PET_BY_ID,
                new MapSqlParameterSource().addValue(PET_ID, petId),
                petDataRowMapper);
    }

    @Override
    public Long addAPet(PetData petData) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(petData);
        //parameterSource.registerSqlType("type", Types.VARCHAR);
        KeyHolder key = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_PETS,
                parameterSource,
                key
                );
        return key.getKey().longValue();
    }


}
