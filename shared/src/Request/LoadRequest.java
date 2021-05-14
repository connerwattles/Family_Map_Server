package Request;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * The users, persons, and events the load service needs
 */
public class LoadRequest {
    /**
     * The user objects that will be loaded
     */
    User[] users;
    /**
     * The person objects that will be loaded
     */
    Person[] persons;
    /**
     * The event objects that will be loaded
     */
    Event[] events;

    /**
     *
     * @return The array of user objects
     */
    public User[] getUsers() { return users; }

    /**
     *
     * @param users The new array of user objects
     */
    public void setUsers(User[] users) { this.users = users; }

    /**
     *
     * @return The array of person objects
     */
    public Person[] getPersons() { return persons; }

    /**
     *
     * @param persons The new array of person objects
     */
    public void setPersons(Person[] persons) { this.persons = persons; }

    /**
     *
     * @return The array of event objects
     */
    public Event[] getEvents() { return events; }

    /**
     *
     * @param events The new array of event objects
     */
    public void setEvents(Event[] events) { this.events = events; }
}
