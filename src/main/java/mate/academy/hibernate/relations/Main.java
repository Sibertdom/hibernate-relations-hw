package mate.academy.hibernate.relations;

import java.util.List;
import mate.academy.hibernate.relations.dao.impl.ActorDaoImpl;
import mate.academy.hibernate.relations.dao.impl.CountryDaoImpl;
import mate.academy.hibernate.relations.dao.impl.MovieDaoImpl;
import mate.academy.hibernate.relations.model.Actor;
import mate.academy.hibernate.relations.model.Country;
import mate.academy.hibernate.relations.model.Movie;
import mate.academy.hibernate.relations.service.ActorService;
import mate.academy.hibernate.relations.service.CountryService;
import mate.academy.hibernate.relations.service.MovieService;
import mate.academy.hibernate.relations.service.impl.ActorServiceImpl;
import mate.academy.hibernate.relations.service.impl.CountryServiceImpl;
import mate.academy.hibernate.relations.service.impl.MovieServiceImpl;
import mate.academy.hibernate.relations.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        CountryDaoImpl countryDao = new CountryDaoImpl(sessionFactory);
        ActorDaoImpl actorDao = new ActorDaoImpl(sessionFactory);
        MovieDaoImpl movieDao = new MovieDaoImpl(sessionFactory);

        CountryService countryService = new CountryServiceImpl(countryDao);
        ActorService actorService = new ActorServiceImpl(actorDao);
        MovieService movieService = new MovieServiceImpl(movieDao);

        Country usa = new Country("USA");

        Actor vinDiesel = new Actor("Vin", "Diesel");
        vinDiesel.setCountry(usa);
        actorService.add(vinDiesel); // Тут usa буде збережено (Cascade)

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setActors(List.of(vinDiesel));
        fastAndFurious.setCountry(usa);
        movieService.add(fastAndFurious);

        Actor dwayneJohnson = new Actor("Dwayne", "Johnson");
        dwayneJohnson.setCountry(usa);
        actorService.add(dwayneJohnson); // Тут usa вже не буде збережено, оскільки вона Persistent

        Movie hobbsAndShaw = new Movie("Hobbs & Shaw");
        hobbsAndShaw.setCountry(usa);
        hobbsAndShaw.setActors(List.of(vinDiesel, dwayneJohnson));
        movieService.add(hobbsAndShaw);

        System.out.println("Отриманий фільм (Fast and Furious): " + movieService.get(fastAndFurious.getId()));
        System.out.println("Отриманий фільм (Hobbs & Shaw): " + movieService.get(hobbsAndShaw.getId()));

        HibernateUtil.shutdown();
    }
}
