import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  // Делаем Scanner доступным для всех методов класса Main
  private static Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

  public static void main(String[] args) {
    List<BankAccount> accounts = new ArrayList<>();
    accounts.add(new BankAccount("Tom", 1000));
    accounts.add(new BankAccount("Stane", 8000));
    accounts.add(new BankAccount("Ludic", -9000));

    while (true) {
      System.out.println("\nСписок счетов сотрудников");
      for (int i = 0; i < accounts.size(); i++) {
        System.out.printf("%d. ", i + 1);
        accounts.get(i).print();
      }
      System.out.println("'e' Выход");
      System.out.println("0. Создать счет");
      System.out.print("Выберите счёт: ");

      // Читаем как строку, чтобы можно было ввести 'e'
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("e")) {
        System.out.println("До свидания!");
        break;
      }

      // Создание счета
      int choice;
      try {
        choice = Integer.parseInt(input);
      } catch (NumberFormatException ex) {
        System.out.println("Ошибка: Введите число или 'e'.");
        continue;
      }

      if (choice == 0) {
        String name = readName("Пожалуйста, представьтесь: ");
        accounts.add(new BankAccount(name, 0));
        System.out.println("Счёт создан!");
        continue;
      }

      // Проверка корректности выбора
      if (choice < 1 || choice > accounts.size()) {
        System.out.println("Неверный номер счёта. Попробуйте снова.");
        continue;
      }

      BankAccount selected = accounts.get(choice - 1);

      System.out.println("\nВыбран счёт: " + selected.getName() + " (баланс: " + selected.getMoney() + ")");
      System.out.println("1. Пополнить");
      System.out.println("2. Снять");
      System.out.println("3. Перевести");

      int action = readInt("Выберите действие: ");

      if (action < 1 || action > 3) {
        System.out.println("Неверное действие.");
        continue;
      }

      int dep = readInt("Введите сумму: ");

      switch (action) {
        case 1:
          selected.deposit(dep);
          break;
        case 2:
          selected.cash(dep);
          break;

        case 3:
          int targetIndex = readInt("Кому перевести? Введите номер счёта: ");

          if (targetIndex < 1 || targetIndex > accounts.size()) {
            System.out.println("Неверный номер счёта получателя.");
            break;
          }

          if (targetIndex - 1 == choice - 1) {
            System.out.println("Нельзя перевести самому себе.");
            break;
          }
          BankAccount target = accounts.get(targetIndex - 1);
          selected.transfer(target, dep);
          break;
      }
    }

    scanner.close();
  }

  private static String readName(String prompt) {
    while (true) {
      System.out.print(prompt);
      String name = scanner.nextLine().trim();

      if (name.isEmpty()) {
        System.out.println("Ошибка: имя не может быть пустым.");
        continue;
      }

      boolean isValid = true;
      for (int i = 0; i < name.length(); i++) {
        if (!Character.isLetter(name.charAt(i)) && (name.charAt(i)) != ' ') {
          isValid = false;
          break;
        }
      }

      if (!isValid) {
        System.out.println("Ошибка: имя должно состоять только из букв и пробелов.");
        continue;
      }
      return name;
    }
  }

  // Универсальный метод для безопасного ввода числа
  private static int readInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      String line = scanner.nextLine().trim(); // Читаем строку целиком
      try {
        return Integer.parseInt(line); // Пытаемся перевести в число
      } catch (NumberFormatException ex) {
        // Если не получилось — выводим ошибку и цикл повторяется
        System.out.println("Ошибка: '" + line + "' — это не число. Попробуйте снова.");
      }
    }
  }
}