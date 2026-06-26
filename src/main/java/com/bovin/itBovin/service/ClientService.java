package com.bovin.itBovin.service;

import com.bovin.itBovin.dto.ClientSearchCriteria;
import com.bovin.itBovin.err.ClientNotFoundErr;
import com.bovin.itBovin.model.ClientModel;
import com.bovin.itBovin.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientModel> getClientsWithFilter(ClientSearchCriteria criteria, Pageable pageable) {
        Page<ClientModel> entityPage = clientRepository.findAll(
                fromCriteria(criteria),
                pageable);
        return entityPage;
    }

    public static Specification<ClientModel> fromCriteria(ClientSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Field[] fields = criteria.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(criteria);
                    if (value != null && !value.toString().isEmpty()) {
                        String fieldName = field.getName();
                        predicates.add(cb.like(
                                cb.lower(root.get(fieldName)),
                                "%" + value.toString().toLowerCase() + "%"));
                    }
                } catch (IllegalAccessException e) {
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public ClientModel getClientById(Integer id) throws ClientNotFoundErr {
        Optional<ClientModel> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return optionalClient.get();
        } else {
            throw new ClientNotFoundErr(id);
        }
    }
}