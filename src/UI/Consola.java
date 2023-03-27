package UI;

import domain.Utilizator;
import domain.Validator.ValidationException;
import service.Service;

import java.util.Scanner;

public class Consola {
    private Service service;
    private Boolean ok;

    public Consola(Service service) {
        this.service = service;
        this.ok = false;
    }

    public void runConsola() {
        Boolean running = true;
        while (running) {
            this.ok = false;
            Scanner integerScanner = new Scanner(System.in);
            System.out.println("0. Exit");
            System.out.println("1. Login");
            System.out.println("2. Register");
            int option = integerScanner.nextInt();
            switch (option) {
                case 0:
                    running = false;
                    this.ok = true;
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    this.uiAddUser();
            }
        }
    }

    public void adminConsola(Long id) {
        Boolean runningAdminConsola = true;
        Scanner integerScanner = new Scanner(System.in);
        while (runningAdminConsola) {
            System.out.println("0. Exit to principal menu");
            System.out.println("1. Add an user");
            System.out.println("2. Remove an user");
            System.out.println("3. Add a friend");
            System.out.println("4. Remove a friend");
            System.out.println("5. Show all users");
            System.out.println("6. Show all friends");
            int option = integerScanner.nextInt();
            switch (option) {
                case 0 -> {
                    runningAdminConsola = false;
                    this.ok = true;
                    break;
                }
                case 1 -> this.uiAddUser();
                case 2 -> this.uiDeleteUser();
                case 3 -> this.uiAddFriendship(id);
                case 4 -> this.removeFriend(id);
                case 5 -> this.showAll();
                case 6 -> this.showAllFriends(id);
            }
        }
    }


    public void showAllFriends(Long id) {
        Utilizator utilizator = this.service.findOne(id);
        for (Utilizator utilizatorFriend : utilizator.getFriends())
            System.out.println(utilizatorFriend);
    }

    public void showAll() {
        for (Utilizator utilizator : this.service.findAll())
            System.out.println(utilizator);
    }

    public void uiAddUser() {
        try {
            String firstName, lastName, userName, password;
            Scanner stringScanner = new Scanner(System.in);
            System.out.println("Write username: ");
            userName = stringScanner.nextLine();
            System.out.println("Write first name: ");
            firstName = stringScanner.nextLine();
            System.out.println("Write last name: ");
            lastName = stringScanner.nextLine();
            System.out.println("Write password: ");
            password = stringScanner.nextLine();
            Utilizator utilizator = new Utilizator((long) (this.service.getSize() + 1), firstName, lastName, userName, password, false);
            this.service.save(utilizator);
        } catch (ValidationException e) {
            System.out.println(e.toString());
        }
    }

    public void uiDeleteUser() {
        String userName;
        Utilizator deletedUser;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Write the username of the user that you want to delete: ");
            userName = scanner.nextLine();
            deletedUser = this.service.delete(userName);
            if (deletedUser != null) {
                this.service.removeFriendsFromAllUsers(deletedUser);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void uiAddFriendship(Long idFrom) {
        String userName;
        try {
            System.out.println("Write the username of the user that you want to add as a friend: ");
            Scanner scanner = new Scanner(System.in);
            userName = scanner.nextLine();
            this.service.addFriendship(idFrom, userName);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void login() {
        if (this.ok)
            this.ok = false;
        else {
            String username, password;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Write your username: ");
            username = scanner.nextLine();
            System.out.println("Write your password: ");
            password = scanner.nextLine();
            Utilizator utilizatorLogat = this.service.login(username, password);
            if (utilizatorLogat == null)
                System.out.println("Utilizator sau parola gresita");
            else if (utilizatorLogat.getAdmin())
                this.adminConsola(utilizatorLogat.getId());
            else
                this.userConsola(utilizatorLogat.getId());
        }
    }

    public void removeFriend(Long idFrom) {
        String username;
        System.out.println("Write the username of the user that you want to unfriend: ");
        Scanner scanner = new Scanner(System.in);
        username = scanner.nextLine();
        Long id = this.service.getUserByUsername(username).getId();
        this.service.removeFriend(idFrom, id);
    }

    public void userConsola(Long id) {
        Boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("0. Exit to principal menu");
            System.out.println("1. Add a friend");
            System.out.println("2. Remove a friend");
            System.out.println("3. Show all my friends");
            int option = scanner.nextInt();
            switch (option) {
                case 0 -> {
                    running = false;
                    this.ok = true;
                    break;
                }
                case 1 -> this.uiAddFriendship(id);
                case 2 -> this.removeFriend(id);
                case 3 -> this.showAllFriends(id);
            }
        }
    }
}
