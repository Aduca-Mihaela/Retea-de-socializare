import UI.Consola;
import domain.Utilizator;
import domain.Validator.UtilizatorValidator;
import repository.memory.InMemoryRepository0;
import service.Service;


public class Main {
    public static void main(String[] args) {
        UtilizatorValidator utilizatorValidator = new UtilizatorValidator();
        InMemoryRepository0<Long, Utilizator> repository = new InMemoryRepository0<Long, Utilizator>(utilizatorValidator);
        Utilizator u1 = new Utilizator(1L,"Aduca", "Mihaila", "MihailaAduca", "parola12", true);
        Utilizator u2 = new Utilizator(2L,"Adina", "Maris", "user1", "parola12", false);
        Utilizator u3 = new Utilizator(3l, "Mihai", "Lungu", "user2", "parola12", false);
        Utilizator u4 = new Utilizator(4l, "Ioana", "Maria", "user3", "parola12", false);
        Service service = new Service(repository);
        repository.save(u1);
        repository.save(u2);
        repository.save(u3);
        repository.save(u4);
        Consola console = new Consola(service);
        console.runConsola();
    }
}
