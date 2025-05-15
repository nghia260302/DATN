package com.tuannghia.andshop.service.impl;

import com.tuannghia.andshop.constant.SortType;
import com.tuannghia.andshop.entity.Contact;
import com.tuannghia.andshop.repository.ContactRepository;
import com.tuannghia.andshop.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ContactServiceImp implements ContactService {
    private ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Page<Contact> getContactsPage(String sortBy, Pageable pageable) {
        if (sortBy.equals(SortType.newest)) {
            return contactRepository.findByOrderByCreatedAtDesc(pageable);
        }
        return contactRepository.findByOrderByCreatedAtAsc(pageable);
    }

    @Override
    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }
}
