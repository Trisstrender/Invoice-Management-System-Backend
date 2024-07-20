package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);

        return personMapper.toDTO(entity);
    }

    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(long id) {
        PersonEntity personEntity = fetchPersonById(id);
        return personMapper.toDTO(personEntity);
    }

    @Override
    public PersonDTO updatePerson(long id, PersonDTO personDTO) {
        PersonEntity existingPerson = fetchPersonById(id);
        existingPerson.setHidden(true);
        personRepository.save(existingPerson);

        PersonEntity newPerson = personMapper.toEntity(personDTO);
        newPerson.setId(0); // Ensure a new entity is created
        newPerson = personRepository.save(newPerson);

        return personMapper.toDTO(newPerson);
    }

    @Override
    public List<InvoiceDTO> getPersonSales(String identificationNumber) {
        return invoiceService.getPersonSales(identificationNumber);
    }

    @Override
    public List<InvoiceDTO> getPersonPurchases(String identificationNumber) {
        return invoiceService.getPersonPurchases(identificationNumber);
    }

    @Override
    public List<Map<String, Object>> getPersonStatistics() {
        return personRepository.findAll().stream()
                .map(person -> {
                    long revenue = person.getSales().stream().mapToLong(InvoiceEntity::getPrice).sum();
                    Map<String, Object> statistics = new HashMap<>();
                    statistics.put("personId", person.getId());
                    statistics.put("personName", person.getName());
                    statistics.put("revenue", revenue);
                    return statistics;
                })
                .collect(Collectors.toList());
    }

    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
}