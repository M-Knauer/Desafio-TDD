package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ControllerNotFoundException;

@Service
public class EventService {

	@Autowired
	EventRepository repository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {	
			Event entity = repository.getOne(id);
			
			entity.setName(dto.getName());
			entity.setDate(dto.getDate());
			entity.setUrl(dto.getUrl());
			entity.setCity(new City(dto.getCityId(), null));
			
			return new EventDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ControllerNotFoundException("Id not found "+id);
		}
	}
}