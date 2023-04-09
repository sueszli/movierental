package at.ac.tuwien.qs.movierental.repository;

import java.util.List;

/**
 * Generic CRUD Interface for repository layer.
 * @param <T> for given CRUD operations in this interface.
 */
public interface DAO<T> {

    /**
     * Create a new t and add to repository.
     *
     * @param t to be added to the repository.
     * @return the recently added t.
     */
    T create(T t);

    /**
     * Read an existing entity from repository.
     *
     * @param id of the entity to read.
     * @return the found entity or null, if not found.
     */
    T read(Long id);

    /**
     * Read all existing entities in repository.
     *
     * @return a List containing all entities in the repository.
     */
    List<T> read();

    /**
     * Update an existing t in the repository with the given argument.
     *
     * @param t to be updated in repository (with matching IDs)
     * @return the new state of the t in the repository.
     */
    T update(T t);

    /**
     * Delete an t from the repository if existent (Idempotent).
     *
     * @param t with matching ID with the one to be deleted.
     */
    void delete(T t);

}
