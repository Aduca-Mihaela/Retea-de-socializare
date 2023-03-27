package service;

import domain.Utilizator;
import domain.Validator.ValidationException;
import repository.memory.InMemoryRepository0;

import java.util.List;
import java.util.Objects;

public class Service implements ServiceInterface<Long, Utilizator>{
    private InMemoryRepository0<Long, Utilizator> repository;
    public Service(InMemoryRepository0<Long, Utilizator> repository){
        this.repository = repository;
    }

    @Override
    public Utilizator findOne(Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public Iterable<Utilizator> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Utilizator save(Utilizator entity) {
        return this.repository.save(entity);
    }

    public int getSize(){
        return repository.getSize();
    }

    @Override
    public Utilizator delete(String userName) {
        Long id = this.getUserByUsername(userName).getId();
        return this.repository.delete(id);
    }

    @Override
    public Utilizator update(Utilizator entity) {
        return this.repository.update(entity);
    }

    public Utilizator getUserByUsername(String userName){
        for (Utilizator utilizator : this.findAll()){
            if (Objects.equals(utilizator.getUserName(), userName))
                return utilizator;
        }
        return null;
    }

    public void addFriendship(Long id, String userName){
        Utilizator userTo = getUserByUsername(userName);
        Utilizator userFrom = this.repository.findOne(id);
        List<Utilizator> friendsTo = userTo.getFriends();
        if (friendsTo.contains(userFrom))
            throw new ValidationException("This user is already your friend.");
        List<Utilizator> friendsFrom = userFrom.getFriends();
        friendsTo.add(userFrom);
        friendsFrom.add(userTo);
        userFrom.setFriends(friendsFrom);
        userTo.setFriends(friendsTo);
        this.update(userFrom);
        this.update(userTo);
    }

    public Utilizator login(String username, String password){
        for (Utilizator utilizator : this.findAll()){
            if (Objects.equals(utilizator.getUserName(), username))
                if (Objects.equals(password, utilizator.getPassword()))
                    return utilizator;
        }
        return null;
    }

    public void removeFriend(Long idFrom, Long idTo){
        Utilizator utilizatorFrom = this.findOne(idFrom);
        Utilizator utilizatorTo = this.findOne(idTo);

        List<Utilizator> friendshipTo = utilizatorTo.getFriends();
        List<Utilizator> friendshipFrom = utilizatorFrom.getFriends();
        friendshipFrom.remove(utilizatorTo);
        friendshipTo.remove(utilizatorFrom);
        utilizatorFrom.setFriends(friendshipFrom);
        utilizatorTo.setFriends(friendshipTo);
        this.update(utilizatorFrom);
        this.update(utilizatorTo);
    }

    public void removeFriendsFromAllUsers(Utilizator deletedUser){
        for (Utilizator utilizator : this.findAll()){
            List<Utilizator> friends = utilizator.getFriends();
            friends.remove(deletedUser);
            utilizator.setFriends(friends);
            this.update(utilizator);
        }
    }
}









