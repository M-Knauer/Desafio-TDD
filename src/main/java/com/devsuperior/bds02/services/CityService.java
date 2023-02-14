package com.devsuperior.bds02.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.ControllerNotFoundException;
import com.devsuperior.bds02.services.exceptions.DatabaseException;

@Service
public class CityService {

	@Autowired
	CityRepository repository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		return repository.findAll(Sort.by("name")).stream().map(x -> new CityDTO(x)).toList();
		
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City(null, dto.getName());
		return new CityDTO(repository.save(entity));
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ControllerNotFoundException("Id not found "+id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Entity cannot be deleted");
		}
	}
}
