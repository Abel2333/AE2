package org.ae2;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ae2.Data.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

public class Controller {

    public static void ClearConsole() {
        try {
            // Check the current operating system
            String operatingSystem = System.getProperty("os.name");

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Start() throws ClassNotFoundException, JsonProcessingException {
        GlobalData.initialize();
        Scanner scanner = new Scanner(System.in);
        StringBuffer buffer = new StringBuffer();
        while (true) {
            System.out.println("Login (press 'q' to quit): ");
            buffer.append(scanner.nextLine());

            if (buffer.length() == 1 && buffer.charAt(0) == 'q') {
                break;
            }

            Stuff currentStuff = GlobalData.getStuff(buffer.toString());
            buffer.setLength(0);
            if (currentStuff == null) {
                System.out.println("There are no stuff " + buffer);
                continue;
            }
            buffer.setLength(0);

            System.out.println("Password: ");
            //char[] pwd = System.console().readPassword();
            buffer.append(scanner.nextLine());

            if (currentStuff.login(buffer.toString())) {
                buffer.setLength(0);
                System.out.println("Welcome " + currentStuff.getName() + "! Last logged in on " + currentStuff.acquireFormatLastLogged());
            } else {
                continue;
            }

            switch (currentStuff.getPosition()) {
                case "Teacher" -> teacherOperation((Teacher) currentStuff, buffer, scanner);
                case "Director" -> directorOperation((Director) currentStuff, buffer, scanner);
                case "Administrator" -> administratorOperation((Administrator) currentStuff, buffer, scanner);
            }
            buffer.setLength(0);
            currentStuff.setLastLogged(LocalDateTime.now());
        }

        GlobalData.end();
    }

    private void teacherOperation(Teacher teacher, StringBuffer buffer, Scanner scanner) {
        HashSet<String> invitations = teacher.getInvitations();
        if (invitations != null) {
            System.out.println("Here are " + invitations.size() + " invitations.");
        }
        boolean logout = false;
        while (!logout) {
            System.out.println("\n");
            System.out.println("You want Display personal information(1), View the invitation(2) or Logout(3)?: ");
            int operation = Integer.parseInt(scanner.nextLine());
            switch (operation) {
                case 1:
                    teacher.display();
                    break;
                case 2:
                    if (invitations == null) {
                        System.out.println("You have not received any invitation.");
                        break;
                    }

                    for (String invitationID : invitations) {
                        Requirement requirement = GlobalData.getRequirement(invitationID);
                        if (requirement == null) {
                            System.out.println("There are no requirement with ID " + invitationID);
                            break;
                        }
                        requirement.display();
                    }

                    System.out.println("Do you want to involve one of these invitations? ('No' or uuid): ");
                    String uuid = scanner.nextLine();
                    if (uuid.equals("No") || uuid.equals("no")) {
                        break;
                    } else {
                        Requirement requirement = GlobalData.getRequirement(uuid);
                        assert requirement != null;
                        requirement.addTeacher(teacher.getID());
                        GlobalData.changeRequirement(requirement.getID(), requirement);
                        teacher.removeInvitation(uuid);
                    }
                    break;
                case 3:
                    logout = true;
            }
        }
    }

    private void directorOperation(Director director, StringBuffer buffer, Scanner scanner) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n");
            System.out.println("You want Display personal information(1), Formulate the requirement(2) or Logout(3)?: ");
            int operation = Integer.parseInt(scanner.nextLine());
            switch (operation) {
                case 1:
                    director.display();
                    break;
                case 2:
                    System.out.println("Expected age: ");
                    String age = scanner.nextLine();

                    System.out.println("Expected course: ");
                    String course = scanner.nextLine();

                    Requirement requirement = director.formulateRequirement(age, course);
                    requirement.display();

                    GlobalData.addRequirement(requirement);
                    break;
                case 3:
                    logout = true;
            }
        }
    }

    private void administratorOperation(Administrator administrator, StringBuffer buffer, Scanner scanner) throws ClassNotFoundException {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n");
            System.out.println("Here are " + GlobalData.getInstance().getStuffNumbers() + " stuffs and " + GlobalData.getInstance().getRequirementNumbers() + " requirements.");
            System.out.println("You want Display personal information(1), View requirements(2) or Logout(3)?: ");
            int operation = Integer.parseInt(scanner.nextLine());
            switch (operation) {
                case 1:
                    administrator.display();
                    buffer.setLength(0);
                    break;
                case 2:
                    Vector<Requirement> requirements = GlobalData.getAllRequirements();
                    for (Requirement requirement : requirements) {
                        requirement.display();
                    }

                    System.out.println("Which requirement do you want to choose (uuid): ");
                    buffer.append(scanner.nextLine());
                    String processRequirement = buffer.toString();
                    buffer.setLength(0);

                    System.out.println("You want Find appropriate teacher(1) or Train current teacher on requirement(2): ");
                    buffer.append(scanner.nextLine());
                    if (buffer.toString().equals("1")) {
                        System.out.println("Here are the satisfied teachers: ");
                        Vector<Teacher> satisfiedTeachers = administrator.findAppropriateTeacher(processRequirement);
                        for (Teacher teacher : satisfiedTeachers) {
                            teacher.display();
                        }

                        System.out.println("Which teachers do you want to send invitations: ");
                        buffer.setLength(0);
                        buffer.append(scanner.nextLine());
                        String tempName = buffer.toString().replaceAll("\\s+", "");
                        String[] teachersName = tempName.split(",");
                        for (String teacherName : teachersName) {
                            administrator.sendInvitation(processRequirement, teacherName);
                        }
                    } else {
                        administrator.trainTeacher(processRequirement);
                        System.out.println("Training success.");
                    }
                    buffer.setLength(0);
                    break;
                case 3:
                    logout = true;
            }
        }
    }
}
