package mate.academy.hibernate.relations.service.impl;

import mate.academy.hibernate.relations.dao.CountryDao; // Припустимо, що CountryDao існує
import mate.academy.hibernate.relations.model.Country;
import mate.academy.hibernate.relations.service.CountryService;

public class CountryServiceImpl implements CountryService {
    private final CountryDao countryDao;

    // Впровадження залежності (Dependency Injection) DAO через конструктор
    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public Country add(Country country) {
        // Делегування операції збереження до DAO
        return countryDao.add(country);
    }

    @Override
    public Country get(Long id) {
        // Делегування операції отримання до DAO.
        // Оскільки DAO повертає Optional, ми використовуємо orElseThrow
        // для повернення сутності або викидання винятку, якщо об'єкт не знайдено.
        return countryDao.get(id).orElseThrow(
                () -> new RuntimeException("Country with id " + id + " not found")
        );
    }
}